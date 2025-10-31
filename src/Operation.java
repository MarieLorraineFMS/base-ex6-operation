public final class Operation {
    private Operation() {
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static double sub(double a, double b) {
        return a - b;
    }

    public static double mul(double a, double b) {
        return a * b;
    }

    public static double div(double a, double b) {
        if (b == 0.0) {
            // SHOULD NOT APPEND
            throw new IllegalArgumentException("SHOULD NOT HAPPEN : Division by zero is not allowed.");
        }
        return a / b;
    }
}
