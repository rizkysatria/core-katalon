package core.localStore

class LocalStore {

    private static final ThreadLocal<Map<String, Object>> STORE =
            ThreadLocal.withInitial { [:] }

    static void set(String key, Object value) {
        STORE.get()[key] = value
    }

    static <T> T get(String key, T defaultValue = null) {
        def map = STORE.get()
        return map.containsKey(key) ? (T) map[key] : defaultValue
    }

    static void clear() {
        STORE.get().clear()
    }
    
}
