package core.adapter.ios

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import core.utility.LogUtil

class IOSUIResolver {

    private static final List<String> TEXT_PRIORITY = [
        "label",
        "name",
        "value",           
        "placeholderValue" 
    ]

    static String getText(TestObject to, int timeout) {
        for (String attr : TEXT_PRIORITY) {
            String val = Mobile.getAttribute(
                to,
                attr,
                timeout,
                FailureHandling.OPTIONAL    
            )
            if (isValid(val)) {
                return val
            }
        }

        return null
    }

    private static boolean isValid(String v) {
        return v != null && v.trim().length() > 0
    }
}
