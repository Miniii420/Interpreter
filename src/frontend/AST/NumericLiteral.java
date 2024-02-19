package frontend.AST;

public class NumericLiteral implements Expr {
    private final double value;
    private final NodeType kind = NodeType.NumericLiteral;

    public NumericLiteral(double value) {
        this.value = value;
    }

    @Override
    public NodeType getKind() {
        return kind;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return "NumericLiteral{" +
                "value=" + value +
                '}';
    }
}