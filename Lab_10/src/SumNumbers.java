import java.util.List;

public class SumNumbers {
    /**
     * Sums a list of numbers, returning the result as a primitive double.
     * Null elements are skipped.
     */
    public static double sum(List<? extends Number> list) {
        if (list == null) return 0.0;
        double total = 0.0;
        for (Number n : list) {
            if (n != null) total += n.doubleValue();
        }
        return total;
    }

    /**
     * Sums a list of numbers, returning the result as a boxed Number.
     * Null elements are skipped.
     */
    public static Number sumAsNumber(List<? extends Number> list) {
        return Double.valueOf(sum(list));
    }
}
