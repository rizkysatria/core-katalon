package core.interaction

import core.types.SwipePosition
import core.types.Timeout
import core.runtime.profile.ExecutionProfile
import core.types.Platform
import core.types.SwipePosition
import core.runtime.locator.LocatorResolver
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.testobject.ConditionType
import core.adapter.ios.IOSSwipeExecutor
import core.adapter.android.AndroidSwipeExecutor

class SwipeHandler {

    static void execute(
        SwipePosition position,
        int times = 1,
        double startPct = 0.8,
        double endPct = 0.2
    ) {
        if (ExecutionProfile.isIOS()) {
            IOSSwipeExecutor.swipe(position, times, startPct, endPct)
        } else {
            AndroidSwipeExecutor.swipe(position, times)
        }
    }
    
    static boolean executeUntilVisible(
        String key,
        SwipePosition position,
        int maxTimes = 5,
        double startPct = 0.8,
        double endPct = 0.2,
        Timeout timeout = Timeout.SHORT
    ) {
        def to = LocatorResolver.create(key)

        for (int i = 0; i < maxTimes; i++) {
            if (Mobile.waitForElementVisible(to, timeout.seconds, FailureHandling.OPTIONAL)) {
                return true
            }

            execute(position, 1, startPct, endPct)
        }

        return false
    }
}
