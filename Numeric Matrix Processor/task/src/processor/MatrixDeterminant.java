package processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixDeterminant {
    double[][] mtx;
    /** Zero-based indices of the rows of the matrix included in this matrix view. */
    List<Integer> rows;
    /** Same, for columns. */
    List<Integer> cols;

    /** Create the top level matrix view. */
    public MatrixDeterminant(double[][] mtx) {
        this.mtx = mtx;
        rows = IntStream.range(0, mtx.length).boxed().collect(Collectors.toList());
        cols = new ArrayList<>(rows);
    }

    /** Create a class denoting a matrix one dimension less than the previous one. */
    private MatrixDeterminant(double[][] mtx, List<Integer> prevRows, List<Integer> prevCols,
                              Integer excludeRow, Integer excludeCol) {
        this.mtx = mtx;
        rows = new ArrayList<>(prevRows);
        cols = new ArrayList<>(prevCols);
        rows.remove(excludeRow);
        cols.remove(excludeCol);
    }

    public double calculate() {
        if (rows.size() == 1) {  // the most primitive border case
            return mtx[rows.get(0)][cols.get(0)];
        }

        double result = 0;
        int sign = 1;
        Integer excludeRow = rows.get(0);
        for (Integer excludeCol : cols) {
            double value = this.mtx[excludeRow][excludeCol];
            if (value > 0) {
                result += sign * value
                        * new MatrixDeterminant(this.mtx, rows, cols, excludeRow, excludeCol).calculate();
            }
            sign *= -1;
        }
        return result;
    }
}
