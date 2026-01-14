package core.scaffold.gherkin

class GherkinParser {

    static List<String> extractSteps(File featureFile) {
        featureFile.readLines()
            .collect { it.trim() }
            .findAll {
                it.startsWith("Given") ||
                it.startsWith("When")  ||
                it.startsWith("Then") ||
                it.startsWith("And")
            }
    }
}
