package olivermakesco.de.rule.api.rules;

import olivermakesco.de.rule.api.Rule;

public class BooleanRule implements Rule<Boolean> {
    Boolean value;

    public BooleanRule(Boolean defaultValue) {
        value = defaultValue;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof Boolean bool)) return;
        this.value = bool;
    }

    @Override
    public Boolean[] getPossibleValues() {
        return new Boolean[] {false, true};
    }
}
