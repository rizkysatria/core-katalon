package core.scaffold.gherkin

class StepNameFormatter {

    static String methodName(String step) {
        step
            .replaceAll(/"(.*?)"/, "")
            .replaceAll(/[^a-zA-Z ]/, "")
            .trim()
            .split(/\s+/)
            .collect { it.capitalize() }
            .join("")
            .uncapitalize()
    }

    static boolean hasParam(String step) {
        step.contains('"')
    }

    static String regex(String step) {
        step.replaceAll(/"(.*?)"/, "(.*)")
    }
}
