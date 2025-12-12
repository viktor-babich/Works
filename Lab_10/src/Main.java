public class Main {
    public static void main(String[] args) {
        AddNumbers<Integer, Double> ex1 = new AddNumbers<>(5, 3.2);
        System.out.println("Integer + Double: " + ex1.add());

        AddNumbers<Long, Integer> ex2 = new AddNumbers<>(10L, 7);
        System.out.println("Integer + Long: " + ex2.add());

        AddNumbers<Double, Long> ex3 = new AddNumbers<>(2.5, 4L);
        System.out.println("Double + Long: " + ex3.add());

        AddNumbers<Integer, Long> ex4 = new AddNumbers<>(-3, 10000000000L);
        System.out.println("Integer + Long: " + ex4.add());

        // addAsNumber() and toString()
        System.out.println(ex1.toString());
        System.out.println("addAsNumber type: " + ex1.addAsNumber().getClass().getSimpleName());
    }
}
