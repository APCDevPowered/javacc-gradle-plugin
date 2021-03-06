package org.apcdevpowered.gradle.javacc

import javax.inject.Inject

import org.apcdevpowered.gradle.javacc.internal.DefaultJJTreeSourceSet
import org.apcdevpowered.gradle.javacc.internal.DefaultJTBSourceSet;
import org.apcdevpowered.gradle.javacc.internal.DefaultJavaCCSourceSet;
import org.apcdevpowered.gradle.javacc.task.CompileJJTreeTask
import org.apcdevpowered.gradle.javacc.task.CompileJTBTask
import org.apcdevpowered.gradle.javacc.task.CompileJavaCCTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.file.FileResolver
import org.gradle.api.internal.plugins.DslObject;
import org.gradle.api.internal.tasks.DefaultSourceSet;
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet

class JavaCCPlugin implements Plugin<Project> {

    final FileResolver fileResolver

    @Inject
    JavaCCPlugin(FileResolver fileResolver) {
        this.fileResolver = fileResolver
    }

    @Override
    public void apply(Project project) {
        project.getPlugins().apply(JavaPlugin)

        JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class)

        configureCompileJavaCC(project, javaConvention)
        configureCompileJJTree(project, javaConvention)
        configureCompileJTB(project, javaConvention)
    }

    private void configureCompileJavaCC(Project project, JavaPluginConvention pluginConvention) {
        pluginConvention.sourceSets.all( { SourceSet sourceSet ->
            DslObject sourceSetDslObject = new DslObject(sourceSet)

            DefaultJavaCCSourceSet javaccSourceSet = new DefaultJavaCCSourceSet(sourceSet.getDisplayName(), fileResolver)
            sourceSetDslObject.getConvention().getPlugins().put('JavaCC', javaccSourceSet)
            javaccSourceSet.getJavaCC().srcDir("src/${sourceSet.getName()}/javacc")
            sourceSet.getAllSource().source(javaccSourceSet.getJavaCC())

            String compileTaskName = sourceSet.getCompileTaskName('JavaCC')
            CompileJavaCCTask task = project.tasks.create(compileTaskName, CompileJavaCCTask) {
                group = 'JavaCC'
                description = 'Compile JavaCC code.'
                source = javaccSourceSet.getJavaCC()
                destinationDir = new File(project.getBuildDir(), "generated/${sourceSet.getName()}/javacc")
                processSourceSet = {
                    sourceSet.java.srcDir(destinationDir)
                }
            }
            project.tasks.getByName(sourceSet.getCompileJavaTaskName()).shouldRunAfter(task)
            if(sourceSet.getName().equals(SourceSet.MAIN_SOURCE_SET_NAME)) {
                project.tasks.getByName("assemble").dependsOn(task)
            } else if(sourceSet.getName().equals(SourceSet.TEST_SOURCE_SET_NAME)) {
                project.tasks.getByName("test").dependsOn(task)
            }
        } )
    }

    private void configureCompileJJTree(Project project, JavaPluginConvention pluginConvention) {
        pluginConvention.sourceSets.all( { SourceSet sourceSet ->
            DslObject sourceSetDslObject = new DslObject(sourceSet)

            DefaultJJTreeSourceSet jjtreeSourceSet = new DefaultJJTreeSourceSet(sourceSet.getDisplayName(), fileResolver)
            sourceSetDslObject.getConvention().getPlugins().put('JJTree', jjtreeSourceSet)
            jjtreeSourceSet.getJJTree().srcDir("src/${sourceSet.getName()}/jjtree")
            sourceSet.getAllSource().source(jjtreeSourceSet.getJJTree())

            String compileTaskName = sourceSet.getCompileTaskName('JJTree')
            CompileJJTreeTask task = project.tasks.create(compileTaskName, CompileJJTreeTask) {
                group = 'JavaCC'
                description = 'Compile JJTree code.'
                source = jjtreeSourceSet.getJJTree()
                destinationDir = new File(project.getBuildDir(), "generated/${sourceSet.getName()}/jjtree")
                processSourceSet = {
                    sourceSet.javacc.srcDir(destinationDir)
                }
            }
            project.tasks.getByName(sourceSet.getCompileTaskName('JavaCC')).shouldRunAfter(task)
            if(sourceSet.getName().equals(SourceSet.MAIN_SOURCE_SET_NAME)) {
                project.tasks.getByName("assemble").dependsOn(task)
            } else if(sourceSet.getName().equals(SourceSet.TEST_SOURCE_SET_NAME)) {
                project.tasks.getByName("test").dependsOn(task)
            }
        } )
    }

    private void configureCompileJTB(Project project, JavaPluginConvention pluginConvention) {
        pluginConvention.sourceSets.all( { SourceSet sourceSet ->
            DslObject sourceSetDslObject = new DslObject(sourceSet)

            DefaultJTBSourceSet jtbSourceSet = new DefaultJTBSourceSet(sourceSet.getDisplayName(), fileResolver)
            sourceSetDslObject.getConvention().getPlugins().put('JTB', jtbSourceSet)
            jtbSourceSet.getJTB().srcDir("src/${sourceSet.getName()}/jtb")
            sourceSet.getAllSource().source(jtbSourceSet.getJTB())

            String compileTaskName = sourceSet.getCompileTaskName('JTB')
            CompileJTBTask task = project.tasks.create(compileTaskName, CompileJTBTask) {
                group = 'JavaCC'
                description = 'Compile JTB code.'
                source = jtbSourceSet.getJTB()
                destinationDir = new File(project.getBuildDir(), "generated/${sourceSet.getName()}/jtb")
                processSourceSet = {
                    sourceSet.javacc.srcDir(destinationDir)
                }
            }
            project.tasks.getByName(sourceSet.getCompileTaskName('JavaCC')).shouldRunAfter(task)
            if(sourceSet.getName().equals(SourceSet.MAIN_SOURCE_SET_NAME)) {
                project.tasks.getByName("assemble").dependsOn(task)
            } else if(sourceSet.getName().equals(SourceSet.TEST_SOURCE_SET_NAME)) {
                project.tasks.getByName("test").dependsOn(task)
            }
        } )
    }
}
