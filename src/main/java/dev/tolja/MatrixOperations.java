package dev.tolja;

public interface MatrixOperations {
    Matrix add(Matrix b);
    Matrix subtract(Matrix b);
    Matrix multiply(Matrix b);
    void transpose();
    double getElement(int row, int col);
    void setElement(int row, int col, double value);
    void printMatrix();
}
