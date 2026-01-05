package utility

enum Timeout {

    VERY_SHORT(1),
    SHORT(3),
    MEDIUM(5),
    LONG(10),
    VERY_LONG(20)

    final int seconds

    Timeout(int seconds) {
        this.seconds = seconds
    }
}