package org.apcdevpowered.gradle.javacc.model

class JTBOptions {

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
        if(!propertyName.getChars().every( { char c -> c.isLowerCase() } )) {
            throw new NullPointerException('propertyName must be lower case string')
        }
        if (newValue == null) {
            optionMap.remove(propertyName)
        } else if (newValue instanceof Boolean) {
            boolean value = newValue as Boolean
            if (value) {
                optionMap.put(propertyName, true)
            } else {
                optionMap.remove(propertyName)
            }
        } else {
            optionMap.put(propertyName, newValue.toString())
        }
    }

    String[] buildOptions() {
        List<String> options = new ArrayList<String>()
        optionMap.each( { key, value ->
            if (value == null) {
                throw new IllegalStateException()
            } else if (value instanceof Boolean) {
                if (value == false) {
                    throw new IllegalStateException()
                } else {
                    options.add("-${key}")
                }
            } else if (value instanceof String) {
                options.addAll( ["-${key}", value])
            } else {
                throw new IllegalStateException()
            }
        } )
        return options
    }
}
