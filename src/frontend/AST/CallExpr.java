package frontend.AST;

import java.util.List;
public class CallExpr implements Expr {
    private final NodeType kind = NodeType.CallExpr;
    private List<Expr> args;
    private Expr caller;

    public CallExpr(List<Expr> args, Expr caller) {
        this.args = args;
        this.caller = caller;
    }

    public NodeType getKind() {
        return kind;
    }


    public List<Expr> getArgs() {
        return args;
    }

    public void setArgs(List<Expr> args) {
        this.args = args;
    }

    public Expr getCaller() {
        return caller;
    }

    public void setCaller(Expr caller) {
        this.caller = caller;
    }
}