package runtime.Values;

import runtime.Environment;

public interface FunctionCall {
    RuntimeVal apply(RuntimeVal[] args, Environment env);
}