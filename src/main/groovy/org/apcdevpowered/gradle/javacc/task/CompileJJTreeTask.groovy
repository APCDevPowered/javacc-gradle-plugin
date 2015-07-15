package org.apcdevpowered.gradle.javacc.task

import java.io.File

import org.gradle.api.file.RelativePath

class CompileJJTreeTask extends AbstractCompileTask {
    File getDefaultDestinationDir() {
        return new File(getProject().getBuildDir(), 'generated/jjtree')
    }

    String[] getDefaultIncludeFiles() {
        return ['**/*.jjt']
    }

    @Override
    protected void compileSource(File sourceFile, RelativePath relativePath) {
    }
}
