package core.ui

import core.utility.AssertUtil
import core.ui.UIAction
import core.utility.AssertMode

class UIAssert {

    static void textEquals(String locatorKey, String expected, String message = "", AssertMode mode = AssertMode.SOFT) {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertEquals(actual, expected, message, mode)
    }

    static void textNotEquals(String locatorKey, String expected, String message = "", AssertMode mode = AssertMode.SOFT) {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertNotEquals(actual, expected, message, mode)
    }

    static void textContains(String locatorKey, String expected, String message = "", AssertMode mode = AssertMode.SOFT) {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertContains(actual, expected, message, mode)
    }

    static void textNotContains(String locatorKey, String expected, String message = "", AssertMode mode = AssertMode.SOFT) {
        String actual = UIAction.getText(locatorKey)
        AssertUtil.assertNotContains(actual, expected, message, mode)
    }
}
