package processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixView {
    double[][] mtx;
    /** Zero-based indices of the rows of the matrix included in this matrix view. */
    List<Integer> rows;
    /** Same, for columns. */
    List<Integer> cols;

    /** Create the top level matrix view. */
    public MatrixView(double[][] mtx) {
        this.mtx = mtx;
        rows = IntStream.range(0, mtx.length).boxed().collect(Collectors.toList());
        cols = new ArrayList<>(rows);
    }

    /** Create a class denoting a matrix one dimension less than the previous one. */
    private MatrixView(double[][] mtx, List<Integer> prevRows, List<Integer> prevCols,
                       Integer excludeRow, Integer excludeCol) {
        this.mtx = mtx;
        rows = new ArrayList<>(prevRows);
        cols = new ArrayList<>(prevCols);
        rows.remove(excludeRow);
        cols.remove(excludeCol);
    }

    private MatrixView reduced(int excludeRow, int excludeCol) {
        return new MatrixView(mtx, rows, cols, excludeRow, excludeCol);
    }

    public double[][] calculateCofactors() {
        int size = rows.size();
        double[][] cofactors = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int sign = (i + j) % 2 == 0 ? 1 : -1;
                cofactors[i][j] = sign * reduced(i, j).calculateDeterminant();
            }
        }
        return cofactors;
    }

    public double calculateDeterminant() {
        if (rows.size() == 1) {  // the most primitive base case of recursion
            return mtx[rows.get(0)][cols.get(0)];
        }

        double result = 0;
        int sign = 1;
        Integer excludeRow = rows.get(0);
        for (Integer excludeCol : cols) {
            double value = this.mtx[excludeRow][excludeCol];
            if (value != 0) {
                result += sign * value
                        * reduced(excludeRow, excludeCol).calculateDeterminant();
            }
            sign *= -1;
        }
        return result;
    }
}
