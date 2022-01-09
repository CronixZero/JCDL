/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:38
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;

public abstract class AbstractSubCommand extends AbstractCommand {

    protected AbstractSubCommand(String name) {
        super(name);
    }

    protected AbstractSubCommand(String name, String[] aliases) {
        super(name, aliases);
    }

    protected AbstractSubCommand(String name, String description) {
        super(name, description);
    }

    protected AbstractSubCommand(String name, String[] aliases, String description) {
        super(name, aliases, description);
    }

    protected AbstractSubCommand(String name, String description, Permission permission) {
        super(name, description, permission);
    }

    protected AbstractSubCommand(String name, String[] aliases, String description, Permission permission) {
        super(name, aliases, description, permission);
    }
}
