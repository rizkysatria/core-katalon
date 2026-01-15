package core.interaction

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import core.runtime.locator.LocatorResolver
import core.runtime.profile.ExecutionProfile
import core.types.Timeout
import core.utility.LogUtil

class TextHandler {
    
    static void setText(String key, String text, Timeout timeout = Timeout.VERY_SHORT) {
        def testObject = LocatorResolver.create(key)
        try {
            Mobile.setText(testObject, text, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
            return
        } catch (Exception ignore) {}

        try {
            Mobile.setText(testObject, text, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
            return
        } catch (Exception ignore) {}

        Mobile.setText(testObject, text, timeout.seconds, FailureHandling.STOP_ON_FAILURE)

    }   

    static String getText(String key, Timeout timeout = Timeout.VERY_SHORT) {
        def testObject = LocatorResolver.create(key)
        if (ExecutionProfile.isIOS()) {
            String value = Mobile.getAttribute(testObject, "value", timeout.seconds, FailureHandling.OPTIONAL)
            return value
        } else {
            String value = Mobile.getText(testObject, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
            return value
        }
    } 

    static void clearText(String key, Timeout timeout = Timeout.MEDIUM) {
        def testObject = LocatorResolver.create(key)
        Mobile.clearText(testObject, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
    }

}