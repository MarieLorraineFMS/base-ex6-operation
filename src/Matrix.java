import java.util.Arrays;

public final class Matrix {
    // Represent matrix as a rectangular double[][]
    private final int rows;
    private final int cols;
    private final double[][] data;

    public Matrix(double[][] source) {
        validateRectangular(source);
        this.rows = source.length;
        this.cols = (rows == 0) ? 0 : source[0].length;
        this.data = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(source[r], 0, this.data[r], 0, cols);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int r, int c) {
        return data[r][c];
    }

    public Matrix add(Matrix other) {
        ensureSameShape(this, other, "add");
        double[][] out = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                out[r][c] = this.data[r][c] + other.data[r][c];
            }
        }
        return new Matrix(out);
    }

    public Matrix sub(Matrix other) {
        ensureSameShape(this, other, "sub");
        double[][] out = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                out[r][c] = this.data[r][c] - other.data[r][c];
            }
        }
        return new Matrix(out);
    }

    public Matrix mulScalar(double k) {
        double[][] out = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                out[r][c] = this.data[r][c] * k;
            }
        }
        return new Matrix(out);
    }

    @Override
    public String toString() {
        // console visualization
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int r = 0; r < rows; r++) {
            sb.append("  ").append(Arrays.toString(data[r])).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    ///////////////////// HELPERS ////////////////////////////////////////////

    private static void validateRectangular(double[][] matrix) {
        if (matrix == null)
            throw new IllegalArgumentException("Matrix cannot be null.");
        if (matrix.length == 0)
            return;
        int cols = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != cols) {
                throw new IllegalArgumentException("Matrix must be rectangular.");
            }
        }
    }

    private static void ensureSameShape(Matrix a, Matrix b, String op) {
        if (a.rows != b.rows || a.cols != b.cols) {
            throw new IllegalArgumentException(
                    "Cannot " + op + " matrices of different shapes: (" +
                            a.rows + "x" + a.cols + ") vs (" +
                            b.rows + "x" + b.cols + ")");
        }
    }
}
