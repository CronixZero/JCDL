/* 
Coded for sapota
Made by CronixZero
Created 11.01.2022 - 02:06
 */

package xyz.cronixzero.sapota.commands;

import com.google.common.flogger.FluentLogger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.ApiStatus;
import xyz.cronixzero.sapota.commands.listener.CommandListener;
import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.commands.result.CommandResponseHandler;
import xyz.cronixzero.sapota.commands.result.CommandResult;
import xyz.cronixzero.sapota.commands.result.CommandResultType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.RejectedExecutionException;

public class DefaultCommandHandler implements CommandHandler {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final Map<String, Command> commands = new HashMap<>();
    private final MessageContainer messageContainer;

    public DefaultCommandHandler(MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
    }

    @Override
    public Command registerCommand(Command command) {
        SubCommandRegistry subCommandRegistry = new SubCommandRegistry();
        Map<CommandResultType, Method> responseHandlers = new EnumMap<>(CommandResultType.class);

        for (Method method : command.getClass().getMethods()) {
            if (method.isAnnotationPresent(CommandResponseHandler.class)) {
                CommandResponseHandler responseHandler = method.getAnnotation(CommandResponseHandler.class);

                responseHandlers.put(responseHandler.type(), method);
            }

            if (method.isAnnotationPresent(SubCommand.class)) {
                SubCommand subCommand = method.getAnnotation(SubCommand.class);

                subCommandRegistry.registerSubCommand(subCommand, command.getClass());
            }
        }

        command.setResponseHandlers(responseHandlers);

        if (subCommandRegistry.iterator().hasNext())
            command.setSubCommandRegistry(subCommandRegistry);

        commands.put(command.getName(), command);

        if (command.getAliases() != null) {
            for (String alias : command.getAliases()) {
                commands.computeIfPresent(alias, (k, v) -> {
                    logger.atWarning().log("Received alias %s which is already referenced with a command", k);
                    return v;
                });
            }
        }

        return command;
    }

    @Override
    public void flushCommands(JDA bot) {
        flushCommands(bot.updateCommands());
    }

    @Override
    public void flushCommands(Guild guild) {
        flushCommands(guild.updateCommands());
    }

    private void flushCommands(CommandListUpdateAction updateAction) {
        for (Command command : commands.values()) {
            try {
                updateAction.addCommands(command.toCommandData());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                logger.atSevere().withCause(e).log("Could not convert a Command to JDA CommandData");
            }
        }

        try {
            updateAction.queue();
        } catch (RejectedExecutionException e) {
            logger.atSevere().withCause(e).log("Could not send the CommandFlush to Discord's Api");
        }

        updateAction.getJDA().addEventListener(new CommandListener(this));
    }

    @ApiStatus.Internal
    @Override
    public CommandResult<?> dispatchCommand(String commandName, User user, SlashCommandEvent event) {
        Command command = commands.get(commandName);
        CommandResult<?> result = command.onCommand(user, event);

        Method handler = command.getResponseHandlers().get(result.getType());
        try {
            if (handler != null)
                handler.invoke(command, result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.atSevere().withCause(e).log("Could not invoke ResponseHandler");
            result = CommandResult.error(e, command, user, event);
        }

        return result;
    }

    @ApiStatus.Internal
    @Override
    public CommandResult<?> dispatchSubCommand(String commandName, String subCommandName, User user, SlashCommandEvent event) {
        Command command = commands.get(commandName);
        SubCommandRegistry subCommandRegistry = command.getSubCommandRegistry();

        if (subCommandRegistry == null)
            throw new IllegalArgumentException("There are no SubCommands associated with " + commandName);

        SubCommandRegistry.SubCommandInfo subCommandInfo = subCommandRegistry.getSubCommand(subCommandName);

        if (subCommandInfo == null)
            throw new IllegalArgumentException("There is no SubCommand " + subCommandName + " associated with " + commandName);

        Method subCommandMethod = subCommandInfo.getCommandMethod();
        try {
            CommandResult<?> result;
            if (subCommandMethod.getParameterCount() == 1)
                result = (CommandResult<?>) subCommandMethod.invoke(command, event);
            else
                result = (CommandResult<?>) subCommandMethod.invoke(command, user, event);

            if (result == null)
                return CommandResult.success(command, user, event);

            Method handler = command.getResponseHandlers().get(result.getType());

            if (handler != null)
                handler.invoke(command, result);

            return result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.atWarning().withCause(e).log("Could not invoke SubCommandMethod or Handler");
            return CommandResult.error(e, command, user, event);
        }
    }

    @ApiStatus.Internal
    @Override
    public CommandResult<?> dispatchSubCommand(String command, String subCommandGroup, String subCommand, User user, SlashCommandEvent event) {
        return dispatchSubCommand(command, subCommandGroup + "/" + subCommand, user, event);
    }

    @Override
    public Command getCommand(String command) {
        return commands.get(command);
    }

    @Override
    public SubCommandRegistry.SubCommandInfo getSubCommandInfo(String command, String subCommand) {
        return getCommand(command).getSubCommandRegistry().getSubCommand(subCommand);
    }

    @Override
    public Collection<Command> getCommands() {
        return Collections.unmodifiableCollection(commands.values());
    }

    @Override
    public MessageContainer getMessageContainer() {
        return messageContainer;
    }
}
