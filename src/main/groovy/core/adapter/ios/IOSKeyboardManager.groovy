package core.adapter.ios

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import core.runtime.profile.ExecutionProfile
import core.types.Timeout
import core.utility.LogUtil

class IOSKeyboardManager {

    static void dismiss() {
        
        if (!ExecutionProfile.isIOS()) return

        LogUtil.info("[iOS] Attempting to dismiss keyboard")

        try {
            Mobile.hideKeyboard()
            Mobile.delay(Timeout.VERY_SHORT.seconds)
            LogUtil.info("[iOS] hideKeyboard() executed")
            return
        } catch (Exception ignored) {}

        try {
            int width = Mobile.getDeviceWidth()
            int height = Mobile.getDeviceHeight()

            int tapX = width / 2
            int tapY = (int) (height * 0.15)

            Mobile.tapAtPosition(tapX, tapY)
            Mobile.delay(Timeout.VERY_SHORT.seconds)

            LogUtil.info("[iOS] Keyboard dismissed by tapping safe area")
            return
        } catch (Exception ignored) {}

        try {
            TestObject done = new TestObject("ios_done")
            done.addProperty("name", ConditionType.MATCHES, "(Done|Return|OK)")

            if (Mobile.verifyElementExist(done, Timeout.VERY_SHORT.seconds, FailureHandling.OPTIONAL)) {
                Mobile.tap(done, Timeout.VERY_SHORT.seconds)
                LogUtil.info("[iOS] Keyboard dismissed via Done/Return")
                return
            }
        } catch (Exception ignored) {}

        try {
            int w = Mobile.getDeviceWidth()
            int h = Mobile.getDeviceHeight()

            Mobile.swipe(
                w / 2,
                (int)(h * 0.75),
                w / 2,
                (int)(h * 0.9)
            )

            Mobile.delay(Timeout.VERY_SHORT.seconds)
            LogUtil.info("[iOS] Keyboard dismissed via swipe")
            return
        } catch (Exception ignored) {}

        LogUtil.warn("[iOS] Keyboard may still be visible (all strategies failed)")
    }
}
