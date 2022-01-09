/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:42
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;

import java.util.Collections;
import java.util.Set;

public abstract class Command extends AbstractCommand {

    protected Command(String name) {
        super(name);
    }

    protected Command(String name, String[] aliases) {
        super(name, aliases);
    }

    protected Command(String name, String description) {
        super(name, description);
    }

    protected Command(String name, String[] aliases, String description) {
        super(name, aliases, description);
    }

    protected Command(String name, String description, Permission permission) {
        super(name, description, permission);
    }

    protected Command(String name, String[] aliases, String description, Permission permission) {
        super(name, aliases, description, permission);
    }

    protected Set<AbstractSubCommand> registerSubCommands() {
        return Collections.emptySet();
    }
}
