package frontend.AST;

import java.util.List;

public class ObjectLiteral implements Expr {
    private NodeType kind = NodeType.ObectLiteral;
    private List<Property> properties;

    public ObjectLiteral(List<Property> properties) {
        this.properties = properties;
    }

    public NodeType getKind() {
        return kind;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
