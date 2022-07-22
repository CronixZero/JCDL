/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 22:33
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.ApiStatus;
import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.commands.result.CommandResponseHandler;
import xyz.cronixzero.sapota.commands.result.CommandResult;
import xyz.cronixzero.sapota.commands.user.CommandUser;

import java.util.Collection;
import java.util.function.Consumer;

public interface CommandHandler {

    /**
     * Register a {@link Command} and all its Aliases
     * This autonomously registers all {@link SubCommand}s and {@link CommandResponseHandler}s in this {@link Command}
     *
     * @param command The {@link Command} to register
     * @see SubCommandRegistry
     * @see SubCommand
     * @see CommandResponseHandler
     * @see Command
     */
    Command registerCommand(Command command);

    /**
     * Send all registered {@link Command}s to every Guild, the Bot is on
     *
     * @param bot The {@link JDA} Instance to send the Commands from
     */
    void flushCommands(JDA bot);

    /**
     * Send all registered {@link Command}s to a specific {@link Guild}
     *
     * @param guild The Guild to send the {@link Command}s to
     */
    void flushCommands(Guild guild);

    /**
     * This will only work if you specify the Command in your #success {@link CommandResult}
     */
    void setDefaultCommandSuccessAction(Consumer<Command> action);

    @ApiStatus.Internal
    CommandResult<?> dispatchCommand(String command, CommandUser user, SlashCommandInteractionEvent event);

    @ApiStatus.Internal
    CommandResult<?> dispatchSubCommand(String command, String subCommand, CommandUser user, SlashCommandInteractionEvent event);

    @ApiStatus.Internal
    CommandResult<?> dispatchSubCommand(String command, String subCommandGroup, String subCommand,
                                        CommandUser user, SlashCommandInteractionEvent event);

    /**
     * @param command The Name of the Command
     * @return The {@link Command} referenced with the CommandName
     */
    Command getCommand(String command);

    /**
     * Get every Information about a SubCommand
     *
     * @return An Instance of {@link xyz.cronixzero.sapota.commands.SubCommandRegistry.SubCommandInfo} containing the desired SubCommand
     */
    SubCommandRegistry.SubCommandInfo getSubCommandInfo(String command, String subCommand);

    /**
     * @return All registered Commands
     */
    Collection<Command> getCommands();

    /**
     * @return A {@link MessageContainer} Instance
     */
    MessageContainer getMessageContainer();

    /**
     * @return The defined Command Success Action
     */
    Consumer<Command> getCommandSuccessAction();

}
