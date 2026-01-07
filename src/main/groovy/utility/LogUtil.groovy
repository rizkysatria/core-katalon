package utility

import com.kms.katalon.core.util.KeywordUtil

import java.text.SimpleDateFormat

/**
 * LogUtil - Standardized logger utility for automation steps
 *
 * Supports log levels:
 *  - info(...)  ==> General step information
 *  - warn(...)  ==> Suspicious conditions that don't fail the test
 *  - error(...) ==> Critical failures or unexpected states
 *  - debug(...) ==> Internal debug output, use for troubleshooting
 *
 * Enhancements:
 *  - TimeStamp prefix (default: enabled)
 *  - Feature/module context label (optional)
 *  - Conditional debug logging via GlobalVariable.debugMode
 */
class LogUtil {

    // Enable or disable TimeStamp prefix
    static boolean includeTimestamp = true

    // Optional context (e.g., "Login", "Payment", etc.)
    static String currentContext = ""

    // Debug mode toggle (set in profile/global)
    // Add GlobalVariable.getGlobalVariable("debugMode") ?: false
    static boolean debugEnabled = false
    
    static void setContext(String context) {
        currentContext = context
    }

    static void info(Object... messages) {
        log("INFO", messages)
    }

    static void warn(Object... messages) {
        log("WARN", messages)
    }

    static void error(Object... messages) {
        log("ERROR", messages)
    }

    static void debug(Object... messages) {
        if (debugEnabled) {
            log("DEBUG", messages)
        }
    }

    static void logAssert(Object... messages) {
        if (debugEnabled) {
            log("ASSERT", messages)
        }
    }

    private static void log(String level, Object... messages) {
        String timestamp = includeTimestamp ? new SimpleDateFormat("HH:mm:ss").format(new Date()) + " " : ""
        String contextPart = currentContext ? "[$currentContext] " : ""
        String fullMessage = "${timestamp}[${level}] ==> ${contextPart}" + messages.collect { it?.toString() ?: "null" }.join(" ")
        KeywordUtil.logInfo(fullMessage)
    }
}
