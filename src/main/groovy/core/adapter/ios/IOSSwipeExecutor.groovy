package core.adapter.ios

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import core.types.SwipePosition
import org.openqa.selenium.Dimension

class IOSSwipeExecutor {

    static void swipe(
        SwipePosition position,
        int times,
        double startPct,
        double endPct
    ) {
        def driver = MobileDriverFactory.getDriver()
        Dimension size = driver.manage().window().size

        int width = size.width
        int height = size.height

        for (int i = 0; i < times; i++) {
            int startX, startY, endX, endY

            switch (position) {
                case SwipePosition.UP:
                    startX = width / 2
                    startY = (int)(height * startPct)
                    endX   = width / 2
                    endY   = (int)(height * endPct)
                    break

                case SwipePosition.DOWN:
                    startX = width / 2
                    startY = (int)(height * endPct)
                    endX   = width / 2
                    endY   = (int)(height * startPct)
                    break

                case SwipePosition.LEFT:
                    startX = (int)(width * startPct)
                    startY = height / 2
                    endX   = (int)(width * endPct)
                    endY   = height / 2
                    break

                case SwipePosition.RIGHT:
                    startX = (int)(width * endPct)
                    startY = height / 2
                    endX   = (int)(width * startPct)
                    endY   = height / 2
                    break
            }

            Mobile.swipe(startX, startY, endX, endY)
            Mobile.delay(0.3)
        }
    }
}
