/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:42
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Command extends AbstractCommand {

    private SubCommandRegistry subCommandRegistry;

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
