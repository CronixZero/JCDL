/* 
Coded for sapota
Made by CronixZero
Created 11.01.2022 - 02:06
 */

package xyz.cronixzero.sapota.commands;

import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.commands.result.CommandResult;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DefaultCommandHandler implements CommandHandler {

    private final Map<String, Command> commands = new HashMap<>();
    private final MessageContainer messageContainer;

    public DefaultCommandHandler(MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
    }

    @Override
    public void registerCommand(Command command) {

    }

    @Override
    public CommandResult<?> dispatchCommand(String command) {
        return null;
    }

    @Override
    public CommandResult<?> dispatchSubCommand(String command, String subCommand) {
        return null;
    }

    @Override
    public CommandResult<?> dispatchCommand(AbstractCommand command) {
        return null;
    }

    @Override
    public Command getCommand(String command) {
        return commands.get(command);
    }

    @Override
    public SubCommand getSubCommand(String command, String subCommand) {
        return getCommand(command).getSubCommandRegistry().getSubCommand(subCommand).getSubCommand();
    }

    private Method getSubCommandMethod(String command, String subCommand) {
        return getCommand(command).getSubCommandRegistry().getSubCommand(subCommand).getCommandMethod();
    }
}
