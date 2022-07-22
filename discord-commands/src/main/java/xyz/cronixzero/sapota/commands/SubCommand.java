/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 17:25
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;
import xyz.cronixzero.sapota.commands.modifier.SubCommandDataModifier;

import java.lang.annotation.*;

/**
 * This Annotation defines a Method as a SubCommand.
 * There are multiple ways how a Method with this Annotation might look
 * If {@code null} or nothing is returned, it will be defaulted to SUCCESS
 * <p>
 * Examples on <a href="https://github.com/CronixZero/JCDL/wiki">GitHub</a>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {

    String name();

    Class<? extends SubCommandDataModifier> dataModifier() default SubCommandDataModifier.class;

    String subCommandGroup() default "";

    String subCommandGroupDescription() default "";

    String description();

    Permission permission() default Permission.UNKNOWN;

    String[] aliases() default {};

}
