/* 
Coded for Sapota
Made by CronixZero
Created 17.10.2021 - 01:00
 */

package xyz.cronixzero.sapota.commands.result;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.cronixzero.sapota.commands.Command;
import xyz.cronixzero.sapota.commands.user.CommandUser;

public class CommandResult<E> {

    private final CommandResultType type;
    private final E hint;

    private Command command;
    private CommandUser user;
    private SlashCommandEvent event;

    private CommandResult(CommandResultType type, E hint) {
        this.type = type;
        this.hint = hint;
    }

    public CommandResult(CommandResultType type, E hint, Command command, CommandUser user, SlashCommandEvent event) {
        this.type = type;
        this.hint = hint;
        this.command = command;
        this.user = user;
        this.event = event;
    }

    /////////////////////////////////////////////////////
    /////////////////////// ERROR ///////////////////////
    /////////////////////////////////////////////////////

    /**
     * This is the simplest form of an Error Response.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has Error Messages enabled,
     * this will result in a Message to the User.
     *
     * @param <E> Type of Exception
     * @param e   The Exception
     * @see #error(Throwable, Command, CommandUser, SlashCommandEvent)
     * @see #error(Throwable, Command, Member, SlashCommandEvent)
     * @see #error(Throwable, Command, User, SlashCommandEvent)
     */
    @Contract(value = "_ -> new", pure = true)
    public static <E extends Throwable> @NotNull CommandResult<E> error(E e) {
        return new CommandResult<>(CommandResultType.ERROR, e);
    }

