package frontend.AST;

public class VarDeclaration implements Stmt {
    private final NodeType kind = NodeType.VarDeclaration;
    private final boolean constant;
    private final String identifier;
    private final Expr value; // Note: In Java, interfaces cannot have optional fields, so you may need to handle this differently in your implementation.

    public VarDeclaration(boolean constant, String identifier, Expr value) {
        this.constant = constant;
        this.identifier = identifier;
        this.value = value;
    }

    public NodeType getKind() {
        return kind;
    }

    public boolean isConstant() {
        return constant;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expr getValue() {
        return value;
    }
}
