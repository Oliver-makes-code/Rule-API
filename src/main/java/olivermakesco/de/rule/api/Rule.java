package olivermakesco.de.rule.api;

import java.util.ArrayList;

public abstract class Rule {
    public abstract Value<?>[] getValues();
    public abstract Value<?> get(String name);
    public abstract Value<?> getValue();
    public abstract void setValue(Value<?> value);

    public String[] getNames() {
        var out = new ArrayList<String>();
        for (Value<?> value : getValues())
            out.add(value.getName());
        return out.toArray(String[]::new);
    }

    public abstract static class Value<V> {
        public abstract String getName();
        public abstract V getValue();
        public abstract void setValue(V value);
        public abstract void setValue(String value);
    }
}
