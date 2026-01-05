package core

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.util.KeywordUtil
import utility.LogUtil
import core.context.ExecutionContext
import core.ios.IOSTapStrategy
import utility.SwipePosition
import utility.Platform
import utility.Timeout
import core.ios.IOSKeyboardManager
import core.LocatorStore

class UIAction {

    static int SCROLL_START_X = 500
    static int SCROLL_END_X = 500
    static int SCROLL_UP_START_Y = 1500
    static int SCROLL_UP_END_Y = 300

    /**
    * Waits for an element to be present on screen using a platform-specific strategy.
    * This method abstracts the difference between iOS and Android element
    * synchronization behavior:
    *
    * - On iOS:
    *   Uses `verifyElementExist` because iOS elements may exist in the hierarchy
    *   but are not immediately "present" in the same way as Android.
    *
    * - On Android:
    *   Uses `waitForElementPresent`, which is more reliable and performant
    *   on Android UI hierarchies.
    *
    * @param to the TestObject representing the target UI element
    * @param timeout the timeout configuration (default: MEDIUM)
    * @return true if the element exists or becomes present within the timeout, false otherwise
    */
    static boolean waitForElementPresent(TestObject to, Timeout timeout = Timeout.MEDIUM) {
        boolean isVisible = false 
        
         if (ExecutionContext.getPlatform() == Platform.IOS) {
            isVisible = Mobile.verifyElementExist(to, timeout.seconds, FailureHandling.OPTIONAL)
        } else {
            isVisible = Mobile.waitForElementPresent(to, timeout.seconds, FailureHandling.OPTIONAL)
        }
        if (!isVisible) {
            LogUtil.info("Element not immediately visible, triggering scroll:", to.getObjectId())
        }
        return isVisible
    }   

    /**
    * Performs a vertical swipe gesture on iOS devices using percentage-based coordinates.
    * This method is designed specifically for iOS because:
    * - iOS swipe behavior is highly sensitive to absolute screen coordinates
    * - Percentage-based calculation ensures consistency across different screen sizes
    * The gesture can be repeated multiple times to support progressive scrolling.
    * @param position the swipe direction (TOP or BOTTOM)
    * @param times number of swipe repetitions
    * @param startPct starting Y position as a percentage of screen height
    * @param endPct ending Y position as a percentage of screen height
    */
    private static void swipeIOS(SwipePosition position, int times, double startPct, double endPct) {
        def driver = MobileDriverFactory.getDriver()
        def size = driver.manage().window().getSize()
        int w = size.getWidth()
        int h = size.getHeight()

        int startX = (int)(w * 0.5)
        int startY = 0
        int endY = 0

        switch(position) {
            case SwipePosition.TOP:
                startY = (int)(h * startPct)
                endY = (int)(h * endPct)
                break
            case SwipePosition.BOTTOM:
               startY = (int)(h * endPct)
                endY = (int)(h * startPct)
                break
            default:
                startY = (int)(h * startPct)
                endY = (int)(h * endPct)
        }

        for (int i = 1; i <= times; i++) {
            KeywordUtil.logInfo("🔄 Swipe ${position} ke-${i} (startX=${startX}, startY=${startY} -> endY=${endY})")
            Mobile.swipe(startX, startY, startX, endY)
        }
    }

    /**
    * Performs a vertical swipe gesture on Android devices using fixed coordinate values.
    * This method is Android-specific and intentionally uses predefined constants
    * instead of dynamic screen percentage calculations because:
    * - Android swipe behavior is generally more tolerant to absolute coordinates
    * - Fixed coordinates provide more stable and predictable results across
    *   different Android devices and UI hierarchies
    * @param position the swipe direction (TOP or BOTTOM)
    * @param times number of swipe repetitions (currently handled externally)
    * @param startPct unused for Android (kept for API consistency with iOS)
    * @param endPct unused for Android (kept for API consistency with iOS)
    */
    private static void swipeAndroid(SwipePosition position, int times, double startPct, double endPct) {
        switch(position) {
            case SwipePosition.TOP:
                 try {
                    Mobile.swipe(SCROLL_START_X, SCROLL_UP_START_Y, SCROLL_END_X, SCROLL_UP_END_Y)
                    LogUtil.info("Scrolled Up")
                } catch (Exception e) {
                    LogUtil.warn("Scroll Up failed:", e.message)
                }
                break
            case SwipePosition.BOTTOM:
               try {
                    Mobile.swipe(SCROLL_START_X, SCROLL_UP_END_Y, SCROLL_END_X, SCROLL_UP_START_Y)
                    LogUtil.info("Scrolled Down")
                } catch (Exception e) {
                    LogUtil.warn("Scroll Down failed:", e.message)
                }
                break
            default:
                try {
                    Mobile.swipe(SCROLL_START_X, SCROLL_UP_START_Y, SCROLL_END_X, SCROLL_UP_END_Y)
                } catch (Exception ignored) {}
        }
    }

    /**
     * Attempts to scroll vertically until the element becomes visible or max attempts reached.
     * Returns true if element becomes visible.
     *
     * @param locator locator string
     * @param timeout timeout value per scroll check
     * @param maxScroll maximum number of scroll attempts
     * @return true if element is found visible
     */
    static boolean scrollUntilVisible(String key, Timeout timeout = Timeout.MEDIUM, int maxScroll = 5) {
        def testObject = LocatorResolver.create(key)
        int attempt = 0
        while (attempt < maxScroll) {
            if (Mobile.waitForElementPresent(testObject, 2, FailureHandling.OPTIONAL)) {
                return true
            }
            try {
                Mobile.swipe(SCROLL_START_X, SCROLL_UP_START_Y, SCROLL_END_X, SCROLL_UP_END_Y)
            } catch (Exception e) {
                LogUtil.warn("Scroll failed at attempt ${attempt + 1}:", e.message)
                break
            }
            attempt++
        }
        return false
    }

