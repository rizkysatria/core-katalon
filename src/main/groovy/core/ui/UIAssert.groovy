package core.ui

import core.utility.AssertUtil
import core.ui.UIAction

class UIAssert {

    static void textEquals(String locatorKey, String expected, String message = "") {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertEquals(actual, expected, message)
    }

    static void textNotEquals(String locatorKey, String expected, String message = "") {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertNotEquals(actual, expected, message)
    }

    static void textContains(String locatorKey, String expected, String message = "") {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertContains(actual, expected, message)
    }

    static void textNotContains(String locatorKey, String expected, String message = "") {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertNotContains(actual, expected, message)
    }
}
