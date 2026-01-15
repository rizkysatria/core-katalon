package core.utility

import com.kms.katalon.core.util.KeywordUtil

class LogUtil {

    static void info(Object... msg) {
        KeywordUtil.logInfo(format("INFO", msg))
    }

    static void warn(Object... msg) {
        KeywordUtil.logWarning(format("WARN", msg))
    }

    static void error(Object... msg) {
        KeywordUtil.logError(format("ERROR", msg))
    }

    static void infoAssert(Object... msg) {
        KeywordUtil.logInfo(format("ASSERT", msg))
    }

    static void infoAssertFailed(Object... msg) {
        KeywordUtil.logWarning(format("ASSERT", msg))
    }

    private static String format(String level, Object... msg) {
        return "[${level}] " + msg.collect { it?.toString() }.join(" ")
    }
}
