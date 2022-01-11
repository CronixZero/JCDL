/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:38
 */

package xyz.cronixzero.sapota.commands.subcommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import xyz.cronixzero.sapota.commands.AbstractCommand;
import xyz.cronixzero.sapota.commands.SubCommand;
import xyz.cronixzero.sapota.commands.result.CommandResult;

import java.lang.annotation.Annotation;

public abstract class AbstractSubCommand extends AbstractCommand implements SubCommand {

    private String group;

    protected AbstractSubCommand(String name) {
        super(name);
    }

    protected AbstractSubCommand(String name, String... aliases) {
        super(name, aliases);
    }

    protected AbstractSubCommand(String name, String group, String... aliases) {
        super(name, aliases);
        this.group = group;
    }

    protected AbstractSubCommand(String name, String description) {
        super(name, description);
    }

    protected AbstractSubCommand(String name, String description, String group, String... aliases) {
        super(name, description, aliases);
        this.group = group;
    }

    protected AbstractSubCommand(String name, String description, Permission permission, String group, String... aliases) {
        super(name, description, permission, aliases);
        this.group = group;
    }

    @Override
    protected abstract CommandResult<?> onCommand(User user, SlashCommandEvent event);

    @Override
    public String name() {
        return getName();
    }

    @Override
    public String subCommandGroup() {
        return group;
    }

    @Override
    public String description() {
        return getDescription();
    }

    @Override
    public Permission permission() {
        return getPermission();
    }

    @Override
    public String[] aliases() {
        return getAliases();
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return SubCommand.class;
    }
}
