/* 
Coded for sapota
Made by CronixZero
Created 11.01.2022 - 02:08
 */

package xyz.cronixzero.sapota.commands;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;

public class SubCommandRegistry implements Iterable<SubCommandRegistry.SubCommandInfo> {

    private final Map<String, SubCommandInfo> subCommands = new HashMap<>();

    @ApiStatus.Internal
    public void registerSubCommand(SubCommand subCommand, Class<? extends Command> commandClass) {
        String separator = subCommand.subCommandGroup().equals("") ? "/" : "";
        String key = subCommand.subCommandGroup() + separator + subCommand.name(); // 'group/sub' or 'sub'

        subCommands.put(key, SubCommandInfo.generateInfo(subCommand, commandClass));
    }

    public void unregisterSubCommand(String subCommand) {
        subCommands.remove(subCommand);
    }

    public void unregisterSubCommand(String group, String subCommand) {
        subCommands.remove(group + "/" + subCommand);
    }

    public SubCommandInfo getSubCommand(String name) {
        return subCommands.get(name);
    }

    public SubCommandInfo getSubCommand(String group, String name) {
        return subCommands.get(group + "/" + name);
    }

    public Collection<SubCommandInfo> getSubCommands() {
        return Collections.unmodifiableCollection(subCommands.values());
    }

    @NotNull
    @Override
    public Iterator<SubCommandInfo> iterator() {
        return subCommands.values().iterator();
    }

    public static class SubCommandInfo {
        private final Method commandMethod;
        private final SubCommand subCommand;

        public SubCommandInfo(Method commandMethod, SubCommand subCommand) {
            this.commandMethod = commandMethod;
            this.subCommand = subCommand;
        }

        public static SubCommandInfo generateInfo(SubCommand subCommand, Class<? extends Command> commandClass) {
            Method method = null;

            for (Method m : commandClass.getMethods()) {
                if (!m.isAnnotationPresent(SubCommand.class))
                    continue;

                SubCommand annotation = m.getAnnotation(SubCommand.class);

                if (annotation.equals(subCommand))
                    method = m;
            }

            if (method == null) {
                throw new IllegalArgumentException("Could not find SubCommand Method for " + subCommand.name());
            }

            return new SubCommandInfo(method, subCommand);
        }

        public Method getCommandMethod() {
            return commandMethod;
        }

        public SubCommand getSubCommand() {
            return subCommand;
        }
    }

}
