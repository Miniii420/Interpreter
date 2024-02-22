package runtime.Values;

public class ValMaker {

    public NumberVal MK_NUMBER(double n){
        return new NumberVal(n);
    }
    public static NullVal MK_NULL(){
        return new NullVal();
    }
    public BoolVal MK_BOOL(boolean b){
        return new BoolVal(b);
    }
}
