package processor;

import java.util.Arrays;

class MatrixProcessor {

    public static boolean checkSizesForAddition(double[][] m1, double[][] m2) {
        return m1.length == m2.length && m1[0].length == m2[0].length;
    }

    public static boolean checkSizesForMultiplication(double[][] m1, double[][] m2) {
        return m1[0].length == m2.length;
    }

    public static double[][] addMatrices(double[][] m1, double[][] m2) {
        final int rows = m1.length;
        final int cols = m1[0].length;
        double[][] sum = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return sum;
    }

    public static double[][] scaleMatrix(double[][] m, double scalar) {
        return Arrays.stream(m)
                .map(row -> Arrays.stream(row)
                        .map(v -> v * scalar)
                        .toArray())
                .toArray(double[][]::new);
    }

    public static double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        final int rows = m1.length;
        final int cols = m2[0].length;
        final int commonSize = m2.length;
        double[][] multi = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < commonSize; k++) {
                    multi[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return multi;
    }
}
