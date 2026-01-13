package core.adapter.ios

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import core.utility.LogUtil
import core.adapter.ios.IOSKeyboardManager
import core.adapter.ios.IOSOverlayGuard
import core.types.Platform
import core.types.Timeout
import core.ui.UIAction

class IOSTapStrategy {

    static void tap(TestObject to, String locator, Timeout timeout) {

        LogUtil.info("📱 iOS Tap → ${locator}")

        if (tryTap(to, timeout)) return
        IOSKeyboardManager.dismiss()
        if (tryTap(to, timeout)) return
        IOSOverlayGuard.waitUntilClear()
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
