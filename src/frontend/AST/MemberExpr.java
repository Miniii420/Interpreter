package frontend.AST;

import org.w3c.dom.Node;

public class MemberExpr implements Expr {
    private final NodeType kind = NodeType.MemberExpr;
    private Expr object;
    private Expr property;
    private boolean computed;

    public MemberExpr(Expr object, Expr property, boolean computed) {
        this.object = object;
        this.property = property;
        this.computed = computed;
    }

    public NodeType getKind() {
        return kind;
    }

    public Expr getObject() {
        return object;
    }

    public void setObject(Expr object) {
        this.object = object;
    }

    public Expr getProperty() {
        return property;
    }

    public void setProperty(Expr property) {
        this.property = property;
    }

    public boolean isComputed() {
        return computed;
    }

    public void setComputed(boolean computed) {
        this.computed = computed;
    }
}
