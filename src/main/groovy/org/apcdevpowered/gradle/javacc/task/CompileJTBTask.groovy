package org.apcdevpowered.gradle.javacc.task

import java.io.File

import org.apcdevpowered.gradle.javacc.JavaCCPlugin
import org.apcdevpowered.gradle.javacc.internal.ZipClassLoader
import org.apcdevpowered.gradle.javacc.model.JTBOptions
import org.gradle.api.GradleException
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.RelativePath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.util.CollectionUtils
import org.gradle.util.ConfigureUtil

import groovy.lang.Closure;

class CompileJTBTask extends AbstractCompileTask {

    public final String defaultJTBVersion = '1.4.9'

    static final Map<String, ZipClassLoader> JTB_CLASS_LOADER_CACHE = new HashMap<String, ZipClassLoader>()

    String version = defaultJTBVersion
    final JTBOptions options = new JTBOptions()

    String[] getDefaultIncludeFiles() {
        return ['**/*.jtb', '**/*.java']
    }

    @Input
    public String getVersion() {
        return version
    }

    public void setVersion(String version) {
        this.version = version
    }

    @Nested
    JTBOptions getOptions() {
        return options
    }

    CompileJTBTask options(Closure configureClosure) {
        ConfigureUtil.configure(configureClosure, getOptions())
        return this
    }

    @Override
    protected void processFile(FileVisitDetails fileDetails) {
        if(fileDetails.getFile().getName().endsWith('.jtb')) {
            compileSource(fileDetails.getFile(), fileDetails.getRelativePath())
        } else {
            fileDetails.copyTo(new File(getDestinationDir(), fileDetails.getRelativePath().getPathString()))
        }
    }

    protected void compileSource(File sourceFile, RelativePath relativePath) {
        Class<?> jtbClass = getJTBClass(version, 'EDU.purdue.jtb.JTB')
        String[] userOptions = options.buildOptions()
        List<String> programOptions = new ArrayList<String>()
        File destinationPackageDir = new File(getDestinationDir(), CollectionUtils.join("/", Arrays.copyOfRange(relativePath.getSegments(), 0, relativePath.getSegments().length - 1)))
        File outputFile = new File(destinationPackageDir, 'jtb.out.jj')
        long outputFileLastModified = outputFile ? outputFile.lastModified() : 0L
        destinationPackageDir.mkdirs()
        programOptions.addAll(['-d', destinationPackageDir])
        programOptions.addAll(['-p', destinationPackageDir])
        programOptions.addAll(['-o', outputFile])
        String[] arguments = (programOptions as String[]) + userOptions + sourceFile
        jtbClass.invokeMethod('main', [arguments] as Object[])
        Class<?> messagesClass = getJTBClass(version, 'EDU.purdue.jtb.misc.Messages')
        int errorCount  = messagesClass.invokeMethod('errorCount', [] as Object[])
        if (errorCount) {
            throw new GradleException("JTB failed with ${errorCount} errors")
        }
        if (!outputFile.exists()) throw new GradleException("JTB failed with output file not exists")
        if ((outputFile ? outputFile.lastModified() : 0L) == outputFileLastModified) throw new GradleException("JTB failed with output file last modified time not changed")
    }

    private static Class<?> getJTBClass(String version, String name) {
        ZipClassLoader classLoader = JTB_CLASS_LOADER_CACHE.get(version)
        if (classLoader) return classLoader.loadClass(name)
        InputStream resourceStream = JavaCCPlugin.getClassLoader().getResourceAsStream("jtb/jtb-${version}.jar")
        if (resourceStream == null) {
            throw new GradleException("JTB version ${version} not found")
        }
        return resourceStream.withCloseable( { InputStream inputStream ->
            classLoader = new ZipClassLoader(inputStream)
            Class clazz = classLoader.loadClass(name)
            JTB_CLASS_LOADER_CACHE.put(version, classLoader)
            return clazz
        } )
    }
}
