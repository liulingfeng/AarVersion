package com.lxs.mylibrary

class DependencyResolveExt {
    final String name

    DependencyResolveExt(String name) {
        this.name = name
    }

    boolean debuggable = true
    String groupId
    String artifactId
    String version

}