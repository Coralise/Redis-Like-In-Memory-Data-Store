package me.wayne;

class AssertUtil {

    private AssertUtil() {
    }
    
    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

}
