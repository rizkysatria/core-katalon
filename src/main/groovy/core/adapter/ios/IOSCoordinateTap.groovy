package core.adapter.ios

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import core.utility.LogUtil

class IOSCoordinateTap {

    static boolean tapByElementFrame(TestObject to) {
        try {
            def el = Mobile.findElement(to, 1)
            int x = el.rect.x + (el.rect.width / 2)
            int y = el.rect.y + (el.rect.height / 2)

            LogUtil.warn("🎯 Coordinate tap at (${x}, ${y})")
            Mobile.tapAtPosition(x, y)
            return true
        } catch (Exception e) {
            return false
        }
    }
}
