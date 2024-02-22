package runtime;

import frontend.AST.Expr;
import frontend.AST.Program;
import frontend.AST.Stmt;
import frontend.AST.VarDeclaration;
import runtime.Environment;
import runtime.Interpreter;
import runtime.Values.RuntimeVal;
import runtime.Values.NullVal;
import runtime.Values.ValMaker;

public class Statements {
    public static RuntimeVal evalProgram(Program program, Environment env) {
        RuntimeVal lastEvaluated = ValMaker.MK_NULL();

        for (Stmt statement : program.getBody()) {
            lastEvaluated = Interpreter.evaluate(statement, env);
        }

        return lastEvaluated;
    }

    public static RuntimeVal evalVarDeclaration(VarDeclaration declaration, Environment env) {
        RuntimeVal value = declaration.getValue() != null ? Interpreter.evaluate(declaration.getValue(), env) :  ValMaker.MK_NULL();

        return env.declareVar(declaration.getIdentifier(), (RuntimeVal) value, declaration.isConstant());
    }
}
