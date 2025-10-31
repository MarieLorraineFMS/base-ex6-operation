public final class Visual {
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";

    private static final int DELAY_MS = 500;

    private static String fmt(double value) {
        if (Double.isInfinite(value))
            return value > 0 ? "∞" : "-∞";
        if (Double.isNaN(value))
            return "NaN";
        String str = (Math.abs(value) >= 1e6 || Math.abs(value) < 1e-6)
                ? String.format("%.6g", value)
                : String.format("%.6f", value);
        return str.replaceAll("\\.?0+$", "");
    }

    ////////////////// Timing /////////////////////////

    public static void sleep() {

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException ignored) {
        }
    }

    ////////////////// [6.1] OPERATION STEPS /////////////////////////

    public static void showOpStep(String label, double a, String op, double b, double result) {
        // Valid
        String eq = a + " " + op + " " + b + " = " + YELLOW + result + RESET;
        System.out.println(CYAN + "Step " + label + ": " + RESET + eq);
        sleep();
    }

    public static void showOpError(String label, double a, String op, double b, String message) {
        // !valid
        String eq = a + " " + op + " " + b + " = " + RED + message + RESET;
        System.out.println(CYAN + "Step " + label + ": " + RESET + eq);
        sleep();
    }

    ////////////////// [6.2] TRIANGLE ANIMATION /////////////////////

    public static void animateIsoTriangle(int height) {
        // Grow triangle line by line using String.repeat
        for (int i = 1; i <= height; i++) {
            String spaces = " ".repeat(height - i);
            String stars = "*".repeat(2 * i - 1);
            System.out.println(spaces + stars);
            sleep();
        }
    }

    public static void animateRightTriangle(int height) {
        // Grow rectangle-triangle using nested loops
        for (int i = 1; i <= height; i++) {
            for (int s = 0; s < (height - i); s++)
                System.out.print(" ");
            for (int a = 0; a < i; a++)
                System.out.print("*");
            System.out.println();
            sleep();
        }
    }

    /////////////////// [6.3] LARGEST NUMBERS & SUM ////////////////////

    // YELLOW for current element, CYAN for current best
    private static void printArrayHighlight(double[] arr, int currIdx, int bestIdx, String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(" [ ");
        for (int i = 0; i < arr.length; i++) {
            boolean isCurr = (i == currIdx);
            boolean isBest = (i == bestIdx);
            if (isCurr && isBest) {
                // Current equals best (first step) -> highlight with both colors
                sb.append("[").append(YELLOW).append(CYAN).append(fmt(arr[i]))
                        .append(RESET).append("]");
            } else if (isCurr) {
                sb.append("[").append(YELLOW).append(fmt(arr[i])).append(RESET).append("]");
            } else if (isBest) {
                sb.append("[").append(CYAN).append(fmt(arr[i])).append(RESET).append("]");
            } else {
                sb.append(fmt(arr[i]));
            }
            if (i < arr.length - 1)
                sb.append(", ");
        }
        sb.append(" ]");
        System.out.println(sb);
    }

    public static double sumTwoLargestPassesWithSteps(double[] arr) {
        if (arr == null || arr.length < 2) {
            throw new IllegalArgumentException("Array must contain at least two elements.");
        }

        // ---------- PASS 1: find max1 & its index ----------
        System.out.println(CYAN + "Pass 1: trouver la plus grande valeur (max1)" + RESET);
        int idx1 = 0;
        double max1 = arr[0];
        printArrayHighlight(arr, 0, idx1, "  compare i=0 → best=0 (start)");
        sleep();

        for (int i = 1; i < arr.length; i++) {
            printArrayHighlight(arr, i, idx1, "  compare i=" + i + " → best=" + idx1 +
                    " (" + fmt(max1) + ")");
            if (arr[i] > max1) {
                System.out.println("  " + YELLOW + "update best : " + RESET +
                        "arr[" + i + "]=" + fmt(arr[i]) + " > " + fmt(max1));
                idx1 = i;
                max1 = arr[i];
            } else {
                System.out.println("  pas de changement");
            }
            sleep();
        }
        System.out.println("→ max1 = arr[" + idx1 + "] = " + YELLOW + fmt(max1) + RESET);
        System.out.println();

        // ---------- PASS 2: find max2 ignoring idx1 ----------
        System.out.println(CYAN + "Pass 2: Trouver la 2e plus grande valeur (max2), ignoring idx1=" + idx1 + RESET);
        // Initialize to first index that is not idx1
        int start = (idx1 == 0) ? 1 : 0;
        int idx2 = start;
        double max2 = arr[start];
        printArrayHighlight(arr, start, idx2, "  compare i=" + start + " → best=" + idx2 + " (start)");
        sleep();

        for (int i = start + 1; i < arr.length; i++) {
            if (i == idx1)
                continue; // skip the largest element index
            printArrayHighlight(arr, i, idx2, "  compare i=" + i + " → best=" + idx2 +
                    " (" + fmt(max2) + ")");
            if (arr[i] > max2) {
                System.out.println("  " + YELLOW + "update best: " + RESET +
                        "arr[" + i + "]=" + fmt(arr[i]) + " > " + fmt(max2));
                idx2 = i;
                max2 = arr[i];
            } else {
                System.out.println("  pas de changement");
            }
            sleep();
        }
        System.out.println("→ max2 = arr[" + idx2 + "] = " + CYAN + fmt(max2) + RESET);
        System.out.println();

        double sum = max1 + max2;
        System.out.println(YELLOW + "Result: " + RESET + "max1 + max2 = " + fmt(max1) + " + " + fmt(max2) +
                " = " + YELLOW + fmt(sum) + RESET);
        return sum;
    }

    /////////////////////// MATRIX ///////////////////////////

    public static void printMatrixWithHighlight(Matrix m, int hr, int hc, String title) {
        // matrix with [r][c] highlighted
        System.out.println(title);
        for (int r = 0; r < m.getRows(); r++) {
            StringBuilder sb = new StringBuilder("  ");
            for (int c = 0; c < m.getCols(); c++) {
                String cell = String.valueOf(m.get(r, c));
                if (r == hr && c == hc) {
                    sb.append("[").append(YELLOW).append(cell).append(RESET).append("]");
                } else {
                    sb.append("[").append(cell).append("]");
                }
                sb.append(" ");
            }
            System.out.println(sb);
        }
    }

    ////////////////// [6.4] ADD STEPS /////////////////////////

    public static Matrix addWithSteps(Matrix A, Matrix B) {
        ensureSameShape(A, B, "add");
        double[][] partial = new double[A.getRows()][A.getCols()];
        Matrix result = new Matrix(partial);

        for (int r = 0; r < A.getRows(); r++) {
            for (int c = 0; c < A.getCols(); c++) {
                printMatrixWithHighlight(A, r, c, CYAN + "A" + RESET);
                printMatrixWithHighlight(B, r, c, CYAN + "B" + RESET);

                partial[r][c] = A.get(r, c) + B.get(r, c);
                result = new Matrix(partial);

                printMatrixWithHighlight(result, r, c, YELLOW + "A+B" + RESET);
                sleep();
            }
        }
        return result;
    }

    ///////////// [6.5] SUB STEPS ////////////////////////////////////////////////

    public static Matrix subWithSteps(Matrix A, Matrix B) {
        ensureSameShape(A, B, "sub");
        double[][] partial = new double[A.getRows()][A.getCols()];
        Matrix result = new Matrix(partial);

        for (int r = 0; r < A.getRows(); r++) {
            for (int c = 0; c < A.getCols(); c++) {
                printMatrixWithHighlight(A, r, c, CYAN + "A" + RESET);
                printMatrixWithHighlight(B, r, c, CYAN + "B" + RESET);

                partial[r][c] = A.get(r, c) - B.get(r, c);
                result = new Matrix(partial);

                printMatrixWithHighlight(result, r, c, YELLOW + "A-B" + RESET);
                sleep();
            }
        }
        return result;
    }

    /////////////////// [6.6] SCALAR STEPS ///////////////////

    public static Matrix mulScalarWithSteps(Matrix A, double k) {
        double[][] partial = new double[A.getRows()][A.getCols()];
        Matrix result = new Matrix(partial);

        for (int r = 0; r < A.getRows(); r++) {
            for (int c = 0; c < A.getCols(); c++) {
                printMatrixWithHighlight(A, r, c, CYAN + "A" + RESET);

                partial[r][c] = A.get(r, c) * k;
                result = new Matrix(partial);

                printMatrixWithHighlight(result, r, c, YELLOW + k + "*A" + RESET);
                sleep();
            }
        }
        return result;
    }

    /////// SHAPE CHECK ///////////////////////////////////////////

    private static void ensureSameShape(Matrix a, Matrix b, String op) {
        if (a.getRows() != b.getRows() || a.getCols() != b.getCols()) {
            throw new IllegalArgumentException(
                    "Cannot " + op + " matrices of different shapes: (" +
                            a.getRows() + "x" + a.getCols() + ") vs (" +
                            b.getRows() + "x" + b.getCols() + ")");
        }
    }

}
