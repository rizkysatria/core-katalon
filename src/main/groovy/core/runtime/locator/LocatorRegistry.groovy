package core.runtime.locator

import core.runtime.profile.ExecutionProfile
import groovy.json.JsonSlurper

class LocatorRegistry {

    private static final ThreadLocal<Class> ACTIVE =
        new ThreadLocal<>()

    static void register(Class locatorClass) {
        ACTIVE.set(locatorClass)
    }

    static void unregister() {
        ACTIVE.remove()
    }   

    static String resolve(String key) {
        Class clazz = ACTIVE.get()

        if (clazz == null) {
            throw new IllegalStateException(
                "LocatorRegistry not initialized for this thread"
            )
        }

        try {
            def value = clazz."$key"

            if (!(value instanceof String)) {
                throw new IllegalStateException(
                    "Locator '$key' in ${clazz.simpleName} is null or not a String"
                )
            }

            return value
        } catch (MissingPropertyException e) {
            throw new IllegalArgumentException(
                "Locator key not found: $key in ${clazz.simpleName}"
            )
        }
    }
}
