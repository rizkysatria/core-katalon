package core.context

import utility.Platform

class ExecutionContext {

    private static Platform platform

    static void setPlatform(Platform p) {
        platform = p
    }
    
    static Platform getPlatform() {
        return platform != null ? platform : Platform.ANDROID
    }
    
    static boolean isIOS() {
        return getPlatform() == Platform.IOS
    }

    static boolean isAndroid() {
        return getPlatform() == Platform.ANDROID
    }
    
}
