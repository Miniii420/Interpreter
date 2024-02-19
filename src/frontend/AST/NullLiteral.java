package frontend.AST;

public class NullLiteral implements Expr {
    private final NodeType kind = NodeType.NullLiteral;

    @Override
    public NodeType getKind() {
        return kind;
    }

    public String toString() {
        return "NullLiteral{}";
    }
}
