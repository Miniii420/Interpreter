package frontend.AST;

public class Identifier implements Expr {
    private final String symbol;
    private final NodeType kind = NodeType.Identifier;

    public Identifier(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public NodeType getKind() {
        return kind;
    }

    public String getValue() {
        return symbol;
    }

    public String toString() {
        return "Identifier{" +
                "symbol='" + symbol + '\'' +
                '}';
    }
}