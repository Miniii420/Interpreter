package runtime;

import frontend.AST.*;

import runtime.Values.*;
import runtime.Interpreter;
import runtime.Environment;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

import static runtime.Interpreter.evaluate;
import static runtime.Values.ValMaker.MK_NULL;

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
        RuntimeVal lhs = evaluate(binop.getLeft(), env);
        RuntimeVal rhs = evaluate(binop.getRight(), env);

        if (lhs instanceof NumberVal && rhs instanceof NumberVal) {
            return evalNumericBinaryExpr((NumberVal) lhs, (NumberVal) rhs, binop.getOperator());
        }

        return MK_NULL();
    }

    public static RuntimeVal evalIdentifier(Identifier ident, Environment env) {
        return env.lookupVar(ident.getValue());
    }

    public static RuntimeVal evalAssignment(AssignmentExpr node, Environment env) {
        if (!(node.getAssigne() instanceof Identifier)) {
            throw new RuntimeException("Invalid LHS inside assignment Expr " + node.getAssigne());
        }
        String varname = ((Identifier) node.getAssigne()).getValue();
        RuntimeVal value = evaluate(node.getValue(), env);
        return env.assignVar(varname, value);
    }

    public static RuntimeVal evalObjectExpr(ObjectLiteral obj, Environment env) {
        ObjectVal object = new ObjectVal(new HashMap<>());

        for (Property property : obj.getProperties()) {
            String key = property.getKey();
            Expr value = property.getValue();
            RuntimeVal runtimeVal = value == null ? env.lookupVar(key) : evaluate(value, env);

            object.getProperties().put(key, runtimeVal);
        }

        return object;
    }

    public static RuntimeVal evalCallExpr(CallExpr expr, Environment env) {
        List<RuntimeVal> args = expr.getArgs().stream()
                .map(arg -> evaluate(arg, env))
                .collect(Collectors.toList());

        RuntimeVal fn = evaluate(expr.getCaller(), env);

        if (fn instanceof NativeFnVal) {
            return ((NativeFnVal) fn).getCall().apply(args.toArray(new RuntimeVal[0]), env);
        } else if (fn instanceof FunctionVal) {
            FunctionVal func = (FunctionVal) fn;
            Environment scope = new Environment(func.getDeclarationEnv());

            List<String> parameters = func.getParameters();
            for (int i = 0; i < parameters.size(); i++) {
                if (i < args.size()) {
                    String varname = parameters.get(i);
                    scope.declareVar(varname, args.get(i), false);
                } else {
                    // Handle mismatch in arity
                }
            }

            RuntimeVal result = MK_NULL();

            for (Stmt stmt : func.getBody()) {
                result = evaluate(stmt, scope);
            }

            return result;
        } else {
            throw new RuntimeException("Cannot call value that is not a function: " + fn);
        }
    }

}
