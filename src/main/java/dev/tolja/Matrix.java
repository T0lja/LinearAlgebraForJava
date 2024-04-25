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