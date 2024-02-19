package frontend.AST;

import java.util.List;

public class Program implements Stmt {
    private final List<Stmt> body;
    private final NodeType kind = NodeType.Program;

    public Program(List<Stmt> body) {
        this.body = body;
    }

    @Override
    public NodeType getKind() {
        return kind;
    }

    public List<Stmt> getBody() {
        return body;
    }

    public String toString() {
        return "Program{" +
                "body=" + body +
                '}';
    }
}