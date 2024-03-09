package runtime.Values;

public class BoolVal implements RuntimeVal{
    private final ValueType type = ValueType.Boolean;
    private final boolean value;


    public BoolVal(boolean value) {
        this.value = value;
    }

    public ValueType getType() {
        return type;
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BoolVal{type=" + type + ", value=" + value + "}";
    }


}
