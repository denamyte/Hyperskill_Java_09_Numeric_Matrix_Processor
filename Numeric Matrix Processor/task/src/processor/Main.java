package processor;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new UserIO().mainLoop();
    }
}

class UserIO {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String[] ordinals = {"", "first ", "second "};
    private static final DecimalFormat DEC_FORMAT = new DecimalFormat("0.#");

    private final Map<Integer, Runnable> actions = Map.of(
            1, this::matrixAddition,
            2, this::matrixScalarMultiplication,
            3, this::matrixMatrixMultiplication
    );
    String result = "";

    void mainLoop() {
        int choice = -1;
        while (choice != 0) {
            doAction(choice);
            showResult();
            showMainMenu();
            choice = readInt();
        }
    }

    private void doAction(int choice) {
        final Runnable runnable = actions.get(choice);
        if (runnable != null) {
            runnable.run();
        }
    }

    private void showResult() {
        if (!result.isEmpty()) {
            System.out.println(result);;
        }
    }

    private void showMainMenu() {
        System.out.print("1. Add matrices\n2. Multiply matrix by a constant\n" +
                                 "3. Multiply matrices\n0. Exit\nYour choice: ");
    }

    private void matrixAddition() {
        var m1 = inputMatrix(1);
        var m2 = inputMatrix(2);
        result = MatrixProcessor.checkSizesForAddition(m1, m2)
                ? matrixToString(MatrixProcessor.sumDoubleMatrices(m1, m2))
                : "The operation cannot be performed.\n";
    }

    private void matrixScalarMultiplication() {

    }

    private void matrixMatrixMultiplication() {

    }

    private double[][] inputMatrix(int number) {
        System.out.printf("Enter size of %smatrix: ", ordinals[number]);
        int rows = readIntRow()[0];
        double[][] mtx = new double[rows][];
        System.out.printf("Enter %smatrix:%n", ordinals[number]);
        for (int i = 0; i < rows; i++) {
            mtx[i] = readDoubleRow();
        }
        return mtx;
    }

    static int readInt() {
        return Integer.parseInt(SCANNER.nextLine());
    }

    static int[] readIntRow() {
        return Arrays.stream(SCANNER.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();
    }

    static double[] readDoubleRow() {
        return Arrays.stream(SCANNER.nextLine().split("\\s+"))
                .mapToDouble(Double::parseDouble).toArray();
    }

    static String matrixToString(double[][] mtx) {
        StringBuilder sb = new StringBuilder("The result is:\n");
        for (double[] row : mtx) {
            for (double value : row) {
                sb.append(DEC_FORMAT.format(value)).append(' ');
            }
            sb.setLength(sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }

}

class MatrixProcessor {

    public static boolean checkSizesForAddition(double[][] m1, double[][] m2) {
        return m1.length == m2.length && m1[0].length == m2[0].length;
    }

    public static double[][] sumDoubleMatrices(double[][] m1, double[][] m2) {
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

    public static int[][] scaleMatrix(int[][] m, int scalar) {
        return Arrays.stream(m)
                .map(row -> Arrays.stream(row)
                        .map(v -> v * scalar)
                        .toArray())
                .toArray(int[][]::new);
    }
}
