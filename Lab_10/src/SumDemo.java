import java.util.*;

public class SumDemo {
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.5, 2.25, -0.75);
        List<Long> longs = Arrays.asList(10000000000L, 5L, -10L);
        List<Float> floats = Arrays.asList(1.2f, 2.3f);

        System.out.println("integer sum: " + SumNumbers.sum(ints));
        System.out.println("double sum: " + SumNumbers.sum(doubles));
        System.out.println("long sum: " + SumNumbers.sum(longs));
        System.out.println("float sum: " + SumNumbers.sum(floats));

        // Mixed list declared as List<Number> (allowed) — still accepted by sum
        List<Number> mixed = new ArrayList<>();
        mixed.add(1);
        mixed.add(2.5);
        mixed.add(3L);
        mixed.add(null); // nulls are skipped
        System.out.println("mixed sum: " + SumNumbers.sum(mixed));

        // Demonstrate sumAsNumber()
        Number boxed = SumNumbers.sumAsNumber(doubles);
        System.out.println("sum as number (type): " + boxed.getClass().getSimpleName() + ", value: " + boxed);
    }
}
