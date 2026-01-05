package core

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import core.context.ExecutionContext

public class AppManager {

    static void killApp() {
        try {
            Mobile.closeApplication()
        } catch (Exception e) {
            println "App already terminated"
        }
    }

    static void launchApp(String appPath) {
        if (ExecutionContext.isIOS()) {
            waitSimulatorReady()
            killWDA()
            Mobile.startApplication(appPath, false)
        } else {
            Mobile.startApplication(appPath, false)
        }
    }

    /**
     * Blocks execution until the currently booted iOS Simulator
     * reaches a fully ready state.
     *
     * This prevents Appium from attempting to start WebDriverAgent
     * while the simulator is still initializing, which commonly
     * leads to connection failures.
     *
     * Uses:
     *   xcrun simctl bootstatus booted -b
     */
    private static void waitSimulatorReady() {
		def cmd = "xcrun simctl bootstatus booted -b"
		def proc = cmd.execute()
		proc.waitFor()
	}

    /**
     * Terminates any running WebDriverAgentRunner process.
     *
     * This is a defensive cleanup step to eliminate zombie WDA
     * instances that may still be holding network ports,
     * causing ECONNREFUSED errors during Appium startup.
     *
     * Safe to call even when no WDA process exists.
     */
	private static void killWDA() {
		try {
			"pkill -f WebDriverAgentRunner".execute()
			sleep(2000)
		} catch (Exception ignored) {
			// safe to ignore
		}
	}
}
