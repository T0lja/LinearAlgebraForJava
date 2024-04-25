import dev.tolja.Matrix;

public class Main {
    public static void main(String[] args) {
        double[][] a = {{1d, 2d, 3d}, {3d, 5d, 6d}};
        double[][] b = {{2d, 3d, 5d}, {6d, 7d, 8d}};

        double[][] c = {{2d, 3d}, {6d, 7d}, {8d, 9d}};
        Matrix matrix1 = Matrix.random(2,2);
        Matrix matrix2 = new Matrix(b);
        Matrix matrix3 = Matrix.identity(6);
        matrix1.printMatrix();
    }
}
