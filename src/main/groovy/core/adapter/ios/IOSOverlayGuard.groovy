package core.adapter.ios

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import core.utility.LogUtil

class IOSOverlayGuard {

    static void waitUntilClear(int maxWait = 3) {
        LogUtil.info("🧱 Waiting overlay to clear")

        TestObject overlay = new TestObject()
        overlay.addProperty(
            "-ios predicate string",
            ConditionType.EQUALS,
            "name == 'CenterPageView' OR name == 'SystemInputAssistantView'"
        )

        int waited = 0
        while (Mobile.verifyElementExist(overlay, 1, FailureHandling.OPTIONAL)) {
            if (waited++ >= maxWait) break
            Mobile.delay(1)
        }
    }
}
