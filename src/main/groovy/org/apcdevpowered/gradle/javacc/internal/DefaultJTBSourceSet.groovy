package org.apcdevpowered.gradle.javacc.internal

import org.apcdevpowered.gradle.javacc.JTBSourceSet
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.util.ConfigureUtil

import groovy.lang.Closure

class DefaultJTBSourceSet implements JTBSourceSet {

    final SourceDirectorySet jtb

    DefaultJTBSourceSet(String displayName, FileResolver fileResolver) {
        jtb = new DefaultSourceDirectorySet("${displayName} JTB source", fileResolver);
        jtb.getFilter().include(['**/*.jtb', '**/*.java']);
    }

    SourceDirectorySet getJTB() {
        return jtb
    }

    JTBSourceSet jtb(Closure configureClosure) {
        ConfigureUtil.configure(configureClosure, getJTB())
        return this
    }
}
