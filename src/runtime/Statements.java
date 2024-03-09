package runtime;

import frontend.AST.*;

import runtime.Values.*;
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

    public static RuntimeVal evalFunctionDeclaration(FunctionDeclaration declaration, Environment env) {
        FunctionVal fn = new FunctionVal(
                declaration.getName(),
                declaration.getParameters(),
                env,
                declaration.getBody()
        );

        return env.declareVar(declaration.getName(), fn, true);
    }

}
