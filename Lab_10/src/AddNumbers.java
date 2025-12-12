public class AddNumbers<T extends Number, U extends Number> {
    private final T a;
    private final U b;

    public AddNumbers(T a, U b) {
        this.a = a;
        this.b = b;
    }

    // primitive
    public double add() {
        return a.doubleValue() + b.doubleValue();
    }

    // object
    public Number addAsNumber() {
        return Double.valueOf(add());
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) + %s (%s) = %s",
                a, a.getClass().getSimpleName(),
                b, b.getClass().getSimpleName(),
                add());
    }
}
