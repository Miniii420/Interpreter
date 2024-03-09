package frontend.AST;

public class Property implements Expr {
    private NodeType kind = NodeType.Property;
    private String key;
    private Expr value;

    public Property( String key, Expr value) {
        this.key = key; this.value = value;
    }

    public NodeType getKind() {
        return kind;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Expr getValue() {
        return value;
    }

    public void setValue(Expr value) {
        this.value = value;
    }
}
