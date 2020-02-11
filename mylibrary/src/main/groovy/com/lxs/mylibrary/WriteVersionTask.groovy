package com.lxs.mylibrary

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class WriteVersionTask extends DefaultTask {
    @Optional
    String defaultVersion = "1.0.0"

    public WriteVersionTask() {
        setGroup("aar版本升级")
        setDescription("版本自动升级")
    }

    @TaskAction
    void run() {
        def path = "./build.gradle"
        File versionFile = new File(path)
        StringBuffer updatedFileText = new StringBuffer()
        versionFile.eachLine { line ->
            if (line.contains("version =")) {
                def buf = new StringBuffer(line)
                def projectName = ""
                if (project.name.startsWith("ewt_")) {
                    projectName = project.name.substring(4, project.name.length())
                }else {
                    projectName = project.name
                }
                if ("comlib2".equalsIgnoreCase(project.name) || "core".equalsIgnoreCase(project.name)) {
                    defaultVersion = parseXml("http://172.16.9.78:8081/artifactory/gradle-dev-local/com/mistong/android/${projectName}/maven-metadata.xml")
                } else {
                    defaultVersion = parseXml("http://172.16.9.78:8081/artifactory/gradle-dev-local/com/mistong/business/${projectName}/maven-metadata.xml")
                }
                println "${projectName}线上最新版本${defaultVersion}"
                if (defaultVersion != "") {
                    def versionCode = defaultVersion.replace(".", "").toInteger() + 1
                    int amt = versionCode / 100
                    int sw = versionCode % 100 / 10
                    int qw = versionCode % 10
                    buf.replace(buf.indexOf("version ="), buf.length(), "version = \"${amt}.${sw}.${qw}\"")
                    line = buf.toString()
                    println "版本更新成功${versionCode}"
                }
            }
            updatedFileText.append(line + "\n")
        }
        versionFile.write(updatedFileText.toString())
    }

    static def parseXml(urlString) {
        try {
            def metaData = new XmlParser().parse(urlString)
            def latestVersion = metaData.versioning.latest
            return latestVersion.text()
        } catch (Exception e) {
            println "文件不存在${e.message}"
        }
        return ""
    }
}