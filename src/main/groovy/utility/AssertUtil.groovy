package core.utility

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject

import core.LocatorResolver
import utility.LogUtil

class AssertUtility {

    static void visible(String key, String message = null) {
        def to = LocatorResolver.create(key)
        boolean result = Mobile.waitForElementVisible(to, 3,FailureHandling.OPTIONAL)

        if (!result) {
            fail(message ?: "Expected element [$key] to be visible")
        } else {
            pass(message ?: "Element [$key] is visible")
        }
    }

    static void notVisible(String key, String message = null) {
        def to = LocatorResolver.create(key)
        boolean result = Mobile.waitForElementNotVisible(to, 3, FailureHandling.OPTIONAL)

        if (!result) {
            fail(message ?: "Expected element [$key] to be NOT visible")
        } else {
            pass(message ?: "Element [$key] is not visible")
        }
    }

    static void textEquals(String key, String expected, String message = null) {
        def to = LocatorResolver.create(key)
        String actual = Mobile.getText(to, 3, FailureHandling.OPTIONAL)

        if (actual != expected) {
            fail(message ?: 
                "Text mismatch on [$key]. Expected <$expected> but found <$actual>"
            )
        } else {
            pass(message ?: "Text equals on [$key]: <$expected>")
        }
    }

    static void textContains(String key, String expected, String message = null) {
        def to = LocatorResolver.create(key)
        String actual = Mobile.getText(to, 3, FailureHandling.OPTIONAL)

        if (actual == null || !actual.contains(expected)) {
            fail(message ?: 
                "Expected text of [$key] to contain <$expected>, but was <$actual>"
            )
        } else {
            pass(message ?: "Text contains <$expected> on [$key]")
        }
    }

    static void enabled(String key, String message = null) {
        def to = LocatorResolver.create(key)
        boolean enabled = Mobile.verifyElementEnabled(to, 3, FailureHandling.OPTIONAL)

        if (!enabled) {
            fail(message ?: "Expected element [$key] to be enabled")
        } else {
            pass(message ?: "Element [$key] is enabled")
        }
    }

    static void disabled(String key, String message = null) {
        def to = LocatorResolver.create(key)
        boolean enabled = Mobile.verifyElementEnabled(to, 3, FailureHandling.OPTIONAL)

        if (enabled) {
            fail(message ?: "Expected element [$key] to be disabled")
        } else {
            pass(message ?: "Element [$key] is disabled")
        }
    }

    private static void fail(String message) {
        LogUtil.logAssert("❌ FAILED: $message")
    }

    private static void pass(String message) {
        LogUtil.logAssert("✅ PASSED: $message")
    }
}
