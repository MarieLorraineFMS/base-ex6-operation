public final class TriangleUtils {
    private TriangleUtils() {
    }

    public static void printRightTriangle(int height) {
        // Print a rectangle triangle using nested loops
        for (int i = 1; i <= height; i++) {
            for (int s = 0; s < (height - i); s++)
                System.out.print(" ");
            for (int a = 0; a < i; a++)
                System.out.print("*");
            System.out.println();
        }
    }

    public static void printIsoTriangle(int height) {
        // Print an isosceles triangle using String.repeat
        for (int i = 1; i <= height; i++) {
            String spaces = " ".repeat(height - i);
            String stars = "*".repeat(2 * i - 1);
            System.out.println(spaces + stars);
        }
    }
}
