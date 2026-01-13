package core.adapter.android

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import core.types.SwipePosition

class AndroidSwipeExecutor {

    static void swipe(SwipePosition position, int times) {
        for (int i = 0; i < times; i++) {
            switch (position) {
                case SwipePosition.UP:
                    Mobile.swipe(500, 1500, 500, 300)
                    break
                case SwipePosition.DOWN:
                    Mobile.swipe(500, 300, 500, 1500)
                    break
                case SwipePosition.LEFT:
                    Mobile.swipe(900, 800, 100, 800)
                    break
                case SwipePosition.RIGHT:
                    Mobile.swipe(100, 800, 900, 800)
                    break
            }
        }
    }
}
