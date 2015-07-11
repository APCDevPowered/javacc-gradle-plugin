package org.apcdevpowered.gradle.javacc

import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceTask

abstract class AbstractCompileTask extends SourceTask {
    @InputDirectory
    File inputDirectory;
    @OutputDirectory
    File outputDirectory;

    AbstractCompileTask(File inputDirectory, File outputDirectory, String filter) {
        setInputDirectory(inputDirectory);
        setOutputDirectory(outputDirectory);
        include(filter);
    }

    File getInputDirectory() {
        return inputDirectory;
    }

    void setInputDirectory(File inputDirectory) {
        this.inputDirectory = inputDirectory;
        setSource(inputDirectory);
    }

    File getOutputDirectory() {
        return outputDirectory;
    }

    void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}
