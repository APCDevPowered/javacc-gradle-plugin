package org.apcdevpowered.gradle.javacc

import org.gradle.api.file.SourceDirectorySet

import groovy.lang.Closure

interface JavaCCSourceSet {

    SourceDirectorySet getJavaCC()

    JavaCCSourceSet javacc(Closure configureClosure)
}