    /**
     * This is the complex form of an Error Response.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has Error Messages enabled,
     * the Application will send a Message to the User
     *
     * @param <E>     Type of Exception
     * @param e       The {@link Throwable}
     * @param command The {@link Command} the Error was thrown from
     * @param event   The {@link SlashCommandEvent} associated with this Error
     * @param user    The {@link User} that issued the {@link Command}
     * @see #error(Throwable)
     * @see #error(Throwable, Command, Member, SlashCommandEvent)
     * @see #error(Throwable, Command, User, SlashCommandEvent)
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <E extends Throwable> @NotNull CommandResult<E> error(E e, Command command, CommandUser user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.ERROR, e, command, user, event);
    }

    /**
     * This is the complex form of an Error Response.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has Error Messages enabled,
     * the Application will send a Message to the User
     *
     * @param <E>     Type of Exception
     * @param e       The {@link Throwable}
     * @param command The {@link Command} the Error was thrown from
     * @param event   The {@link SlashCommandEvent} associated with this Error
     * @param user    The {@link User} that issued the {@link Command}
     * @see #error(Throwable)
     * @see #error(Throwable, Command, Member, SlashCommandEvent)
     * @see #error(Throwable, Command, CommandUser, SlashCommandEvent)
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <E extends Throwable> @NotNull CommandResult<E> error(E e, Command command, User user, SlashCommandEvent event) {
        return error(e, command, new CommandUser(user), event);
    }

    /**
     * This is the complex form of an Error Response.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has Error Messages enabled,
     * the Application will send a Message to the User
     *
     * @param <E>     Type of Exception
     * @param e       The {@link Throwable}
     * @param command The {@link Command} the Error was thrown from
     * @param event   The {@link SlashCommandEvent} associated with this Error
     * @param member  The {@link Member} that issued the {@link Command}
     * @see #error(Throwable)
     * @see #error(Throwable, Command, CommandUser, SlashCommandEvent)
     * @see #error(Throwable, Command, User, SlashCommandEvent)
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <E extends Throwable> @NotNull CommandResult<E> error(E e, Command command, Member member, SlashCommandEvent event) {
        return error(e, command, new CommandUser(member), event);
    }

    /////////////////////////////////////////////////////
    ////////////////////// SUCCESS //////////////////////
    /////////////////////////////////////////////////////

    /**
     * Use this {@link CommandResult} if the Command resulted in a success
     *
     * @see #success(Command, CommandUser, SlashCommandEvent)
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull CommandResult<Void> success() {
        return new CommandResult<>(CommandResultType.SUCCESS, null);
    }

    /**
     * Use this {@link CommandResult} if the Command resulted in a success
     *
     * @param command The {@link Command} that happened successfully
     * @param event   The {@link SlashCommandEvent} that happened successfully
     * @param user    The {@link CommandUser} that issued the {@link Command}
     * @see #success()
     * @see #success(Command, User, SlashCommandEvent)
     * @see #success(Command, Member, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> success(Command command, CommandUser user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.SUCCESS, null, command, user, event);
    }

    /**
     * Use this {@link CommandResult} if the Command resulted in a success
     *
     * @param command The {@link Command} that happened successfully
     * @param event   The {@link SlashCommandEvent} that happened successfully
     * @param user    The {@link User} that issued the {@link Command}
     * @see #success()
     * @see #success(Command, CommandUser, SlashCommandEvent)
     * @see #success(Command, Member, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> success(Command command, User user, SlashCommandEvent event) {
        return success(command, new CommandUser(user), event);
    }

    /**
     * Use this {@link CommandResult} if the Command resulted in a success
     *
     * @param command The {@link Command} that happened successfully
     * @param event   The {@link SlashCommandEvent} that happened successfully
     * @param member  The {@link Member} that issued the {@link Command}
     * @see #success()
     * @see #success(Command, CommandUser, SlashCommandEvent)
     * @see #success(Command, User, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> success(Command command, Member member, SlashCommandEvent event) {
        return success(command, new CommandUser(member), event);
    }

    /////////////////////////////////////////////////////
    /////////////////// NO PERMISSION ///////////////////
    /////////////////////////////////////////////////////

    /**
     * Use this {@link CommandResult} if the User has no Permission for the Command.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has NoPermission Messages enabled,
     * the Application will send a Message to the User
     *
     * @see #noPermissions(Command, CommandUser, SlashCommandEvent)
     * @see #noPermissions(Command, User, SlashCommandEvent)
     * @see #noPermissions(Command, Member, SlashCommandEvent)
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull CommandResult<Void> noPermissions() {
        return new CommandResult<>(CommandResultType.NO_PERMISSIONS, null);
    }

    /**
     * Use this {@link CommandResult} if the User has no Permission for the Command.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has NoPermission Messages enabled,
     * the Application will send a Message to the User
     *
     * @param command The {@link Command} the User has no Permissions for
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param user    The {@link CommandUser} that issued the {@link Command}
     * @see #noPermissions()
     * @see #noPermissions(Command, User, SlashCommandEvent)
     * @see #noPermissions(Command, Member, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> noPermissions(Command command, CommandUser user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.NO_PERMISSIONS, null, command, user, event);
    }

    /**
     * Use this {@link CommandResult} if the User has no Permission for the Command.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has NoPermission Messages enabled,
     * the Application will send a Message to the User
     *
     * @param command The {@link Command} the User has no Permissions for
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param user    The {@link User} that issued the {@link Command}
     * @see #noPermissions()
     * @see #noPermissions(Command, Member, SlashCommandEvent)
     * @see #noPermissions(Command, CommandUser, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> noPermissions(Command command, User user, SlashCommandEvent event) {
        return noPermissions(command, new CommandUser(user), event);
    }

    /**
     * Use this {@link CommandResult} if the User has no Permission for the Command.
     * If the {@link xyz.cronixzero.sapota.commands.messaging.MessageContainer} has NoPermission Messages enabled,
     * the Application will send a Message to the User
     *
     * @param command The {@link Command} the User has no Permissions for
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param member  The {@link Member} that issued the {@link Command}
     * @see #noPermissions()
     * @see #noPermissions(Command, User, SlashCommandEvent)
     * @see #noPermissions(Command, CommandUser, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> noPermissions(Command command, Member member, SlashCommandEvent event) {
        return noPermissions(command, new CommandUser(member), event);
    }

    /////////////////////////////////////////////////////
    ////////////////////// UNKNOWN //////////////////////
    /////////////////////////////////////////////////////

    /**
     * Use this {@link CommandResult} if something unknown happened that cannot be described
     *
     * @see #unknown(Command, CommandUser, SlashCommandEvent)
     * @see #unknown(Command, User, SlashCommandEvent)
     * @see #unknown(Command, Member, SlashCommandEvent)
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull CommandResult<Void> unknown() {
        return new CommandResult<>(CommandResultType.UNKNOWN, null);
    }

    /**
     * Use this {@link CommandResult} if something unknown had happened that cannot be described
     *
     * @param command The {@link Command} where the unknown had happened
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param user    The {@link CommandUser} that issued the {@link Command}
     * @see #unknown()
     * @see #unknown(Command, User, SlashCommandEvent)
     * @see #unknown(Command, Member, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> unknown(Command command, CommandUser user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.UNKNOWN, null, command, user, event);
    }

    /**
     * Use this {@link CommandResult} if something unknown had happened that cannot be described
     *
     * @param command The {@link Command} where the unknown had happened
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param user    The {@link User} that issued the {@link Command}
     * @see #unknown()
     * @see #unknown(Command, Member, SlashCommandEvent)
     * @see #unknown(Command, CommandUser, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> unknown(Command command, User user, SlashCommandEvent event) {
        return unknown(command, new CommandUser(user), event);
    }

    /**
     * Use this {@link CommandResult} if something unknown had happened that cannot be described
     *
     * @param command The {@link Command} where the unknown had happened
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param member  The {@link Member} that issued the {@link Command}
     * @see #unknown()
     * @see #unknown(Command, User, SlashCommandEvent)
     * @see #unknown(Command, CommandUser, SlashCommandEvent)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull CommandResult<Void> unknown(Command command, Member member, SlashCommandEvent event) {
        return unknown(command, new CommandUser(member), event);
    }

    /////////////////////////////////////////////////////
    ////////////////////// DYNAMIC //////////////////////
    /////////////////////////////////////////////////////

    /**
     * Send a {@link CommandResult} that fits your needs. You can customize the hint
     *
     * @param <T>  The Type of your custom Hint
     * @param hint A Hint that contains whatever you like. This will be passed to the {@link CommandResponseHandler}
     * @see #dynamic(Object, Command, CommandUser, SlashCommandEvent)
     * @see #dynamic(Object, Command, User, SlashCommandEvent)
     * @see #dynamic(Object, Command, Member, SlashCommandEvent)
     */
    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull CommandResult<T> dynamic(T hint) {
        return new CommandResult<>(CommandResultType.DYNAMIC, hint);
    }

