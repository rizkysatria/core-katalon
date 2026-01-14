package core.scaffold.gherkin

class StepDefinitionGenerator {

    static void generateAndAppend(List<String> steps, File stepDefFile) {
        def existing = existingMethods(stepDefFile)
        def methods = generateMissingMethods(steps, existing)

        append(stepDefFile, methods)
    }

    private static Set<String> existingMethods(File stepDefFile) {
        stepDefFile.text
            .findAll(/def\s+([a-zA-Z0-9_]+)\s*\(/)
            .collect { it[1] }
            .toSet()
    }

    private static String generateMissingMethods(List<String> steps, Set<String> existing) {
        steps.collect { step ->
            def keyword = step.split(" ")[0]
            def body = step.substring(keyword.length()).trim()

            def method = methodName(body)
            if (existing.contains(method)) return null

            def hasParam = body.contains('"')

                    """
            @${keyword}("${regex(body)}")
            def ${method}(${hasParam ? "String value" : ""}) {
                // TODO: implement
            }
        """
        }.findAll { it != null }.join("\n")
    }

    private static void append(File stepDefFile, String methods) {
        if (!methods?.trim()) return

        stepDefFile.text = stepDefFile.text.replaceFirst(
            /\}\s*$/,
            methods + "\n}"
        )
    }

    private static String methodName(String step) {
        step
            .replaceAll(/"(.*?)"/, "")
            .replaceAll(/[^a-zA-Z ]/, "")
            .trim()
            .split(/\s+/)
            .collect { it.capitalize() }
            .join("")
            .uncapitalize()
    }

    private static String regex(String step) {
        step.replaceAll(/"(.*?)"/, "(.*)")
    }
}
