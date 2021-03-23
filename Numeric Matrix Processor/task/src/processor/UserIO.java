package processor;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

class UserIO {

    public static final String RESULT_IS = "The result is:\n";
    public static final String FIRST = "first";
    public static final String SECOND = "second";
    public static final String CANNOT_BE_PERFORMED = "The operation cannot be performed.\n";
    public static final String NO_INVERSE = "This matrix doesn't have an inverse.\n";

    private static final Scanner SCANNER = new Scanner(System.in);
    private final static Runnable noAction = () -> {};
    private final static Function<double[][], double[][]> noTransposeAction = m -> m;

    private final Map<Integer, Runnable> actions = Map.of(
            1, this::matrixAddition,
            2, this::matrixScalarMultiplication,
            3, this::matrixMatrixMultiplication,
            4, this::transpositionMenu,
            5, this::matrixDeterminant,
            6, this::inverseMatrix
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
                                 "3. Multiply matrices\n4. Transpose matrix\n" +
                                 "5. Calculate a determinant\n6. Inverse matrix\n" +
                                 "0. Exit\nYour choice: ");
    }

    private void matrixAddition() {
        var m1 = inputMatrix(FIRST);
        var m2 = inputMatrix(SECOND);
        result = MatrixProcessor.checkSizesForAddition(m1, m2)
                ? matrixToString(MatrixProcessor.addMatrices(m1, m2))
                : CANNOT_BE_PERFORMED;
    }

    private void matrixScalarMultiplication() {
        var m = inputMatrix("");
        System.out.print("Enter constant: ");
        double scalar = readDouble();
        result = matrixToString(MatrixProcessor.scaleMatrix(m, scalar));
    }

    private void matrixMatrixMultiplication() {
        var m1 = inputMatrix(FIRST);
        var m2 = inputMatrix(SECOND);
        result = MatrixProcessor.checkSizesForMultiplication(m1, m2)
                ? matrixToString(MatrixProcessor.multiplyMatrices(m1, m2))
                : CANNOT_BE_PERFORMED;
    }

    private void transpositionMenu() {
        showTranspositionMenu();
        int choice = readInt();
        double[][] m = inputMatrix("");
        result = MatrixProcessor.checkMatrixIsSquare(m)
                ? matrixToString(transposeActions.getOrDefault(choice, noTransposeAction).apply(m))
                : CANNOT_BE_PERFORMED;
    }

    private void matrixDeterminant() {
        double[][] m = inputMatrix("");
        result = m.length > 0 && MatrixProcessor.checkMatrixIsSquare(m)
                ? String.format("%s%s%n", RESULT_IS, "" + MatrixProcessor.matrixDeterminant(m))
                : CANNOT_BE_PERFORMED;
    }

    private void inverseMatrix() {
        double[][] m = inputMatrix("");
        double[][] inverse = MatrixProcessor.inverseMatrix(m);
        result = inverse == null ? NO_INVERSE : matrixToString(inverse);
    }

    private void showTranspositionMenu() {
        System.out.print("\n1. Main diagonal\n2. Side diagonal\n3. Vertical line\n" +
                                   "4. Horizontal line\nYour choice: ");
    }

    private double[][] inputMatrix(String ordinal) {
        System.out.printf("Enter size of %smatrix: ", ordinal);
        int rows = readIntRow()[0];
        double[][] mtx = new double[rows][];
        System.out.printf("Enter %smatrix:%n", ordinal);
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
        StringBuilder sb = new StringBuilder(RESULT_IS);
        for (double[] row : mtx) {
            for (double value : row) {
                String s = String.format("%8.3f", value)
                        .replace(".000", "    ")
                        .replace("-0 ", " 0 ");
                sb.append(s).append("  ");
            }
            sb.setLength(sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }
}
