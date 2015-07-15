package org.apcdevpowered.gradle.javacc.internal

import java.io.Closeable
import java.io.IOException
import java.net.URL
import java.util.Enumeration
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

import org.apache.commons.io.IOUtils

class ZipClassLoader extends ClassLoader implements Closeable {
    File tempFile
    ZipFile zipFile

    ZipClassLoader(InputStream inputSream) {
        tempFile = File.createTempFile("ZipClassLoader.", ".jar")
        tempFile.withOutputStream( { OutputStream outputStream -> outputStream << inputSream } )
        zipFile = new ZipFile(tempFile)
    }

    @Override
    void close() throws IOException {
        zipFile.close()
        tempFile.delete()
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = name.replace('.', '/').concat(".class")
        ZipEntry zipEntry = zipFile.getEntry(path)
        try {
            byte[] bytes = zipFile.getInputStream(zipEntry).withCloseable( { InputStream inputStream -> IOUtils.toByteArray(inputStream) })
            if(bytes == null) {
                throw new ClassNotFoundException()
            }
            return defineClass(name, bytes, 0, bytes.length)
        } catch (IOException e) {
            throw new ClassNotFoundException(e)
        }
    }
    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        return findResource(name) ?: java.util.Collections.emptyEnumeration()
    }
    @Override
    protected URL findResource(String name) {
        return new URL(tempFile.toURI().toURL(), name)
    }
}