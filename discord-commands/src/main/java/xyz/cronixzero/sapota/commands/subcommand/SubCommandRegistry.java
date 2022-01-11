/* 
Coded for sapota
Made by CronixZero
Created 11.01.2022 - 02:08
 */

package xyz.cronixzero.sapota.commands.subcommand;

import com.google.common.flogger.FluentLogger;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import xyz.cronixzero.sapota.commands.SubCommand;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SubCommandRegistry {

    private final Map<String, SubCommandInfo> subCommands = new HashMap<>();

    public void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.name(), SubCommandInfo.generateInfo(subCommand));
    }

    public void unregisterSubCommand(String subCommand) {
        subCommands.remove(subCommand);
    }

    public SubCommandInfo getSubCommand(String name) {
        return subCommands.get(name);
    }

    public static class SubCommandInfo {
        private final Method commandMethod;
        private final SubCommand subCommand;

        public SubCommandInfo(Method commandMethod, SubCommand subCommand) {
            this.commandMethod = commandMethod;
            this.subCommand = subCommand;
        }

        public static SubCommandInfo generateInfo(SubCommand subCommand) {
            boolean subClass = distinguishSubCommandType(subCommand);

            try {
                if (subClass) {
                    return generateSubClassInfo(subCommand);
                } else
                    return generateMethodInfo(subCommand);
            } catch (NoSuchMethodException | ClassCastException e) {
                FluentLogger.forEnclosingClass().atSevere().withCause(e).log("Could not generate SubCommandInfo for %s", subCommand.name());
            }

            return null;
        }

        private static SubCommandInfo generateSubClassInfo(SubCommand subCommand) throws NoSuchMethodException, ClassCastException {
            Class<? extends AbstractSubCommand> subCommandClass = subCommand.getClass().asSubclass(AbstractSubCommand.class);

            return new SubCommandInfo(subCommandClass.getMethod("onCommand", User.class, SlashCommandEvent.class), subCommand);
        }

        private static SubCommandInfo generateMethodInfo(SubCommand subCommand) {
            Method method = null;

            for (Method m : subCommand.getClass().getMethods()) {
                SubCommand annotation = m.getAnnotation(SubCommand.class);

                if (annotation == null || !annotation.name().equals(subCommand.name()) || !annotation.subCommandGroup().equals(subCommand.subCommandGroup()))
                    continue;

                method = m;
            }

            if (method == null) {
                throw new NullPointerException("Could not find SubCommand Method for " + subCommand.name());
            }

            return new SubCommandInfo(method, subCommand);
        }

        private static boolean distinguishSubCommandType(SubCommand subCommand) {
            try {
                subCommand.getClass().asSubclass(AbstractSubCommand.class);
                return true;
            } catch (ClassCastException e) {
                return false;
            }
        }

        public Method getCommandMethod() {
            return commandMethod;
        }

        public SubCommand getSubCommand() {
            return subCommand;
        }
    }

}
