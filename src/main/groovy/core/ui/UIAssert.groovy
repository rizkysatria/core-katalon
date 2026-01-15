package core.ui

import core.utility.AssertUtil
import core.ui.UIAction

class UIAssert {

    static void textEquals(String locatorKey, String expected, String message = "") {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertEquals(actual, expected, message)
    }

    static void textContains(String locatorKey, String expected, String message = "") {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertContains(actual, expected, message)
    }
}
