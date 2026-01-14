package core.scaffold.structure

import core.scaffold.structure.PathResolver

class FeatureGenerator {

	static void generate(String featureName) {

		String featurePath = PathResolver.featurePath(featureName)

		File featureDir = new File(featurePath)
		if (!featureDir.exists()) {
			featureDir.mkdirs()
		}

		println "✅ Feature created: ${featurePath}"
	}
}
