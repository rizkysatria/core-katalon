package core.scaffold

import com.kms.katalon.core.annotation.Keyword
import core.scaffold.structure.FeatureGenerator
import core.scaffold.structure.EpicGenerator
import core.scaffold.gherkin.GherkinParser
import core.scaffold.gherkin.StepDefinitionGenerator
import com.kms.katalon.core.configuration.RunConfiguration

class ScaffoldTrigger {

	static void generateFeature(String featureName) {
		FeatureGenerator.generate(featureName)
	}

	/**
	 * Generate file LAYER mode
	 * features 
	 * 	{epic}
	 * 		usecase 
	 * 			{Epic}Usecase.groovy
	 * 		stepDef
	 * 			{Epic}Step.groovy
	 * 		screen 
	 * 			{Epic}Screen.groovy
	 * @param featureName
	 * @param epic
	 */
	static void generateEpic(String featureName, String epicName) {
		EpicGenerator.generate(featureName, epicName, true)
	}

	/**
	 * Generate file FLAT mode
	 * features 
	 * 	- {epic}
	 * 		{Epic}Usecase.groovy
	 * 		{Epic}Step.groovy
	 * 		{Epic}Screen.groovy
	 * @param featureName
	 * @param base
	 * @param epic
	 */
	static void generateEpicWithoutFolder(String featureName, String epicName) {
		EpicGenerator.generate(featureName, epicName, false)
	}

	static void generateStepsFromFeature(Map args) {		
		String projectDir = RunConfiguration.getProjectDir()

        File featureFile = new File(projectDir, args.featurePath)
        File stepDefFile = new File(projectDir, args.stepDefPath)
        assert featureFile.exists()
        assert stepDefFile.exists()

        def steps = GherkinParser.extractSteps(featureFile)

        StepDefinitionGenerator.generateAndAppend(steps, stepDefFile)
    }
}
