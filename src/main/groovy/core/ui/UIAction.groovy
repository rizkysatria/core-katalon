package core.ui

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import core.interaction.TapHandler
import core.interaction.TextHandler
import core.types.SwipePosition
import core.types.Timeout

class UIAction {

    static void tap(String key, Timeout timeout = Timeout.MEDIUM) {
        TapHandler.handle(key, timeout)
    }

    static void tapWithRetry(String key, int retry = 2, Timeout timeout = Timeout.SHORT) {
        retry(retry) {
            TapHandler.handle(key, timeout)
        }
    }

    static void setText(String key, String text, Timeout timeout = Timeout.MEDIUM) {
        TextHandler.setText(key, text, timeout)
    }

    static String getText(String key, Timeout timeout = Timeout.MEDIUM) {
        return  TextHandler.getText(key, timeout)
    }

    static void pressBack() {
        Mobile.pressBack()
    }

    static void waitVeryShort() {
        wait(Timeout.VERY_SHORT)
    }

    static void waitShort() {
        wait(Timeout.SHORT)
    }

    static void waitMedium() {
        wait(Timeout.MEDIUM)
    }

    static void waitLong() {
        wait(Timeout.LONG)
    }

    static void waitVeryLong() {
        wait(Timeout.VERY_LONG)
    }

    //PRIVATE METHOD

    private static void wait(Timeout timeout) {
        Mobile.delay(timeout.seconds)
    }

    private static void retry(int count, Closure action) {
        Exception last
        count.times {
            try {
                action.call()
                return
            } catch (Exception e) {
                last = e
                LogUtil.warn("Retry failed:", e.message)
            }
        }
        throw last
    }
}
