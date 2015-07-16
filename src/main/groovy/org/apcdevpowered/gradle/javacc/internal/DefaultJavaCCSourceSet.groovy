package org.apcdevpowered.gradle.javacc.internal

import org.apcdevpowered.gradle.javacc.JavaCCSourceSet
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.util.ConfigureUtil

import groovy.lang.Closure

class DefaultJavaCCSourceSet implements JavaCCSourceSet {

    final SourceDirectorySet javacc

    DefaultJavaCCSourceSet(String displayName, FileResolver fileResolver) {
        javacc = new DefaultSourceDirectorySet("${displayName} JavaCC source", fileResolver);
        javacc.getFilter().include(['**/*.jj', '**/*.java']);
    }

    SourceDirectorySet getJavaCC() {
        return javacc
    }

    JavaCCSourceSet javacc(Closure configureClosure) {
        ConfigureUtil.configure(configureClosure, getJavaCC())
        return this
    }
}
