package core.ios

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import utility.LogUtil
import core.ios.IOSKeyboardManager
import core.ios.IOSOverlayGuard
import utility.Platform
import utility.Timeout
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.exception.StepFailedException
import core.UIAction

class IOSTapStrategy {

    static void tap(TestObject to, String locator, Timeout timeout) {

        LogUtil.info("📱 iOS Tap → ${locator}")

        if (tryTap(to, timeout)) return
        IOSKeyboardManager.dismiss()
        if (tryTap(to, timeout)) return
        IOSOverlayGuard.waitUntilClear()
        if (tryTap(to, timeout)) return
        UIAction.scrollUntilVisible()
        if (tryTap(to, timeout)) return
        if (IOSCoordinateTap.tapByElementFrame(to)) return

        throw new StepFailedException("❌ iOS Tap failed after auto-heal: ${locator}")
    }

    private static boolean tryTap(TestObject to, Timeout timeout) {
        if (!Mobile.verifyElementExist(to, timeout.seconds, FailureHandling.OPTIONAL)) {
            return false
        }

        Mobile.tap(to, timeout.seconds, FailureHandling.OPTIONAL)
        return true
    }
}
