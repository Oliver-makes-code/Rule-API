package olivermakesco.de.rule.api;

public interface Rule<T> {
    T getValue();
    void setValue(Object value);
    void setValue(String value);
    T[] getPossibleValues();
}
