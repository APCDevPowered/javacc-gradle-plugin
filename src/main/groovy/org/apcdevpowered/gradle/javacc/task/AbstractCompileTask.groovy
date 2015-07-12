package org.apcdevpowered.gradle.javacc.task

import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask

abstract class AbstractCompileTask extends SourceTask {
    File destinationDir

    @OutputDirectory
    File getDestinationDir() {
        return destinationDir
    }

    void setDestinationDir(File destinationDir) {
        this.destinationDir = destinationDir
    }
}
