package runtime;

import frontend.AST.*;
import runtime.Values.NullVal;
import runtime.Values.NumberVal;
import runtime.Values.RuntimeVal;

public class Interpreter {

    public static RuntimeVal evaluate(Stmt astNode, Environment env) {
        switch (astNode.getKind()) {
            case NumericLiteral:
                return new NumberVal(((NumericLiteral) astNode).getValue());
            case Identifier:
                return Expressions.evalIdentifier((Identifier) astNode, env);
            case AssignmentExpr:
                return Expressions.evalAssignment((AssignmentExpr) astNode, env);
            case BinaryExpr:
                return Expressions.evalBinaryExpr((BinaryExpr) astNode, env);
            case Program:
                return Statements.evalProgram((Program) astNode, env);
            case VarDeclaration:
                return Statements.evalVarDeclaration((VarDeclaration) astNode, env);
            default:
                System.err.println("This AST Node has not yet been setup for interpretation: " + astNode);
                System.exit(0);
                return null; // unreachable, to satisfy the compiler
        }
    }

    private static RuntimeVal evalProgram(Program program, Environment env) {
        RuntimeVal lastEvaluated = new NullVal();

        for (Stmt statement : program.getBody()) {
            lastEvaluated = evaluate(statement, env);
        }

        return lastEvaluated;
    }
}
