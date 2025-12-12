public class BubbleSortDemo {
    private static <T> void printArray(T[] arr) {
        if (arr == null) {
            System.out.println("null");
            return;
        }
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        Integer[] ints = {100, 20, 303, 4, 10, 7};
        System.out.println("before integer: ");
        printArray(ints);
        BubbleSort.sort(ints);
        System.out.println("after integer (by non-zero digits): ");
        printArray(ints);

        Double[] doubles = {3.5, 1.2, -0.7, 4.4, 0.05};
        System.out.println("\nbefore double: ");
        printArray(doubles);
        BubbleSort.sort(doubles);
        System.out.println("after double (by significand): ");
        printArray(doubles);

        String[] strings = {"banana", "apple", "pear", "Apricot", "xy", "longstring"};
        System.out.println("\nafter string: ");
        printArray(strings);
        BubbleSort.sort(strings);
        System.out.println("after string (by length): ");
        printArray(strings);
    }
}
