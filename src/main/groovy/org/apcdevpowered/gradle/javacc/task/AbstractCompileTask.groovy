package org.apcdevpowered.gradle.javacc.task

import java.io.File

import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.RelativePath
import org.gradle.api.internal.TaskInternal
import org.gradle.api.internal.tasks.TaskExecuter;
import org.gradle.api.internal.tasks.TaskExecutionContext;
import org.gradle.api.internal.tasks.TaskStateInternal;
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

abstract class AbstractCompileTask extends SourceTask {
    File destinationDir
    Closure processSourceSet

    AbstractCompileTask() {
        destinationDir = getDefaultDestinationDir()
        include(getDefaultIncludeFiles())
        TaskExecuter originalExecuter = getExecuter()
        setExecuter( { TaskInternal task, TaskStateInternal state, TaskExecutionContext context ->
            try {
                originalExecuter.execute(task, state, context)
            } finally {
                if (state.getFailure() == null) {
                    if (processSourceSet != null) {
                        processSourceSet.call()
                    }
                }
            }
        } as TaskExecuter )
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

    public Closure getProcessSourceSet() {
        return processSourceSet
    }

    public void setProcessSourceSet(Closure processSourceSet) {
        this.processSourceSet = processSourceSet
    }

    @TaskAction
    void apply() {
        getSource().visit( { FileVisitDetails fileDetails -> if (!fileDetails.isDirectory()) processFile(fileDetails) } )
    }

    protected abstract void processFile(FileVisitDetails fileDetails)
}