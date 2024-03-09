package runtime.Values;

public class NativeFnVal implements RuntimeVal {
    private final ValueType type = ValueType.NativeFn;
    private final FunctionCall call;

    public NativeFnVal(FunctionCall call) {
        this.call = call;
    }

    public ValueType getType() {
        return type;
    }

    public FunctionCall getCall() {
        return call;
    }

    @Override
    public String toString() {
        return "NativeFnVal{" +
                "type=" + type +
                ", call=" + call +
                '}';
    }
}
