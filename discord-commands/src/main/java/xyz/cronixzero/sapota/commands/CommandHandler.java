/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 22:33
 */

package xyz.cronixzero.sapota.commands;

import xyz.cronixzero.sapota.commands.result.CommandResult;

public interface CommandHandler {

    void registerCommand(Command command);

    CommandResult<?> dispatchCommand(String command);

    CommandResult<?> dispatchSubCommand(String command, String subCommand);

    // SubCommands + Commands
    CommandResult<?> dispatchCommand(AbstractCommand command);

    Command getCommand(String command);

    SubCommand getSubCommand(String command, String subCommand);

}
