package core.utility

class RandomUtil {

    static int random(int countData) {
        return Random().nextInt(countData) + 1
    }

}