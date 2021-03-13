package previous.stage4;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

class UserIO {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String[] ordinals = {"", "first ", "second "};
    private final static Runnable noAction = () -> {};
    private final static Function<double[][], double[][]> noTransposeAction = m -> m;

    private final Map<Integer, Runnable> actions = Map.of(
            1, this::matrixAddition,
            2, this::matrixScalarMultiplication,
            3, this::matrixMatrixMultiplication,
            4, this::transpositionMenu
    );
    private static final Map<Integer, Function<double[][], double[][]>> transposeActions = Map.of(
            1, MatrixProcessor::mainDiagTransposition,
            2, MatrixProcessor::secDiagonalTransposition,
            3, MatrixProcessor::vertMirrorTransposition,
            4, MatrixProcessor::horzMirrorTransposition
    );
    String result = "";

    void mainLoop() {
        int choice = -1;
        while (choice != 0) {
            runAction(choice);
            showResult();
            showMainMenu();
            choice = readInt();
        }
    }

    private void runAction(int choice) {
        actions.getOrDefault(choice, noAction).run();
    }

    private void showResult() {
        if (!result.isEmpty()) {
            System.out.println(result);
            ;
        }
    }

    private void showMainMenu() {
        System.out.print("1. Add matrices\n2. Multiply matrix by a constant\n" +
                                 "3. Multiply matrices\n4. Transpose matrix\n0. Exit\nYour choice: ");
    }

    private void matrixAddition() {
        var m1 = inputMatrix(1);
        var m2 = inputMatrix(2);
        result = MatrixProcessor.checkSizesForAddition(m1, m2)
                ? matrixToString(MatrixProcessor.addMatrices(m1, m2))
                : "The operation cannot be performed.\n";
    }

    private void matrixScalarMultiplication() {
        var m = inputMatrix(0);
        System.out.print("Enter constant: ");
        double scalar = readDouble();
        result = matrixToString(MatrixProcessor.scaleMatrix(m, scalar));
    }

    private void matrixMatrixMultiplication() {
        var m1 = inputMatrix(1);
        var m2 = inputMatrix(2);
        result = MatrixProcessor.checkSizesForMultiplication(m1, m2)
                ? matrixToString(MatrixProcessor.multiplyMatrices(m1, m2))
                : "The operation cannot be performed.\n";
    }

    private void transpositionMenu() {
        showTranspositionMenu();
        int choice = readInt();
        double[][] m = inputMatrix(0);
        result = MatrixProcessor.checkSizesForTransposition(m)
                ? matrixToString(transposeActions.getOrDefault(choice, noTransposeAction).apply(m))
                : "The operation cannot be performed.\n";
    }

    private void showTranspositionMenu() {
        System.out.print("\n1. Main diagonal\n2. Side diagonal\n3. Vertical line\n" +
                                   "4. Horizontal line\nYour choice: ");
    }

    private double[][] inputMatrix(int ordIndex) {
        System.out.printf("Enter size of %smatrix: ", ordinals[ordIndex]);
        int rows = readIntRow()[0];
        double[][] mtx = new double[rows][];
        System.out.printf("Enter %smatrix:%n", ordinals[ordIndex]);
        for (int i = 0; i < rows; i++) {
            mtx[i] = readDoubleRow();
        }
        return mtx;
    }

    static int readInt() {
        return Integer.parseInt(SCANNER.nextLine());
    }

    static double readDouble() {
        return Double.parseDouble(SCANNER.nextLine());
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
                sb.append(value).append(' ');
            }
            sb.setLength(sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }
}
