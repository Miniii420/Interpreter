package frontend.AST;

public class BinaryExpr implements Expr {
    private final Expr left;
    private final Expr right;
    private final String operator;
    private final NodeType kind = NodeType.BinaryExpr;

    public BinaryExpr(Expr left, Expr right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public NodeType getKind() {
        return kind;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return toStringRecursive(0);
    }

    private String toStringRecursive(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = "  ".repeat(indentLevel); // Two spaces for each level of indentation

        sb.append(indent).append("BinaryExpr {\n");
        sb.append(indent).append("  left: ");
        sb.append(left instanceof BinaryExpr ? "\n" + ((BinaryExpr) left).toStringRecursive(indentLevel + 1) : left.toString()).append(",\n");
        sb.append(indent).append("  right: ");
        sb.append(right instanceof BinaryExpr ? "\n" + ((BinaryExpr) right).toStringRecursive(indentLevel + 1) : right.toString()).append(",\n");
        sb.append(indent).append("  operator: '").append(operator).append("'\n");
        sb.append(indent).append("}");

        return sb.toString();
    }

}