package org.apcdevpowered.gradle.javacc

import org.gradle.api.file.SourceDirectorySet

import groovy.lang.Closure

interface JTBSourceSet {

    SourceDirectorySet getJTB()

    SourceDirectorySet jtb(Closure configureClosure)
}
