/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:42
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.ApiStatus;
import xyz.cronixzero.sapota.commands.result.CommandResult;
import xyz.cronixzero.sapota.commands.result.CommandResultType;
import xyz.cronixzero.sapota.commands.user.CommandUser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Command {

    private final String name;
    private final String description;

    private Permission permission;
    private String[] aliases;
    private Map<CommandResultType, Method> responseHandlers;
    private SubCommandRegistry subCommandRegistry;

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Command(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    protected Command(String name, String description, Permission permission) {
        this.name = name;
        this.description = description;
        this.permission = permission;
    }

    protected Command(String name, String description, Permission permission, String... aliases) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
    }

    /**
     * Command Method for Commands without SubCommands
     * Note that using this Method will not check for Permissions
     *
     * @param user  The executing User
     * @param event The Event that belongs to this Command Execution
     */
    protected CommandResult<?> onCommand(User user, SlashCommandEvent event) {
        return CommandResult.unknown(this, new CommandUser(user), event);
    }

    /**
     * Command Method for Commands without SubCommands that can only be executed in Guilds
     * */
    protected CommandResult<?> onGuildCommand(Member member, SlashCommandEvent event) {
        return CommandResult.dynamic("Unused", this, new CommandUser(member.getUser(), member), event);
    }

    /**
     * This is a template for an Error-CommandResponseHandler
     * You need to add {@link xyz.cronixzero.sapota.commands.result.CommandResponseHandler} above the method
     *
     * @see xyz.cronixzero.sapota.commands.result.CommandResponseHandler
     */
    public void onError(CommandResult<? extends Throwable> e) {

    }

    /**
     * Register the Options for this Command
     * FOR COMMAND WITHOUT SUBCOMMANDS ONLY
     */
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

    @ApiStatus.Internal
    public void setSubCommandRegistry(SubCommandRegistry subCommandRegistry) {
        this.subCommandRegistry = subCommandRegistry;
    }

    public SubCommandRegistry getSubCommandRegistry() {
        return subCommandRegistry;
    }

    /**
     * Transform this {@link Command} into {@link CommandData}
     *
     * @throws NoSuchMethodException     If Options Method could not be found
     * @throws InstantiationException    If the OptionsClass could not be instantiated
     * @throws IllegalAccessException    If the Option Method or the Option Class Constructor is not accessible
     * @throws InvocationTargetException If the Option Method throws an Exception
     */
    public CommandData toCommandData() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        CommandData data = new CommandData(getName(), getDescription());

        if (getSubCommandRegistry() != null) {
            Map<String, SubcommandGroupData> subcommandGroups = new HashMap<>();

            for (SubCommandRegistry.SubCommandInfo subCommandInfo : subCommandRegistry) {
                SubCommand subCommand = subCommandInfo.getSubCommand();

                if (!subCommand.subCommandGroup().equals("") && subCommand.subCommandGroupDescription().equals("")) {
                    throw new IllegalStateException("SubCommand " + subCommand.name() + " defines a SubCommandGroup without Description");
                }

                SubcommandData subData = new SubcommandData(subCommand.name(), subCommand.description());

                Class<? extends CommandOption> option = subCommand.options();

                if (option != CommandOption.class && option != null) {
                    Method optionsMethod = option.getMethod("getOptions");
                    Object optionsObject = optionsMethod.invoke(option.newInstance());

                    if (!(optionsObject instanceof Collection))
                        throw new IllegalArgumentException("The provided Class of CommandOption does not return a Collection");

                    Collection<OptionData> options = (Collection<OptionData>) optionsObject;

                    subData.addOptions(options);
                }

                if (!subCommand.subCommandGroup().equals("")) {
                    SubcommandGroupData groupData = new SubcommandGroupData(subCommand.subCommandGroup(),
                            subCommand.subCommandGroupDescription());

                    groupData.addSubcommands(subData);

                    subcommandGroups.put(subCommand.subCommandGroup(), groupData);
                } else
                    data.addSubcommands(subData);
            }

            data.addSubcommandGroups(subcommandGroups.values());

            return data;
        }

        Collection<OptionData> options = registerOptions();

        data.addOptions(options);

        return data;
    }

}
