package org.apcdevpowered.gradle.javacc.task

import java.io.File

import org.gradle.api.file.RelativePath

class CompileJavaCCTask extends AbstractCompileTask {
    File getDefaultDestinationDir() {
        return new File(getProject().getBuildDir(), 'generated/javacc')
    }

    String[] getDefaultIncludeFiles() {
        return ['**/*.jj']
    }

    @Override
    protected void compileSource(File sourceFile, RelativePath relativePath) {
    }
}
