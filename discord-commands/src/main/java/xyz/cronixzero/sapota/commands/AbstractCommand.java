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
import org.jetbrains.annotations.ApiStatus;
import xyz.cronixzero.sapota.commands.result.CommandResult;
import xyz.cronixzero.sapota.commands.result.CommandResultType;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractCommand {

    private final String name;
    private final String description;

    private Permission permission;
    private String[] aliases;
    private Map<CommandResultType, Method> responseHandlers;

    protected AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected AbstractCommand(String name, String description, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
    }

    protected AbstractCommand(String name, String description, Permission permission) {
        this.name = name;
        this.description = description;
        this.permission = permission;
    }

    protected AbstractCommand(String name, String description, Permission permission, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
    }

    protected CommandResult<?> onCommand(User user, SlashCommandEvent event) {
        return CommandResult.unknown(this, user, event);
    }

    /**
     * This is a template for an Error-CommandResponseHandler
     * You need to add {@link xyz.cronixzero.sapota.commands.result.CommandResponseHandler} above the method
     *
     * @see xyz.cronixzero.sapota.commands.result.CommandResponseHandler
     */
    public void onError(CommandResult<? extends Throwable> e) {

    }

    public Collection<OptionData> registerOptions() {
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

    @ApiStatus.Internal
    public void setResponseHandlers(Map<CommandResultType, Method> responseHandlers) {
        this.responseHandlers = responseHandlers;
    }

    @ApiStatus.Internal
    public Map<CommandResultType, Method> getResponseHandlers() {
        return responseHandlers;
    }
}
