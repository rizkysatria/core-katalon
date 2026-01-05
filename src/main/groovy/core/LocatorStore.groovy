package core

import core.context.ExecutionContext
import groovy.json.JsonSlurper

class LocatorStore {

    private static final ThreadLocal<Class> ACTIVE =
        new ThreadLocal<>()

    static void init(Class locatorClass) {
        ACTIVE.set(locatorClass)
    }

    static void clear() {
        ACTIVE.remove()
    }

    static String get(String key) {
        Class clazz = ACTIVE.get()

        if (clazz == null) {
            throw new IllegalStateException(
                "LocatorStore not initialized for this thread"
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
