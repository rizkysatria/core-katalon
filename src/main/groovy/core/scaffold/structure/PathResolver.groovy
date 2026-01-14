package core.scaffold.structure

class PathResolver {

	static String ROOT = "Include/scripts/groovy/features"

	static String featurePath(String feature) {
		return "${ROOT}/${feature}"
	}

	static String epicBasePath(String feature, String epic) {
		return "${featurePath(feature)}/${epic}"
	}

	static String usecasePath(String epicBase) {
		return "${epicBase}/usecase"
	}

	static String screenPath(String epicBase) {
		return "${epicBase}/screen"
	}

	static String stepDefPath(String epicBase) {
		return "${epicBase}/stepDef"
	}
}
