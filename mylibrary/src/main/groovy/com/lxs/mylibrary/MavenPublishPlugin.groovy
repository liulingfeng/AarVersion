package com.lxs.mylibrary

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ExcludeRule
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.publish.maven.MavenPublication

class MavenPublishPlugin implements Plugin<Project> {

    @Override
    void apply(Project targetProject) {

        if (targetProject == targetProject.rootProject || targetProject.plugins.hasPlugin("com.android.application") || targetProject.name == "app") {
            targetProject.logger.warn("only the library module will apply the plugin:MavenPublishPlugin ")
            return
        }

        // add the needed plugin
        targetProject.plugins.apply("maven-publish")
        targetProject.plugins.apply("com.jfrog.artifactory")

        MavenPublishExt publishExt = targetProject.extensions.create("mavenPublish", MavenPublishExt)
        targetProject.afterEvaluate {
            // 添加上传构件的task，并定义task的依赖关系
            targetProject.publishing {
                publications {
                    aar(MavenPublication) {
                        groupId = publishExt.groupId
                        artifactId = getArtifactName(targetProject, publishExt.artifactId)
                        version = publishExt.version
//                        if (publishExt.version.contains("SNAPSHOT")) {
//                            artifact("$targetProject.buildDir/outputs/aar/${targetProject.getName()}-debug.aar")
//                        } else {
                        artifact("$targetProject.buildDir/outputs/aar/${targetProject.getName()}-release.aar")
//                        }

                        pom.withXml {
                            def dependencies = asNode().appendNode('dependencies')
                            //ModuleDependency过滤fileTree
                            targetProject.configurations.implementation.allDependencies.withType(ModuleDependency) {
                                // 过滤library引用
                                if (it.version != "unspecified") {
                                    def dependency = dependencies.appendNode('dependency')
                                    dependency.appendNode('groupId', it.group)
                                    dependency.appendNode('artifactId', it.name)
                                    dependency.appendNode('version', it.version)

                                    //添加exclude
                                    if (it.excludeRules.size() > 0) {
                                        def exclusions = dependencies.appendNode("exclusions")
                                        it.excludeRules.each { ExcludeRule ex ->
                                            def exclusion = exclusions.appendNode('exclusion')
                                            exclusion.appendNode('groupId', ex.group)
                                            exclusion.appendNode('artifactId', ex.module)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            targetProject.artifactory {
                contextUrl = publishExt.contextUrl
                publish {
                    repository {
                        repoKey = publishExt.repoKey
                        username = publishExt.userName
                        password = publishExt.password
                    }

                    defaults {
                        publications('aar')
                        publishArtifacts = true
                        // Properties to be attached to the published artifacts.
                        properties = ['qa.level': 'basic', 'dev.team': 'core']
                        // Publish generated POM files to Artifactory (true by default)
                        publishPom = true
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