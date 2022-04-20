package olivermakesco.de.rule.impl;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import olivermakesco.de.rule.api.Rule;
import olivermakesco.de.rule.api.Rules;

import java.util.Collection;
import java.util.Collections;

public class RuleCommand {

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String ruleName = StringArgumentType.getString(context, "Rule");
        String valueName = StringArgumentType.getString(context, "Value");
        Rule rule = getRule(ruleName);
        Rule.Value<?> value = rule.get(valueName);
        rule.setValue(value);
        context.getSource().sendFeedback(Text.of("Rule with name "+ruleName+" updated to value "+valueName+"."),true);
        return 0;
    }
    public static int runWithoutValue(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String ruleName = StringArgumentType.getString(context, "Rule");
        Rule rule = getRule(ruleName);
        context.getSource().sendFeedback(Text.of("Rule with name "+ruleName+" updated has value "+rule.getValue().getName()),false);
        return 0;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("rule").then(
                        CommandManager.argument("Rule", StringArgumentType.string())
                                .suggests((c,b) -> CommandSource.suggestMatching(getValues(),b))
                                .executes(RuleCommand::runWithoutValue)
                                .then(
                                        CommandManager.argument("Value", StringArgumentType.string())
                                                .suggests((c,b) -> CommandSource.suggestMatching(getValues(c),b))
                                                .executes(RuleCommand::run)
                                )
                )
        );
    }

    public static Collection<String> getValues() {
        Collection<String> out = Sets.newLinkedHashSet();
        for (Identifier id : Rules.getIds())
            out.add(id.toString());
        return out;
    }

    public static Rule getRule(String value) {
        Identifier id = new Identifier(value);
        return Rules.get(id);
    }

    public static Collection<String> getValues(CommandContext<ServerCommandSource> context) {
        Rule rule = getRule(StringArgumentType.getString(context, "Rule"));
        Collection<String> out = Sets.newLinkedHashSet();
        Collections.addAll(out, rule.getNames());
        return out;
    }
}
