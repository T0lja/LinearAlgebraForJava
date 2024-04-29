package dev.tolja;

public class Matrix implements MatrixOperations {

    private final double[][] matrix;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];
    }

    public Matrix() {
        this(0, 0);
    }

    public Matrix(double[][] matrix) {
        validateMatrixDimensions(matrix);
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, cols);
        }
    }

    public static double[] solveLinearEquation(Matrix A, double[] b) {
        if (A.rows != A.cols || A.rows != b.length) {
            throw new IllegalArgumentException("Matrix A must be square and compatible with vector b.");
        }

        int n = A.rows;
        Matrix augmentedMatrix = new Matrix(n, n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix.setElement(i, j, A.getElement(i, j));
            }
            augmentedMatrix.setElement(i, n, b[i]);
        }

        for (int i = 0; i < n; i++) {
            int maxRowIndex = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmentedMatrix.getElement(k, i)) > Math.abs(augmentedMatrix.getElement(maxRowIndex, i))) {
                    maxRowIndex = k;
                }
            }
            augmentedMatrix.swapRows(i, maxRowIndex);

            for (int k = i + 1; k < n; k++) {
                double factor = augmentedMatrix.getElement(k, i) / augmentedMatrix.getElement(i, i);
                for (int j = i; j < n + 1; j++) {
                    augmentedMatrix.setElement(k, j, augmentedMatrix.getElement(k, j) - factor * augmentedMatrix.getElement(i, j));
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += augmentedMatrix.getElement(i, j) * x[j];
            }
            x[i] = (augmentedMatrix.getElement(i, n) - sum) / augmentedMatrix.getElement(i, i);
        }

        return x;
    }

    private void swapRows(int i, int j) {
        double[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
    }

    public static Matrix add(Matrix a, Matrix b) {
        if (!areMatrixDimensionsMatched(a, b)) {
            throw new IllegalArgumentException("The dimensions of the two matrices do not match!");
        }

        Matrix result = new Matrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                result.setElement(i, j, a.getElement(i, j) + b.getElement(i, j));
            }
        }
        return result;
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        if (!areMatrixDimensionsMatched(a, b)) {
            throw new IllegalArgumentException("The dimensions of the two matrices do not match!");
        }

        Matrix result = new Matrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                result.setElement(i, j, a.getElement(i, j) - b.getElement(i, j));
            }
        }
        return result;
    }

    public static Matrix zeros(int rows, int cols) {
        return new Matrix(rows, cols);
    }

    public static Matrix random(int rows, int cols) {
        Matrix result = new Matrix(rows, cols);
        result.FillWithRandomNumbers();
        return result;
    }

    public static Matrix identity(int size) {
        Matrix result = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            result.setElement(i, i, 1);
        }
        return result;
    }

    @Override
    public void transpose() {
        Matrix tempMatrix = new Matrix(this.matrix);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.setElement(i,j,tempMatrix.matrix[j][i]);
            }
        }
    }

    @Override
    public void FillWithRandomNumbers() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.setElement(i,j,Math.random());
            }
        }
    }

    @Override
    public double getElement(int row, int col) {
        return matrix[row][col];
    }

    @Override
    public void setElement(int row, int col, double value) {
        this.matrix[row][col] = value;
    }

    @Override
    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols != b.rows) {
            throw new IllegalArgumentException("Cannot multiply matrices with incompatible dimensions!");
        }

        Matrix result = new Matrix(a.rows, b.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                for (int k = 0; k < a.cols; k++) {
                    result.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }
        return result;
    }

    @Override
    public void add(Matrix b) {
        if (!areMatrixDimensionsMatched(this, b)) {
            throw new IllegalArgumentException("The dimensions of the two matrices do not match!");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.setElement(i,j,this.matrix[i][j] + b.matrix[i][j]);
            }
        }
    }

    @Override
    public void subtract(Matrix b) {
        if (!areMatrixDimensionsMatched(this, b)) {
            throw new IllegalArgumentException("The dimensions of the two matrices do not match!");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.setElement(i,j,this.matrix[i][j] - b.matrix[i][j]);
            }
        }
    }

    private void validateMatrixDimensions(double[][] matrix) {
        int cols = matrix[0].length;
        for (double[] row : matrix) {
            if (row.length != cols) {
                throw new IllegalArgumentException("Invalid matrix dimensions!");
            }
        }
    }

    private static boolean areMatrixDimensionsMatched(Matrix a, Matrix b) {
        return a.rows == b.rows && a.cols == b.cols;
    }
}