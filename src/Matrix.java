import java.util.Arrays;

public final class Matrix {
    // Number of rows in the matrix (height)
    private final int rows;
    // Number of columns in the matrix (width)
    private final int cols;
    // Data[r][c]
    private final double[][] data;

    /**
     * Construct a Matrix from a 2D array of doubles.
     * Ensures all rows have the same length.
     *
     */
    public Matrix(double[][] source) {
        validateRectangular(source); // ensure non-null & rectangular
        this.rows = source.length; // number of row
        this.cols = (rows == 0) ? 0 : source[0].length; // number of columns
        this.data = new double[rows][cols];
        // Copy each row
        for (int r = 0; r < rows; r++) {
            System.arraycopy(source[r], 0, this.data[r], 0, cols);
        }
    }

    /** @return number of rows */
    public int getRows() {
        return rows;
    }

    /** @return number of columns */
    public int getCols() {
        return cols;
    }

    /**
     * Get the value at row r & column c.
     *
     * @param r row index
     * @param c column index
     * @return value stored at (r, c)
     */
    public double get(int r, int c) {
        return data[r][c];
    }

    /**
     * Addition
     *
     * @return new Matrix with addition result
     */
    public Matrix add(Matrix other) {
        ensureSameShape(this, other, "add"); // shape check
        double[][] out = new double[rows][cols];
        for (int r = 0; r < rows; r++) { // iterate each cell
            for (int c = 0; c < cols; c++) {
                out[r][c] = this.data[r][c] + other.data[r][c];
            }
        }
        return new Matrix(out);
    }

    /**
     * Substraction
     *
     * @return new Matrix with sub result
     */
    public Matrix sub(Matrix other) {
        ensureSameShape(this, other, "sub"); // shape check
        double[][] out = new double[rows][cols];
        for (int r = 0; r < rows; r++) { // iterate each cell
            for (int c = 0; c < cols; c++) {
                out[r][c] = this.data[r][c] - other.data[r][c];
            }
        }
        return new Matrix(out);
    }

    /**
     * Scalar multiplication
     * - Multiplies every element by given scalar.
     *
     * @return new Matrix with multiplication result
     */
    public Matrix mulScalar(double k) {
        double[][] out = new double[rows][cols];
        for (int r = 0; r < rows; r++) { // iterate each cell
            for (int c = 0; c < cols; c++) {
                out[r][c] = this.data[r][c] * k; // scale each element
            }
        }
        return new Matrix(out);
    }

    /**
     * Console visualization.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int r = 0; r < rows; r++) {
            sb.append("  ").append(Arrays.toString(data[r])).append("\n"); // print each row
        }
        sb.append("]");
        return sb.toString();
    }

    ///////////////////// HELPERS ////////////////////////////////////////////

    /**
     * Ensure array is non-null & rectangular : same number of columns.
     *
     * @throws IllegalArgumentException if null or non-rectangular
     */
    private static void validateRectangular(double[][] matrix) {
        if (matrix == null)
            throw new IllegalArgumentException("Matrix cannot be null.");
        if (matrix.length == 0)
            return; // accept empty matrix
        int cols = matrix[0].length; // expected number of columns
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != cols) { // not rectangular
                throw new IllegalArgumentException("Matrix must be rectangular.");
            }
        }
    }

    /**
     * Ensure two matrices have the same shape : same rows and columns.
     * 
     * @param a  first matrix
     * @param b  second matrix
     * @param op operation name for error message
     * @throws IllegalArgumentException if shapes differ
     */
    private static void ensureSameShape(Matrix a, Matrix b, String op) {
        if (a.rows != b.rows || a.cols != b.cols) {
            throw new IllegalArgumentException(
                    "Cannot " + op + " matrices of different shapes : (" +
                            a.rows + "x" + a.cols + ") vs (" +
                            b.rows + "x" + b.cols + ")");
        }
    }
}
