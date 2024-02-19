package runtime.Values;

public class NumberVal implements RuntimeVal {
    private final ValueType type = ValueType.NUMBER;
    private final double value;

    public NumberVal(double value) {
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NumberVal{type=" + type + ", value=" + value + "}";
    }
}
