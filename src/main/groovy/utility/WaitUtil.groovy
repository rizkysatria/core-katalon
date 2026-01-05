package utility

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import core.LocatorResolver
import utility.Timeout

public class WaitUtil {
    static boolean waitForVisible(
        String locator,
        Timeout timeout,
        FailureHandling failureHandling = FailureHandling.OPTIONAL
    ) {
        def testObject = LocatorResolver.create(locator)
        return Mobile.verifyElementVisible(
            testObject,
            timeout.seconds,
            failureHandling
        )
    }
    static void waitForNotVisible(
        String locator,
        Timeout timeout
    ) {
        def testObject = LocatorResolver.create(locator)
        Mobile.waitForElementNotVisible(
            testObject,
            timeout.seconds,
            FailureHandling.OPTIONAL
        )
    }
}
