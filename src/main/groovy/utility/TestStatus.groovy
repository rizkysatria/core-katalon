package utility

public enum TestStatus {
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    final String value

    TestStatus(String value) {
        this.value = value
    }
}
