/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:42
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;
import xyz.cronixzero.sapota.commands.subcommand.SubCommandRegistry;

import java.util.Collections;
import java.util.Set;

public abstract class Command extends AbstractCommand {

    private SubCommandRegistry subCommandRegistry;

    protected Command(String name) {
        super(name);
    }

    protected Command(String name, String... aliases) {
        super(name, aliases);
    }

    protected Command(String name, String description) {
        super(name, description);
    }

    protected Command(String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    protected Command(String name, String description, Permission permission) {
        super(name, description, permission);
    }

    protected Command(String name, String description, Permission permission, String... aliases) {
        super(name, description, permission, aliases);
    }

    protected Set<AbstractSubCommand> registerSubCommands() {
        return Collections.emptySet();
    }

    public void setSubCommandRegistry(SubCommandRegistry subCommandRegistry) {
        this.subCommandRegistry = subCommandRegistry;
    }

    public SubCommandRegistry getSubCommandRegistry() {
        return subCommandRegistry;
    }

}
