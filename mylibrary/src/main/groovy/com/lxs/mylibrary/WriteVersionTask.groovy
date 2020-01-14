package com.lxs.mylibrary

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class WriteVersionTask extends DefaultTask {
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
                defaultVersion = parseXml("/Users/liuxiaoshuai/Downloads/artifactory-oss-6.16.0/backup/backup-daily/current/repositories/gradle-dev-local/com/lxs/android/${project.name}/maven-metadata.xml")
                if (defaultVersion != "") {
                    def versionCode = defaultVersion.replace(".", "").toInteger() + 1
                    int amt = versionCode / 100
                    int sw = versionCode % 100 / 10
                    int qw = versionCode % 10
                    buf.replace(buf.indexOf("version ="), buf.length(), "version = \"${amt}.${sw}.${qw}\"")
                    line = buf.toString()
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