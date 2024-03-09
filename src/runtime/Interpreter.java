package runtime;

import frontend.AST.*;
import runtime.Values.*;

import static runtime.Expressions.*;
import static runtime.Statements.*;

public class Interpreter {

    public static RuntimeVal evaluate(Stmt astNode, Environment env) {
        switch (astNode.getKind()) {
            case NumericLiteral:
                return new NumberVal(((NumericLiteral) astNode).getValue());
            case Identifier:
                return evalIdentifier((Identifier) astNode, env);
            case ObectLiteral:
                return evalObjectExpr((ObjectLiteral) astNode,env);
            case CallExpr:
                return evalCallExpr((CallExpr) astNode,env);
            case AssignmentExpr:
                return evalAssignment((AssignmentExpr) astNode, env);
            case BinaryExpr:
                return evalBinaryExpr((BinaryExpr) astNode, env);
            case Program:
                return evalProgram((Program) astNode, env);
            case VarDeclaration:
                return evalVarDeclaration((VarDeclaration) astNode, env);
            case FunctionDeclaration:
                return evalFunctionDeclaration((FunctionDeclaration) astNode,env);
            default:
                System.err.println("This AST Node has not yet been setup for interpretation: " + astNode);
                System.exit(0);
                return null; // unreachable, to satisfy the compiler
        }
    }
}
