package olivermakesco.de.rule.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import olivermakesco.de.rule.api.Rules;

public class RuleCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = LiteralArgumentBuilder.literal("rule");
        literal = literal.requires(ctx -> ctx.hasPermissionLevel(4));
        for (Identifier id : Rules.getIds()) {
            var rule = Rules.get(id);
            LiteralArgumentBuilder<ServerCommandSource> ruleLiteral = LiteralArgumentBuilder.literal(id.toString());
            var values = rule.getPossibleValues();
            for (Object value : values) {
                LiteralArgumentBuilder<ServerCommandSource> valueLiteral = LiteralArgumentBuilder.literal(value.toString());
                valueLiteral = valueLiteral.executes(ctx -> {
                    rule.setValue(value);
                    ctx.getSource().sendFeedback(Text.of("Rule " + id.toString() + " now has value " + rule.getValue()), false);
                    return 0;
                });
                ruleLiteral = ruleLiteral.then(valueLiteral);
            }
            literal = literal.then(ruleLiteral.executes(ctx -> {
                ctx.getSource().sendFeedback(Text.of("Rule " + id.toString() + " has value " + rule.getValue()), false);
                return 0;
            }));
        }
        dispatcher.register(literal);
    }
}
