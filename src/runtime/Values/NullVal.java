package runtime.Values;

public class NullVal implements RuntimeVal {
    private final ValueType type = ValueType.NULL;
    private final String value = null;

    @Override
    public ValueType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NullVal{type=" + type + ", value='" + value + "'}";
    }


}
