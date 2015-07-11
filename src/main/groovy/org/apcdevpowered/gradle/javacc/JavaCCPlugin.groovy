package org.apcdevpowered.gradle.javacc

import org.apcdevpowered.gradle.javacc.model.JavaCCModel;
import org.gradle.api.Plugin
import org.gradle.api.Project;

class JavaCCPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.extensions.create("javacc", JavaCCModel)
        
        project.getTasks().create('compileJavaCC', CompileJavaCC)
        project.getTasks().create('compileJJTree', CompileJJTree)
        project.getTasks().create('compileJTB', CompileJTB)
    }
}
