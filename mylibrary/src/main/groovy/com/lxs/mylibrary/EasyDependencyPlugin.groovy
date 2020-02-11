package com.lxs.mylibrary

import org.gradle.api.Plugin
import org.gradle.api.Project

class EasyDependencyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
//        try {
//            GitHooksUtil.checkGitHookFile(project)
//        } catch (IOException e) {
//            e.printStackTrace()
//        }

        if (project != project.rootProject) {
            throw new IllegalStateException("please apply plugin easy-dependency to root project")
        }
        project.subprojects {
            it.plugins.apply(DependencyResolvePlugin)
            it.plugins.apply(AutoPublishPlugin)
            it.plugins.apply(MavenPublishPlugin)
        }
    }
}