    /**
     * Send a {@link CommandResult} that fits your needs. You can customize the hint
     *
     * @param <T>     The Type of your custom Hint
     * @param hint    A Hint that contains whatever you like. This will be passed to the {@link CommandResponseHandler}
     * @param command The {@link Command} that sends your dynamic {@link CommandResult}
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param user    The {@link CommandUser} that issued the {@link Command}
     * @see #dynamic(Object)
     * @see #dynamic(Object, Command, User, SlashCommandEvent)
     * @see #dynamic(Object, Command, Member, SlashCommandEvent)
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <T> @NotNull CommandResult<T> dynamic(T hint, Command command, CommandUser user, SlashCommandEvent event) {
        return new CommandResult<>(CommandResultType.DYNAMIC, hint, command, user, event);
    }

    /**
     * Send a {@link CommandResult} that fits your needs. You can customize the hint
     *
     * @param <T>     The Type of your custom Hint
     * @param hint    A Hint that contains whatever you like. This will be passed to the {@link CommandResponseHandler}
     * @param command The {@link Command} that sends your dynamic {@link CommandResult}
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param user    The {@link User} that issued the {@link Command}
     * @see #dynamic(Object)
     * @see #dynamic(Object, Command, CommandUser, SlashCommandEvent)
     * @see #dynamic(Object, Command, Member, SlashCommandEvent)
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <T> @NotNull CommandResult<T> dynamic(T hint, Command command, User user, SlashCommandEvent event) {
        return dynamic(hint, command, new CommandUser(user), event);
    }

    /**
     * Send a {@link CommandResult} that fits your needs. You can customize the hint
     *
     * @param <T>     The Type of your custom Hint
     * @param hint    A Hint that contains whatever you like. This will be passed to the {@link CommandResponseHandler}
     * @param command The {@link Command} that sends your dynamic {@link CommandResult}
     * @param event   The {@link SlashCommandEvent} that was fired
     * @param member  The {@link net.dv8tion.jda.api.entities.Member} that issued the {@link Command}
     * @see #dynamic(Object)
     * @see #dynamic(Object, Command, CommandUser, SlashCommandEvent)
     * @see #dynamic(Object, Command, User, SlashCommandEvent)
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static <T> @NotNull CommandResult<T> dynamic(T hint, Command command, Member member, SlashCommandEvent event) {
        return dynamic(hint, command, new CommandUser(member), event);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setEvent(SlashCommandEvent event) {
        this.event = event;
    }

    public void setUser(CommandUser user) {
        this.user = user;
    }

    public E getHint() {
        return hint;
    }

    public CommandResultType getType() {
        return type;
    }

    public CommandUser getUser() {
        return user;
    }

    public Command getCommand() {
        return command;
    }

    public SlashCommandEvent getEvent() {
        return event;
    }
}
