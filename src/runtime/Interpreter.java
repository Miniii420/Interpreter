package runtime;

import frontend.AST.BinaryExpr;
import frontend.AST.NumericLiteral;
import frontend.AST.Program;
import frontend.AST.Stmt;
import runtime.Values.NullVal;
import runtime.Values.NumberVal;
import runtime.Values.RuntimeVal;

public class Interpreter {

    public static RuntimeVal evaluate(Stmt astNode) {
        switch (astNode.getKind()) {
            case NumericLiteral:
                return new NumberVal(((NumericLiteral) astNode).getValue());
            case NullLiteral:
                return new NullVal();
            case BinaryExpr:
                return evalBinaryExpr((BinaryExpr) astNode);
            case Program:
                return evalProgram((Program) astNode);
            default:
                System.err.println("This AST Node has not yet been setup for interpretation: " + astNode);
                System.exit(0);
                return null; // Unreachable, added to satisfy compiler
        }
    }

    private static RuntimeVal evalProgram(Program program) {
        RuntimeVal lastEvaluated = new NullVal();

        for (Stmt statement : program.getBody()) {
            lastEvaluated = evaluate(statement);
        }

        return lastEvaluated;
    }

    private static RuntimeVal evalBinaryExpr(BinaryExpr binop) {
        RuntimeVal lhs = evaluate(binop.getLeft());
        RuntimeVal rhs = evaluate(binop.getRight());

        if (lhs instanceof NumberVal && rhs instanceof NumberVal) {
            return evalNumericBinaryExpr((NumberVal) lhs, (NumberVal) rhs, binop.getOperator());
        }

        return new NullVal();
    }

    private static NumberVal evalNumericBinaryExpr(NumberVal lhs, NumberVal rhs, String operator) {
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
}
