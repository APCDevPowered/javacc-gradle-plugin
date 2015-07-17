package org.apcdevpowered.gradle.javacc.task

import java.io.File

import org.apcdevpowered.gradle.javacc.model.JavaCCOptions
import org.gradle.api.GradleException
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.RelativePath
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.SourceSet
import org.gradle.util.CollectionUtils
import org.gradle.util.ConfigureUtil
import org.javacc.parser.Main

import groovy.lang.Closure

class CompileJavaCCTask extends AbstractCompileTask {

    final JavaCCOptions options = new JavaCCOptions()
    
    String[] getDefaultIncludeFiles() {
        return ['**/*.jj', '**/*.java']
    }

    @Nested
    JavaCCOptions getOptions() {
        return options
    }

    CompileJavaCCTask options(Closure configureClosure) {
        ConfigureUtil.configure(configureClosure, getOptions())
        return this
    }

    @Override
    protected void processFile(FileVisitDetails fileDetails) {
        if(fileDetails.getFile().getName().endsWith('.jj')) {
            compileSource(fileDetails.getFile(), fileDetails.getRelativePath())
        } else {
            fileDetails.copyTo(new File(getDestinationDir(), fileDetails.getRelativePath().getPathString()))
        }
    }

    protected void compileSource(File sourceFile, RelativePath relativePath) {
        String[] userOptions = options.buildOptions()
        List<String> programOptions = new ArrayList<String>()
        File destinationPackageDir = new File(getDestinationDir(), CollectionUtils.join("/", Arrays.copyOfRange(relativePath.getSegments(), 0, relativePath.getSegments().length - 1)))
        programOptions.add("-OUTPUT_DIRECTORY=${destinationPackageDir}")
        String[] arguments = (programOptions as String[]) + userOptions + sourceFile
        int errorCode = Main.mainProgram(arguments)
        if (errorCode != 0) {
            throw new GradleException("JavaCC failed with error code ${errorCode}")
        }
    }
}
