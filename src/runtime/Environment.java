package runtime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import runtime.Values.RuntimeVal;

public class Environment {
    private Environment parent;
    private Map<String, RuntimeVal> variables;
    private Set<String> constants;

    public Environment() {
        this.parent = null;
        this.variables = new HashMap<>();
        this.constants = new HashSet<>();
    }
    public Environment(Environment parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
        this.constants = new HashSet<>();
    }

    public RuntimeVal declareVar(String varname, RuntimeVal value, boolean constant) {
        if (this.variables.containsKey(varname)) {
            throw new RuntimeException("Cannot declare variable " + varname + ". As it is already defined.");
        }

        this.variables.put(varname, value);
        System.out.println(this.variables.toString());
        if (constant) {
            this.constants.add(varname);
        }
        return value;
    }

    public RuntimeVal assignVar(String varname, RuntimeVal value) {
        Environment env = this.resolve(varname);
        if (env.constants.contains(varname)) {
            throw new RuntimeException("Cannot reassign to variable " + varname + " as it was declared constant.");
        }
        if (env.variables.containsKey(varname)) {
            env.variables.put(varname, value); // Update the value in the current environment
        } else {
            throw new RuntimeException("Variable " + varname + " not found in current environment."); // Handle case where variable is not found
        }
        return value;
    }


    public RuntimeVal lookupVar(String varname) {
        Environment env = this.resolve(varname);
        return env.variables.get(varname);
    }

    public Environment resolve(String varname) {
        if (this.variables.containsKey(varname)) {
            return this;
        }

        if (this.parent == null) {
            throw new RuntimeException("Cannot resolve '" + varname + "' as it does not exist.");
        }

        return this.parent.resolve(varname);
    }

    @Override
    public String toString() {
        return "Environment{" +
                "parent=" + parent +
                ", variables=" + variables +
                ", constants=" + constants +
                '}';
    }
}
