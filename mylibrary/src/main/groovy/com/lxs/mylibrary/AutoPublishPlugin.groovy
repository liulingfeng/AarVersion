package com.lxs.mylibrary
import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoPublishPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            project.getTasks().create("updateVersion",WriteVersionTask.class)
        }
    }
}