    /**
     * Taps the element if it's visible or becomes visible after scroll.
     *
     * @param key locator string
     * @param timeout Timeout value
     */
    static void tap(String key, Timeout timeout = Timeout.VERY_SHORT) {
        def testObject = LocatorResolver.create(key)
        try {
            Mobile.tap(testObject, timeout.seconds)
            return
        } catch (Exception ignore) {}


        if (ExecutionContext.isIOS()) {
            IOSTapStrategy.tap(testObject, timeout)
            return
        } else {
            waitForElementPresent(testObject, timeout)
            Mobile.tap(testObject, timeout.seconds)
             return 
        }

        scrollUntilVisible(testObject, timeout)
        Mobile.tap(testObject, timeout.seconds)
    }

    /**
     * Sets text to a field. If text mismatch occurs, retries after clearing.
     *
     * @param key locator string
     * @param text text to set
     * @param timeout Timeout value
     */
    static void setText(String key, String text, Timeout timeout = Timeout.VERY_SHORT) {
        def testObject = LocatorResolver.create(key)
        try {
            Mobile.setText(testObject, text, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
            return
        } catch (Exception ignore) {}

        // try {
        //     waitForElementPresent(testObject, timeout)
        //     Mobile.setText(testObject, text, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
        //     return
        // } catch (Exception ignore) {}



        if (MobileDriverFactory.getDriver() == null) {
            throw new IllegalStateException("Driver is NULL before setText")
        }

        // ✅ MOBILE-VALID waits
        Mobile.waitForElementPresent(testObject, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
        Mobile.waitForElementVisible(testObject, timeout.seconds, FailureHandling.STOP_ON_FAILURE)

        // ✅ SET TEXT
        Mobile.setText(testObject, text, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
    }

    static void closeKeyboardIfNeeded() {
        def platform = ExecutionContext.getPlatform()
        if (platform == Platform.IOS) {
            IOSKeyboardManager.dismiss()
            waitUntilGone(Timeout.VERY_SHORT.seconds)
        } else {
            Mobile.pressBack(FailureHandling.OPTIONAL)
        }
    }
    /**
     * Returns the text from an element if visible, otherwise returns null.
     *
     * @param key locator string
     * @param timeout Timeout value
     * @return the text content or null
     */
    static String getText(String key, Timeout timeout = Timeout.MEDIUM) {
        def testObject = LocatorResolver.create(key)
        if (waitForElementPresent(testObject, timeout)) {
            def platform = ExecutionContext.getPlatform()
            if (platform == Platform.IOS) {
                String value = Mobile.getAttribute(testObject, "value", timeout.seconds, FailureHandling.OPTIONAL)
                LogUtil.info("Get Text:", key, "=", value)
                return value
            } else {
                String value = Mobile.getText(testObject, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
                LogUtil.info("Get Text:", key, "=", value)
                return value
            }
        }
        return null
    }

    /**
     * Clears text of an input field if visible.
     *
     * @param key locator string
     * @param timeout Timeout value
     */
    static void clearText(String key, Timeout timeout = Timeout.MEDIUM) {
        def testObject = LocatorResolver.create(key)
        if (waitForElementPresent(testObject, timeout)) {
            Mobile.clearText(testObject, timeout.seconds, FailureHandling.STOP_ON_FAILURE)
            LogUtil.info("Clear Text:", key)
        }
    }
    /**
     * Scrolls vertically until the given text appears on the screen.
     *
     * @param text the visible text to scroll to
     */
    static void scrollToText(String text) {
        try {
            Mobile.scrollToText(text)
            LogUtil.info("Scrolled to Text:", text)
        } catch (Exception e) {
            LogUtil.warn("Scroll to Text failed:", e.message)
        }    
    }

    static void waitUntilGone(int timeoutSec = Timeout.SHORT.seconds) {
        TestObject overlay = new TestObject("ios_overlay")
        overlay.addProperty("name", ConditionType.EQUALS, "CenterPageView")
        int waited = 0
        while (waited < timeoutSec) {
            boolean exists = Mobile.verifyElementExist(
                overlay,
                1,
                FailureHandling.OPTIONAL
            )

            if (!exists) {
                LogUtil.info("[iOS] System overlay cleared")
                return
            }

            LogUtil.warn("[iOS] Waiting system overlay to disappear...")
            Mobile.delay(Timeout.VERY_SHORT.seconds)
            waited++
        }
        LogUtil.warn("[iOS] Overlay still present after timeout, continue anyway")
    }


    static void swipe(SwipePosition position, int times = 5, double startPct = 0.75, double endPct = 0.25) {
        if (ExecutionContext.isIOS()) {
            swipeIOS(position, times, startPct, endPct)
        } else {
            swipeAndroid(position, times, startPct, endPct)
        }
    }


    static void swipeToElement(String key, SwipePosition position, int times = 5, double startPct = 0.75, double endPct = 0.25) {
        def testObject = LocatorResolver.create(key)
        if (waitForElementPresent(testObject, Timeout.MEDIUM)) {
            swipe(position, times, startPct, endPct)
        }
    }

}