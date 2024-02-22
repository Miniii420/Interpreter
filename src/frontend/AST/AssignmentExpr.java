package frontend.AST;

public class AssignmentExpr implements Expr {
    private final NodeType kind = NodeType.AssignmentExpr;
    private final Expr assigne;
    private final Expr value;

    public AssignmentExpr(Expr assigne, Expr value) {
        this.assigne = assigne;
        this.value = value;
    }

    public Expr getValue() {
        return value;
    }

    public Expr getAssigne() {
        return assigne;
    }

    @Override
    public NodeType getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "(" + assigne.toString() + " = " + value.toString() + ")";
    }
}
