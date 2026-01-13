package core.interaction

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import core.runtime.locator.LocatorResolver
import core.types.Timeout
import core.runtime.profile.ExecutionProfile
import core.adapter.ios.IOSTapStrategy

class TapHandler {
    
    static void handle(String key, Timeout timeout = Timeout.VERY_SHORT) {
        def testObject = LocatorResolver.create(key)
        try {
            Mobile.tap(testObject, timeout.seconds)
            return
        } catch (Exception ignore) {}


        if (ExecutionProfile.isIOS()) {
            IOSTapStrategy.tap(testObject, timeout)
            return
        } else {
            Mobile.tap(testObject, timeout.seconds)
             return 
        }

        Mobile.tap(testObject, timeout.seconds)
    }   

}