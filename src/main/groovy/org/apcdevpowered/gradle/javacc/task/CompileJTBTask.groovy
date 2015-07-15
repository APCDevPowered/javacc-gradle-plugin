package org.apcdevpowered.gradle.javacc.task

import EDU.purdue.jtb.JTB
import java.io.File

import org.apcdevpowered.gradle.javacc.JavaCCPlugin;
import org.apcdevpowered.gradle.javacc.internal.ZipClassLoader
import org.apcdevpowered.gradle.javacc.model.JTBOptions
import org.gradle.api.file.RelativePath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.util.CollectionUtils;

class CompileJTBTask extends AbstractCompileTask {
    public final String defaultJTBVersion = '1.4.9'

    static final Map<String, Class<JTB>> JTB_VERSION_CLASS_CACHE = new HashMap<String, Class<JTB>>()

    String version = defaultJTBVersion
    final JTBOptions options = new JTBOptions()

    File getDefaultDestinationDir() {
        return new File(getProject().getBuildDir(), 'generated/jtb')
    }

    String[] getDefaultIncludeFiles() {
        return ['**/*.jtb']
    }

    @Input
    public String getVersion() {
        return version
    }

    public void setVersion(String version) {
        this.version = version
    }

    @Nested
    public JTBOptions getOptions() {
        return options
    }

    @Override
    protected void compileSource(File sourceFile, RelativePath relativePath) {
        Class<JTB> clazz = getJTBClass(version)
        String[] userOptions = options.buildOptions()
        List<String> programOptions = new ArrayList<String>()
        File destinationPackageDir = new File(getDestinationDir(), CollectionUtils.join("/", Arrays.copyOfRange(relativePath.getSegments(), 0, relativePath.getSegments().length - 1)))
        destinationPackageDir.mkdirs()
        programOptions.addAll( ['-d', destinationPackageDir] )
        programOptions.addAll( ['-p', destinationPackageDir] )
        programOptions.addAll( ['-o', new File(destinationPackageDir, 'jtb.out.jj')] )
        String[] options = (programOptions as String[]) + userOptions + sourceFile
        clazz.invokeMethod('main', options)
    }

    private static Class<JTB> getJTBClass(String version) {
        Class<JTB> clazz = JTB_VERSION_CLASS_CACHE.get(version)
        if(clazz) return clazz
        InputStream resourceStream = JavaCCPlugin.getClassLoader().getResourceAsStream("jtb/jtb-${version}.jar")
        if(resourceStream == null) {
            throw new IllegalArgumentException("JTB version ${version} not found")
        }
        return resourceStream.withCloseable( { InputStream inputStream ->
            ZipClassLoader classLoader = new ZipClassLoader(inputStream)
            clazz = classLoader.loadClass('EDU.purdue.jtb.JTB')
            JTB_VERSION_CLASS_CACHE.put(version, clazz)
            return clazz
        } )
    }
}
