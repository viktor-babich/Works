public class BubbleSort {
    /**
     * rules:
     * string: compare by length (shorter first)
     * integer/long: compare by the number of non-zero digits
     * double: compare by the significand (mantissa) of the number in scientific notation
     */
    public static <T> void sort(T[] arr) {
        if (arr == null) return;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (fancyCompare(arr[j], arr[j + 1]) > 0) {
                    T tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> int fancyCompare(T a, T b) {
        if (a == b) return 0;
        if (a == null) return -1;
        if (b == null) return 1;

        // strings
        if (a instanceof String && b instanceof String) {
            String sa = (String) a;
            String sb = (String) b;
            int diff = Integer.compare(sa.length(), sb.length());
            if (diff != 0) return diff;
            return sa.compareTo(sb);
        }

        // integers and longs
        if (a instanceof Integer && b instanceof Integer) {
            int ca = countNonZeroDigits(Math.abs((Integer) a));
            int cb = countNonZeroDigits(Math.abs((Integer) b));
            int diff = Integer.compare(ca, cb);
            if (diff != 0) return diff;
            return ((Integer) a).compareTo((Integer) b);
        }

        if (a instanceof Long && b instanceof Long) {
            long la = Math.abs((Long) a);
            long lb = Math.abs((Long) b);
            int ca = countNonZeroDigits(la);
            int cb = countNonZeroDigits(lb);
            int diff = Integer.compare(ca, cb);
            if (diff != 0) return diff;
            return ((Long) a).compareTo((Long) b);
        }

        // doubles
        if (a instanceof Double && b instanceof Double) {
            double da = Math.abs((Double) a);
            double db = Math.abs((Double) b);
            double sa = significand(da);
            double sb = significand(db);
            int cmp = Double.compare(sa, sb);
            if (cmp != 0) return cmp;
            return Double.compare((Double) a, (Double) b);
        }

        // fallback
        if (a instanceof Comparable && a.getClass().isInstance(b)) {
            return ((Comparable) a).compareTo(b);
        }

        // final fallback
        return a.toString().compareTo(b.toString());
    }

    private static int countNonZeroDigits(long v) {
        String s = Long.toString(v);
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '-') continue;
            if (c != '0') count++;
        }
        return count;
    }

    private static int countNonZeroDigits(int v) {
        return countNonZeroDigits((long) v);
    }

    private static double significand(double d) {
        if (d == 0.0) return 0.0;
        double exp = Math.floor(Math.log10(d));
        return d / Math.pow(10, exp);
    }
}
