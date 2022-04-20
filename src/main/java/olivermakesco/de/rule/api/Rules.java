package olivermakesco.de.rule.api;

import net.minecraft.util.Identifier;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

public class Rules {
    public static final Map<Identifier, Rule<?>> rules = new HashMap<>();
    public static Rule<?> get(Identifier id) {
        return rules.get(id);
    }
    public static Identifier[] getIds() {
        return rules.keySet().toArray(Identifier[]::new);
    }
}
