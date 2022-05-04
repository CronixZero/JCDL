/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 17:25
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.*;

/**
 * This Annotation defines a Method as a SubCommand.
 * There are multiple ways how a Method with this Annotation might look
 * If {@code null} or nothing is returned, it will be defaulted to SUCCESS
 * <p>
 * The 4 possible ways are covered below:
 * <p>
 * Without Result; Without User Argument
 * <pre>{@code
 *   @SubCommand(name = "hello", description = "Let me greet the World!")
 *   public void onSubCommand(SlashCommandEvent event) {
 *      event.reply("Hello World!").queue();
 *   }
 * }</pre>
 * <p>
 * Without Result; With User Argument
 * <pre>{@code
 *   @SubCommand(name = "hello", description = "Let me greet the World!")
 *   public void onSubCommand(User user, SlashCommandEvent event) {
 *      event.reply("Hello World!").queue();
 *   }
 * }</pre>
 * <p>
 * With Result; Without User Argument
 * <pre>{@code
 *   @SubCommand(name = "hello", description = "Let me greet the World!")
 *   public CommandResult<?> onSubCommand(SlashCommandEvent event) {
 *      event.reply("Hello World!").queue();
 *      return CommandResult.success(this, user, event);
 *   }
 * }</pre>
 * <p>
 * With Result; With User Argument
 * <pre>{@code
 *   @SubCommand(name = "hello", description = "Let me greet the World!")
 *   public CommandResult<?> onSubCommand(User user, SlashCommandEvent event) {
 *      event.reply("Hello World!").queue();
 *      return CommandResult.success(this, user, event);
 *   }
 * }</pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {

    String name();

    Class<? extends CommandOption> options() default CommandOption.class;

    String subCommandGroup() default "";

    String subCommandGroupDescription() default "";

    String description();

    Permission permission() default Permission.UNKNOWN;

    String[] aliases() default {};

}
