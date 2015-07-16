package org.apcdevpowered.gradle.javacc.model

import java.util.Map

class JavaCCOptions {

    Map<String, Object> optionMap = new HashMap<String, Object>()

    @Override
    Object getProperty(String propertyName) {
        if (propertyName == null) {
            throw new NullPointerException('propertyName can not be null')
        }
        return optionMap.get(propertyName)
    }

    @Override
    void setProperty(String propertyName, Object newValue) {
        if (propertyName == null) {
            throw new NullPointerException('propertyName can not be null')
        }
        if(!propertyName.getChars().every( { char c -> c.isUpperCase() || c == '_' } )) {
            throw new NullPointerException('propertyName must be upper case with underline string')
        }
        if (newValue == null) {
            optionMap.remove(propertyName)
        } else if (newValue instanceof Boolean) {
            optionMap.put(newValue as Boolean)
        } else if (newValue instanceof Integer) {
            optionMap.put(newValue as Integer)
        } else {
            optionMap.put(newValue.toString())
        }
    }

    String[] buildOptions() {
        List<String> options = new ArrayList<String>()
        optionMap.each( { key, value ->
            if (value == null) {
                throw new IllegalStateException()
            } else if (value instanceof Boolean) {
                options.add("-${key}=${value}")
            } else if (value instanceof Integer) {
                options.add("-${key}=${value}")
            } else if (value instanceof String) {
                options.add("-${key}=${value}")
            } else {
                throw IllegalStateException()
            }
        } )
        return options
    }
}
