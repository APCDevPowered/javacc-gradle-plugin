package org.apcdevpowered.gradle.javacc

import org.apcdevpowered.gradle.javacc.model.JavaCCPluginExtension
import org.apcdevpowered.gradle.javacc.task.CompileJJTree
import org.apcdevpowered.gradle.javacc.task.CompileJTB
import org.apcdevpowered.gradle.javacc.task.CompileJavaCC
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaCCPlugin implements Plugin<Project> {
    static final String COMPILE_JAVACC_TASK_NAME = "compileJavaCC"
    static final String COMPILE_JJTREE_TASK_NAME = "compileJJTree"
    static final String COMPILE_JTB_TASK_NAME = "compileJTB"

    @Override
    public void apply(Project project) {
        JavaCCPluginExtension javaCCPluginExtension = project.extensions.create("javacc", JavaCCPluginExtension)

        configureCompileJavaCC(project, javaCCPluginExtension)
        configureCompileJJTree(project, javaCCPluginExtension)
        configureCompileJTB(project, javaCCPluginExtension)

        URL url = getClass().getClassLoader().getResource('jtb/jtb-1.4.0.jar')
        loader.loadClass('EDU.purdue.jtb.JTB')
    }

    private void configureCompileJavaCC(Project project, JavaCCPluginExtension javaCCPluginExtension) {
        project.tasks.create(COMPILE_JAVACC_TASK_NAME, CompileJavaCC) {
            group = 'JavaCC'
            description = 'Compile JavaCC code.'
        }
    }
    
    private void configureCompileJJTree(Project project, JavaCCPluginExtension javaCCPluginExtension) {
        project.tasks.create(COMPILE_JJTREE_TASK_NAME, CompileJJTree) {
            group = 'JavaCC'
            description = 'Compile JJTree code.'
        }
    }
    
    private void configureCompileJTB(Project project, JavaCCPluginExtension javaCCPluginExtension) {
        project.tasks.create(COMPILE_JTB_TASK_NAME, CompileJTB) {
            group = 'JavaCC'
            description = 'Compile JTB code.'
        }
    }
}
