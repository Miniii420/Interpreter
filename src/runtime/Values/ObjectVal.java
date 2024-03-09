package runtime.Values;

import java.util.Map;

public class ObjectVal implements RuntimeVal {
    private final ValueType type = ValueType.Object;
    private Map<String, RuntimeVal> properties;

    public ObjectVal(Map<String, RuntimeVal> properties) {
        this.properties = properties;
    }

    public ValueType getType() {
        return type;
    }

    public Map<String, RuntimeVal> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, RuntimeVal> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "ObjectVal{" +
                "type=" + type +
                ", properties=" + properties +
                '}';
    }
}