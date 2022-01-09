/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 05:34
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import xyz.cronixzero.sapota.commands.result.CommandResult;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractCommand {

    private final String name;

    private String description;
    private Permission permission;
    private String[] aliases;

    protected AbstractCommand(String name) {
        this.name = name;
    }

    protected AbstractCommand(String name, String[] aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    protected AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected AbstractCommand(String name, String[] aliases, String description) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
    }

    protected AbstractCommand(String name, String description, Permission permission) {
        this.name = name;
        this.description = description;
        this.permission = permission;
    }

    protected AbstractCommand(String name, String[] aliases, String description, Permission permission) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
    }

    protected CommandResult onCommand(User user, SlashCommandEvent event) {
        return CommandResult.unknown(this, user, event);
    }

    protected Set<OptionData> registerOptions() {
        return Collections.emptySet();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Permission getPermission() {
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }
}
