/* 
Coded for Sapota
Made by CronixZero
Created 17.10.2021 - 01:00
 */

package xyz.cronixzero.sapota.commands.result;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import xyz.cronixzero.sapota.commands.AbstractCommand;
import xyz.cronixzero.sapota.commands.Command;

public class CommandResult<E> {

    private final CommandResultType type;
    private final E hint;

    private AbstractCommand command;
    private User user;
    private SlashCommandEvent event;

    private CommandResult(CommandResultType type, E hint) {
        this.type = type;
        this.hint = hint;
    }

    public CommandResult(CommandResultType type, E hint, AbstractCommand command, User user, SlashCommandEvent event) {
        this.type = type;
        this.hint = hint;
        this.command = command;
        this.user = user;
        this.event = event;
    }

    public static <E extends Throwable> CommandResult<E> error(E e) {
        return new CommandResult<>(CommandResultType.ERROR, e);
    }

    public static <E extends Throwable> CommandResult<E> error(E e, AbstractCommand command, User user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.ERROR, e, command, user, event);
    }

    public static CommandResult<?> success() {
        return new CommandResult<>(CommandResultType.SUCCESS, null);
    }

    public static CommandResult<?> success(AbstractCommand command, User user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.SUCCESS, null, command, user, event);
    }

    public static CommandResult<?> noPermissions() {
        return new CommandResult<>(CommandResultType.NO_PERMISSIONS, null);
    }

    public static CommandResult<?> noPermissions(AbstractCommand command, User user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.NO_PERMISSIONS, null, command, user, event);
    }

    public static CommandResult<?> unknown() {
        return new CommandResult<>(CommandResultType.UNKNOWN, null);
    }

    public static CommandResult<?> unknown(AbstractCommand command, User user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.UNKNOWN, null, command, user, event);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setEvent(SlashCommandEvent event) {
        this.event = event;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public E getHint() {
        return hint;
    }

    public CommandResultType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public SlashCommandEvent getEvent() {
        return event;
    }
}
