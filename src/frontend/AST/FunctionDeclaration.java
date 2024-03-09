package frontend.AST;

import java.util.List;

public class FunctionDeclaration implements Stmt {
    private NodeType kind = NodeType.FunctionDeclaration;
    private List<String> parameters;
    private String name;
    private List<Stmt> body;

    public FunctionDeclaration(List<String> parameters, String name, List<Stmt> body) {
        this.parameters = parameters;
        this.name = name;
        this.body = body;
    }

    public NodeType getKind() {
        return kind;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stmt> getBody() {
        return body;
    }

    public void setBody(List<Stmt> body) {
        this.body = body;
    }
}
