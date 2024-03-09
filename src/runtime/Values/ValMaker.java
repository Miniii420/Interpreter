package runtime.Values;

public class ValMaker {

    public static RuntimeVal MK_NUMBER(double n){
        return new NumberVal(n);
    }
    public static RuntimeVal MK_NULL(){
        return new NullVal();
    }
    public static RuntimeVal MK_BOOL(boolean b){
        return new BoolVal(b);
    }

    public static NativeFnVal MK_NATIVE_FN(FunctionCall call) {
        return new NativeFnVal(call);
    }
}
