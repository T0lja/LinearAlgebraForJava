package dev.tolja;

public interface MatrixOperations {
    void add(Matrix b);
    void subtract(Matrix b);
    void transpose();
    double getElement(int row, int col);
    void setElement(int row, int col, double value);
    void printMatrix();
}
