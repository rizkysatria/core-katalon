package core.scaffold.structure

class TemplateFactory {

	static String usecaseTemplate(String featureName, String epic, Boolean withFolder = true) {
		String epicLowercase = epic.toLowerCase()
		String packageNamePath = "features.${featureName.toLowerCase()}.${epicLowercase}"
		String importScreenPath = "${packageNamePath}.${epic}Screen"

		if (withFolder) {
			packageNamePath = "features.${featureName.toLowerCase()}.${epicLowercase}.usecase"
			importScreenPath =  "features.${epicLowercase}.screen.${epic}Screen"
		}

		return """
package ${packageNamePath}

import ${importScreenPath}

class ${epic}Usecase {

	private final ${epic}Screen ${epicLowercase}Screen = new ${epic}Screen()

    def execute() {
        // TODO: implement usecase logic
    }
}
"""
	}

	static String screenTemplate(String featureName, String epic, Boolean withFolder = true) {
		String packageName = withFolder ? "package features.${featureName.toLowerCase()}.${epic.toLowerCase()}.screen" : "package features.${featureName.toLowerCase()}.${epic.toLowerCase()}"
		return """
${packageName}

class ${epic.capitalize()}Screen {

    def verifyScreen() {
        // TODO: implement screen verification
    }
}
"""
	}

	static String stepTemplate(String featureName, String epic, Boolean withFolder = true) {
		String epicLowercase = epic.toLowerCase()
		String packageNamePath = "features.${featureName.toLowerCase()}.${epic.toLowerCase()}"
		String importUsecasePath = "${packageNamePath}.${epic}Usecase"

		if (withFolder) {
			packageNamePath = "features.${featureName.toLowerCase()}.${epic.toLowerCase()}.stepDef"
			importUsecasePath = "features.${epicLowercase}.usecase.${epic}Usecase"
		}

		return """
package ${packageNamePath}

import ${importUsecasePath}

class ${epic}Step {
	
	private final ${epic}Usecase ${epicLowercase}Usecase = new ${epic}Usecase()

    def givenSomething() {
        // TODO: implement step definition
    }
}
"""
	}
}
