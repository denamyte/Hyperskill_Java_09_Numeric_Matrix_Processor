package previous.stage1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main1 {

    public static void main_(String[] args) {
        MatrixProcessor.task1();
    }
}

class MatrixProcessor {

    public static void task1() {
        final var mtx1 = MatrixUtils.readIntMatrix();
        final var mtx2 = MatrixUtils.readIntMatrix();
        if (!checkSizes(mtx1.length, mtx1[0].length, mtx2.length, mtx2[0].length)) {
            System.out.println("ERROR");
            return;
        }
        MatrixUtils.printIntMatrix(sumIntMatrices(mtx1, mtx2));
    }

    public static boolean checkSizes(int h1, int w1, int h2, int w2) {
        return h1 == h2 && w1 == w2;
    }

    public static int[][] sumIntMatrices(int[][] m1, int[][] m2) {
        final int rows = m1.length;
        final int cols = m1[0].length;
        int[][] sum = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return sum;
    }
}

class MatrixUtils {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static int[][] readIntMatrix() {
        int rowCnt = SCANNER.nextInt();
        SCANNER.nextLine();
        return Stream.generate(MatrixUtils::readIntRow).limit(rowCnt)
                .toArray(int[][]::new);
    }

    private static int[] readIntRow() {
        return Arrays.stream(SCANNER.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();
    }

    public static <T> void printIntMatrix(int[][] mtx) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : mtx) {
            for (int value : row) {
                sb.append(value).append(' ');
            }
            sb.setLength(sb.length() - 1);
            sb.append('\n');
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb);
    }
}
