package core.utility

import com.kms.katalon.core.util.KeywordUtil

class LogUtil {

    static void info(Object... msg) {
        KeywordUtil.logInfo(format("INFO", icon("INFO"), msg))
    }

    static void warn(Object... msg) {
        KeywordUtil.markWarning(format("WARN", icon("WARN"), msg))
    }

    static void error(Object... msg) {
        KeywordUtil.logError(format("ERROR", icon("ERROR"), msg))
    }

    static void infoAssert(Object... msg) { 
        KeywordUtil.logInfo(format("ASSERT", icon("ASSERT PASSED"), msg))
    }

    static void infoAssertFailed(Object... msg) {
        KeywordUtil.logInfo(format("ASSERT", icon("WARN"), msg))
        KeywordUtil.markWarning(format("ASSERT", icon("WARN"), msg))
    }

    private static String format(String level, String icon, Object... msg) {
        return "${icon} [${level}] " + msg.collect { it?.toString() }.join(" ")
    }

    private static String icon(String level) {
        switch(level) {
            case "ASSERT PASSED": return "✅"
            case "WARN": return "⚠️"
            case "ERROR": return "❌"
            default: return "ℹ️"
        }
    }
}
