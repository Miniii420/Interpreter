package runtime.Values;

import frontend.AST.NodeType;
import frontend.AST.Stmt;
import org.w3c.dom.Node;
import runtime.Environment;

import java.util.List;

public class FunctionVal implements RuntimeVal {
    private final ValueType type = ValueType.Function;
    private final String name;
    private final List<String> parameters;
    private final Environment declarationEnv;
    private final List<Stmt> body;

    public FunctionVal(String name, List<String> parameters, Environment declarationEnv, List<Stmt> body) {
        this.name = name;
        this.parameters = parameters;
        this.declarationEnv = declarationEnv;
        this.body = body;
    }

    public ValueType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Environment getDeclarationEnv() {
        return declarationEnv;
    }

    public List<Stmt> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "FunctionVal{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                ", declarationEnv=" + declarationEnv +
                ", body=" + body +
                '}';
    }
}