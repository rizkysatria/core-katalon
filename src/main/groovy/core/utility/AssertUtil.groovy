package core.utility

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject

import core.runtime.locator.LocatorResolver
import core.utility.LogUtil

/**
 * AssertUtil is a core assertion utility that provides
 * pure, data-level assertions for the automation framework.
 * For UI-related assertions (e.g. asserting text on screen),
 * use UIAssert instead.
 */
class AssertUtil {

    static void assertEquals(Object actual, Object expected, String message = "") {
        try {
            assert actual == expected
            LogUtil.infoAssert("PASSED", message)
        } catch (AssertionError e) {
            LogUtil.infoAssertFailed(
                "FAILED",
                "Expected=${expected}, Actual=${actual}. ${message}"
            )
            throw e
        }
    }

    static void assertContains(String actual, String expected, String message = "") {
        try {
            assert actual != null
            assert actual.contains(expected)
            LogUtil.infoAssert("PASSED", message)
        } catch (AssertionError e) {
            LogUtil.infoAssertFailed(
                "FAILED",
                "Expected '${actual}' to contain '${expected}'. ${message}"
            )
            throw e
        }
    }

}
