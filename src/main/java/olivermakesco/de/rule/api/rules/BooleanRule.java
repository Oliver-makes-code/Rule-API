package olivermakesco.de.rule.api.rules;

import olivermakesco.de.rule.api.Rule;

import java.util.Locale;

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
    public void setValue(String value) {
        if (value.toLowerCase(Locale.ROOT).equals("true")) this.value = true;
        if (value.toLowerCase(Locale.ROOT).equals("false")) this.value = false;
    }

    @Override
    public Boolean[] getPossibleValues() {
        return new Boolean[] {false, true};
    }
}
