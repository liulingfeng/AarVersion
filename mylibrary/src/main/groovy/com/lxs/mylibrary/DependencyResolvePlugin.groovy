package com.lxs.mylibrary

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency

class DependencyResolvePlugin implements Plugin<Project> {

    @Override
    void apply(Project targetProject) {
        if (targetProject == targetProject.rootProject) {
            throw new IllegalStateException("can not applied DependencyResolvePlugin to root project")
        }
        NamedDomainObjectContainer<DependencyResolveExt> dependencyResolveContainer = targetProject.container(DependencyResolveExt.class)
        targetProject.extensions.add("dynamicDependency", dependencyResolveContainer)
        targetProject.afterEvaluate {
            Map<Project, DependencyResolveExt> resolveExtMap = new HashMap<>()
            targetProject.configurations.all { Configuration configuration ->
                if (configuration.dependencies.size() == 0) {
                    return
                }
                configuration.dependencies.all { dependency ->
                    if (dependency instanceof DefaultProjectDependency) {
                        def projectName = dependency.dependencyProject.name
                        def dependencyResolveExt = dependencyResolveContainer.find {
                            it.name == projectName
                        }
                        if (dependencyResolveExt != null && !dependencyResolveExt.debuggable) {
                            resolveExtMap.put(dependency.dependencyProject, dependencyResolveExt)
                        }
                    }
                }
            }
            targetProject.configurations.all {
                resolutionStrategy.dependencySubstitution {
                    resolveExtMap.each { key, value ->
                        substitute project("${key.path}") with module("${value.groupId}:${getArtifactName(key, value.artifactId)}:${value.version}")
                    }
                }
            }
        }
    }

    static def getArtifactName(Project project, String name) {
        if (name == null || name == "") {
            return project.name
        }
        return name
    }
}