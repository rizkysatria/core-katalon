package core

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import utility.LogUtil
import core.context.ExecutionContext
import utility.Platform
import core.LocatorStore


public class LocatorResolver {

    static TestObject create(String key) {
        def testObject = new TestObject()
        def locator = LocatorStore.get(key)
        def platform = ExecutionContext.getPlatform()
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
        if (locator?.startsWith("//") || locator?.startsWith("(")) {
            to.addProperty("xpath", ConditionType.EQUALS, locator)
            return
        }
        if (isIOSAccessibilityId(locator)) {
            to.addProperty("name", ConditionType.EQUALS, locator)
            return
        }
        to.addProperty(
            "-ios predicate string",
            ConditionType.EQUALS,
            """
            (
                (type == 'XCUIElementTypeButton' AND (label == '${locator}' OR name == '${locator}'))
                OR
                (type IN {'XCUIElementTypeTextField','XCUIElementTypeSecureTextField'} AND name == '${locator}')
            )
            """.stripIndent().trim()
        )
    }


    private static boolean isIOSAccessibilityId(String locator) {
        return locator ==~ /^[A-Z][A-Za-z0-9]*([_-][A-Za-z0-9]+)+$/
    }

}