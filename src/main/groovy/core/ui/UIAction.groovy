package core.ui

import core.interaction.TapHandler
import core.interaction.TextHandler
import core.types.Timeout
import core.types.SwipePosition

class UIAction {

    static void tap(String key, Timeout timeout = Timeout.MEDIUM) {
        TapHandler.handle(key, timeout)
    }

    static void setText(String key, String text, Timeout timeout = Timeout.MEDIUM) {
        TextHandler.setText(key, text, timeout)
    }

    static String getText(String key, Timeout timeout = Timeout.MEDIUM) {
        return  TextHandler.getText(key, timeout)
    }

    static void swipe(
        SwipePosition position,
        int times = 5,
        double startPct = 0.75,
        double endPct = 0.25
    ) {
        // SwipeUseCase.execute(position, times, startPct, endPct)
    }

    static void swipeToElement(
        String key,
        SwipePosition position,
        int times = 5,
        double startPct = 0.75,
        double endPct = 0.25
    ) {
        // SwipeUseCase.executeUntilVisible(key, position, times, startPct, endPct)
    }
}
