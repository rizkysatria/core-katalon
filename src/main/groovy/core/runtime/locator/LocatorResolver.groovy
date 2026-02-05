package core.runtime.locator

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import core.utility.LogUtil
import core.runtime.profile.ExecutionProfile
import core.types.Platform
import core.runtime.locator.LocatorRegistry

public class LocatorResolver {

    static TestObject create(String key) {
        def testObject = new TestObject()
        def locator = LocatorRegistry.resolve(key)
        def platform = ExecutionProfile.getPlatform()   
        if (platform == Platform.IOS) {
            applyIOSLocator(testObject, locator)
        } else {
            applyAndroidLocator(testObject, locator)
        }
        return testObject
    }
    
    private static void applyAndroidLocator(TestObject to, String locator) {
        if (locator?.startsWith("//") || locator?.startsWith("(")) {
            to.addProperty("xpath", ConditionType.EQUALS, locator)
        } else if (locator?.contains("new UiSelector")) {
            to.addProperty("-android uiautomator", ConditionType.EQUALS, locator)
        } else if (locator?.startsWith("id=")) {
            to.addProperty("resource-id", ConditionType.EQUALS, locator.replace("id=", ""))
        } else if (locator?.contains(":id/")) {
            to.addProperty("resource-id", ConditionType.EQUALS, locator)
        } else if (isAndroidContentDesc(locator)) {
            to.addProperty("content-desc", ConditionType.EQUALS, locator)
        } else {
            to.addProperty("text", ConditionType.EQUALS, locator)
        }
    }

    private static boolean isAndroidContentDesc(String locator) {
        return locator ==~ /^[A-Z][A-Za-z0-9]*(_[A-Za-z0-9]+)+$/    
    }
    
    private static void applyIOSLocator(TestObject to, String locator) {
        if (!locator) return

        if (locator.startsWith("//") || locator.startsWith("(")) {
            to.addProperty("xpath", ConditionType.EQUALS, locator)
            return
        }

        if (isIOSAccessibilityId(locator)) {
            to.addProperty("name", ConditionType.EQUALS, locator)
            return
        }

        String classChain = """
        **/*[
            label == '${locator}'
            OR name == '${locator}'
            OR value == '${locator}'
            OR placeholderValue == '${locator}'
        ]
        """.stripIndent().trim()

        to.addProperty("-ios class chain", ConditionType.EQUALS, classChain)
    }


    private static boolean isIOSAccessibilityId(String locator) {
        return locator ==~ /^[A-Z][A-Za-z0-9]*([_-][A-Za-z0-9]+)+$/
    }

}