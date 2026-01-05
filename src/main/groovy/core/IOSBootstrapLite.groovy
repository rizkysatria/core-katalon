package core

public class IOSBootstrapLite {

    static void prepare() {
        try {
            def check = "xcrun simctl list devices | grep Booted".execute()
            check.waitFor()

            if (check.exitValue() == 0) {
                println "iOS simulator already booted"
                return
            }

            println "No booted simulator found, booting default simulator..."
            "xcrun simctl boot booted".execute()
            sleep(5000)
        } catch (Exception e) {
            println "Skip simulator bootstrap (non-mac or CI limitation)"
        }
    }
}
