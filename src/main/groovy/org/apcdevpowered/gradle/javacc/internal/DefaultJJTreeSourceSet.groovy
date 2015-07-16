package org.apcdevpowered.gradle.javacc.internal

import org.apcdevpowered.gradle.javacc.JJTreeSourceSet
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.util.ConfigureUtil

import groovy.lang.Closure

class DefaultJJTreeSourceSet implements JJTreeSourceSet {

    final SourceDirectorySet jjtree
    
    DefaultJJTreeSourceSet(String displayName, FileResolver fileResolver) {
        jjtree = new DefaultSourceDirectorySet("${displayName} JJTree source", fileResolver);
        jjtree.getFilter().include(['**/*.jjt', '**/*.java']);
    }


    SourceDirectorySet getJJTree() {
        return jjtree
    }

    JJTreeSourceSet jjtree(Closure configureClosure) {
        ConfigureUtil.configure(configureClosure, getJJTree())
        return this
    }
}
