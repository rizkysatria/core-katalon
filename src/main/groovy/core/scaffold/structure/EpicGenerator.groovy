package core.scaffold.structure

import core.scaffold.structure.PathResolver
import core.scaffold.structure.TemplateFactory
import com.kms.katalon.core.annotation.Keyword	

class EpicGenerator {

	static void generate(String featureName, String epicName, Boolean withFolder = true) {
		String epicToCamelCase = toCamelCase(epicName)
		String epicBase = PathResolver.epicBasePath(featureName, epicToCamelCase)
		if (withFolder) {
			generateWithFolder(featureName, epicBase, epicName)
		} else {
			generateWithoutFolder(featureName,epicBase, epicName)
		}
		println "✅ Epic created: ${epicName}"
	}

	private static void generateWithoutFolder(String featureName, String base, String epic) {
		File baseDir = new File(base)
		if (!baseDir.exists()) {
			baseDir.mkdirs()
		}

		new File(base, "${epic}Usecase.groovy")
				.text = TemplateFactory.usecaseTemplate(featureName, epic, false)

		new File(base, "${epic}Screen.groovy")
				.text = TemplateFactory.screenTemplate(featureName, epic, false)

		new File(base, "${epic}Step.groovy")
				.text = TemplateFactory.stepTemplate(featureName, epic, false)
	}

	private static void generateWithFolder(String featureName, String base, String epic) {
		def usecaseDir = new File("${base}/usecase")
		def screenDir  = new File("${base}/screen")
		def stepDir    = new File("${base}/stepDef")

		[
			usecaseDir,
			screenDir,
			stepDir
		].each { it.mkdirs() }

		new File(usecaseDir, "${epic}Usecase.groovy")
				.text = TemplateFactory.usecaseTemplate(featureName, epic)

		new File(screenDir, "${epic}Screen.groovy")
				.text = TemplateFactory.screenTemplate(featureName, epic)

		new File(stepDir, "${epic}Step.groovy")
				.text = TemplateFactory.stepTemplate(featureName, epic)
	}

	private static String toCamelCase(String input) {
		if (!input) return input
			return input[0].toLowerCase() + input.substring(1)
	}
}
