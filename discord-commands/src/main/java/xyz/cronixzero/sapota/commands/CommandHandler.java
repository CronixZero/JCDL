/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 22:33
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.commands.result.CommandResult;

public interface CommandHandler {

    Command registerCommand(Command command);

    void flushCommands(JDA bot);

    void flushCommands(Guild guild);

    CommandResult<?> dispatchCommand(String command, User user, SlashCommandEvent event);

    CommandResult<?> dispatchSubCommand(String command, String subCommand, User user, SlashCommandEvent event);

    CommandResult<?> dispatchSubCommand(String command, String subCommandGroup, String subCommand, User user, SlashCommandEvent event);

    Command getCommand(String command);

    SubCommandRegistry.SubCommandInfo getSubCommandInfo(String command, String subCommand);

    MessageContainer getMessageContainer();

}
