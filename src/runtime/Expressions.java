package runtime;

import frontend.AST.BinaryExpr;
import frontend.AST.Identifier;
import frontend.AST.AssignmentExpr;
import runtime.Environment;
import runtime.Interpreter;
import runtime.Values.NumberVal;
import runtime.Values.RuntimeVal;
import runtime.Values.ValMaker;

public class Expressions {
    public static NumberVal evalNumericBinaryExpr(NumberVal lhs, NumberVal rhs, String operator) {
        double result = 0;

        switch (operator) {
            case "+":
                result = lhs.getValue() + rhs.getValue();
                break;
            case "-":
                result = lhs.getValue() - rhs.getValue();
                break;
            case "*":
                result = lhs.getValue() * rhs.getValue();
                break;
            case "/":
                result = lhs.getValue() / rhs.getValue();
                break;
            case "%":
                result = lhs.getValue() % rhs.getValue();
                break;
        }
        return new NumberVal(result);
    }

    public static RuntimeVal evalBinaryExpr(BinaryExpr binop, Environment env) {
        RuntimeVal lhs = Interpreter.evaluate(binop.getLeft(), env);
        RuntimeVal rhs = Interpreter.evaluate(binop.getRight(), env);

        if (lhs instanceof NumberVal && rhs instanceof NumberVal) {
            return evalNumericBinaryExpr((NumberVal) lhs, (NumberVal) rhs, binop.getOperator());
        }

        return ValMaker.MK_NULL();
    }

    public static RuntimeVal evalIdentifier(Identifier ident, Environment env) {
        return env.lookupVar(ident.getSymbol());
    }

    public static RuntimeVal evalAssignment(AssignmentExpr node, Environment env) {
        if (!(node.getAssigne() instanceof Identifier)) {
            throw new RuntimeException("Invalid LHS inside assignment Expr " + node.getAssigne());
        }
        String varname = ((Identifier) node.getAssigne()).getSymbol();
        RuntimeVal value = Interpreter.evaluate(node.getValue(), env);
        return env.assignVar(varname, value);
    }
}
