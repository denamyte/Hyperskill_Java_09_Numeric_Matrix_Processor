package processor;

import java.util.Arrays;

class MatrixProcessor {

    public static boolean checkSizesForAddition(double[][] m1, double[][] m2) {
        return m1.length == m2.length && m1[0].length == m2[0].length;
    }

    public static boolean checkSizesForMultiplication(double[][] m1, double[][] m2) {
        return m1[0].length == m2.length;
    }

    public static boolean checkMatrixIsSquare(double[][] m) {
        return m.length == m[0].length;
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

    public static double[][] mainDiagTransposition(double[][] m) {
        double temp;
        for (int i = 0; i < m.length - 1; i++) {
            for (int j = i; j < m.length; j++) {
                temp = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = temp;
            }
        }
        return m;
    }

    public static double[][] secDiagonalTransposition(double[][] m) {
        double temp;
        for (int row = 0, col = m.length - 1; row < m.length - 1; row++, col--) {
            for (int row2 = row + 1, col2 = col - 1; col2 >= 0; row2++, col2--) {
                temp = m[row][col2];
                m[row][col2] = m[row2][col];
                m[row2][col] = temp;
            }
        }
        return m;
    }

    public static double[][] vertMirrorTransposition(double[][] m) {
        double temp;
        int center = m.length / 2;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0, j2 = m.length - 1; j < center; j++, j2--) {
                temp = m[i][j];
                m[i][j] = m[i][j2];
                m[i][j2] = temp;
            }
        }
        return m;
    }

    public static double[][] horzMirrorTransposition(double[][] m) {
        double temp;
        int center = m.length / 2;
        for (int j = 0; j < m.length; j++) {
            for (int i = 0, i2 = m.length - 1; i < center; i++, i2--) {
                temp = m[i][j];
                m[i][j] = m[i2][j];
                m[i2][j] = temp;
            }
        }
        return m;
    }

    public static double matrixDeterminant(double[][] m) {
        return new MatrixView(m).calculateDeterminant();
    }

    public static double[][] inverseMatrix(double[][] m) {
        MatrixView matrixView = new MatrixView(m);
        double det = matrixView.calculateDeterminant();
        if (det == 0.0) {
            return null;
        }
        double[][] cof = matrixView.calculateCofactors();
        double[][] transCof = mainDiagTransposition(cof);
        return scaleMatrix(transCof, 1 / det);
    }

}
