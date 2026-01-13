package core.utility

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject

import core.runtime.locator.LocatorResolver
import core.utility.LogUtil
import org.junit.Assert

class AssertUtil {

    static void assertTrue(boolean condition, String message = "Expected condition to be TRUE") {
        LogUtil.logAssert("[ASSERT] assertTrue → ${message}")
        Assert.assertTrue(message, condition)
        LogUtil.logAssert("[ASSERT PASS] ${message}")
    }

    static void assertFalse(boolean condition, String message = "Expected condition to be FALSE") {
        LogUtil.logAssert("[ASSERT] assertFalse → ${message}")
        Assert.assertFalse(message, condition)
        LogUtil.logAssert("[ASSERT PASS] ${message}")
    }

    static void assertEquals(String keyExpectedObject, String keyActualObject, String message = "") {
        def expected = LocatorResolver.create(keyExpectedObject)
        def actual = LocatorResolver.create(keyActualObject)
        String msg = message ?: "Expected: ${expected}, Actual: ${actual}"
        LogUtil.logAssert("[ASSERT] assertEquals → ${msg}")
        Assert.assertEquals(msg, expected, actual)
        LogUtil.logAssert("[ASSERT PASS] ${msg}")
    }

    static void assertNotEquals(String keyExpectedObject, String keyActualObject, String message = "") {
        def expected = LocatorResolver.create(keyExpectedObject)
        def actual = LocatorResolver.create(keyActualObject)
        String msg = message ?: "Expected NOT equals: ${expected}, Actual: ${actual}"
        LogUtil.logAssert("[ASSERT] assertNotEquals → ${msg}")
        Assert.assertNotEquals(msg, expected, actual)
        LogUtil.logAssert("[ASSERT PASS] ${msg}")
    }

    static void assertNotNull(String keyObject, String message = "Expected value NOT NULL") {
        def value = LocatorResolver.create(keyObject)
        LogUtil.logAssert("[ASSERT] assertNotNull → ${message}")
        Assert.assertNotNull(message, value)
        LogUtil.logAssert("[ASSERT PASS] ${message}")
    }

    static void assertNull(String keyObject, String message = "Expected value NULL") {
        def value = LocatorResolver.create(keyObject)
        LogUtil.logAssert("[ASSERT] assertNull → ${message}")
        Assert.assertNull(message, value)
        LogUtil.logAssert("[ASSERT PASS] ${message}")
    }

    static void assertEqualsIgnoreCase(String expected, String actual, String message = "") {
        String msg = message ?: "Expected (ignore case): ${expected}, Actual: ${actual}"
        LogUtil.logAssert("[ASSERT] assertEqualsIgnoreCase → ${msg}")
        Assert.assertTrue(msg, expected?.equalsIgnoreCase(actual))
        LogUtil.logAssert("[ASSERT PASS] ${msg}")
    }

    static void assertContains(String actual, String expectedPart, String message = "") {
        String msg = message ?: "Expected '${actual}' to contain '${expectedPart}'"
        LogUtil.logAssert("[ASSERT] assertContains → ${msg}")
        Assert.assertTrue(msg, actual?.contains(expectedPart))
        LogUtil.logAssert("[ASSERT PASS] ${msg}")
    }

    static void assertNotContains(String actual, String unexpectedPart, String message = "") {
        String msg = message ?: "Expected '${actual}' NOT to contain '${unexpectedPart}'"
        LogUtil.logAssert("[ASSERT] assertNotContains → ${msg}")
        Assert.assertFalse(msg, actual?.contains(unexpectedPart))
        KeywordUtil.logInfo("[ASSERT PASS] ${msg}")
    }

    static void fail(String message) {
        KeywordUtil.markFailed("[ASSERT FAIL] ${message}")
        Assert.fail(message)
    }
}
