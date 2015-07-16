package org.apcdevpowered.gradle.javacc.task

import java.io.File

import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.RelativePath
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

abstract class AbstractCompileTask extends SourceTask {
    File destinationDir

    AbstractCompileTask() {
        destinationDir = getDefaultDestinationDir()
        include(getDefaultIncludeFiles())
    }

    abstract File getDefaultDestinationDir()

    abstract String[] getDefaultIncludeFiles()

    @OutputDirectory
    File getDestinationDir() {
        return destinationDir
    }

    void setDestinationDir(File destinationDir) {
        this.destinationDir = destinationDir
    }
 
    @TaskAction
    public void apply() {
        getSource().visit( { FileVisitDetails fileDetails -> if (!fileDetails.isDirectory()) processFile(fileDetails) } )
    }

    protected abstract void processFile(FileVisitDetails fileDetails)
}