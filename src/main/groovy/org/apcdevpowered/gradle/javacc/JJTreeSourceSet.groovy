package org.apcdevpowered.gradle.javacc

import org.gradle.api.file.SourceDirectorySet

import groovy.lang.Closure

interface JJTreeSourceSet {

    SourceDirectorySet getJJTree()

    SourceDirectorySet jjtree(Closure configureClosure)
}
