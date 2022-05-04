/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 17:25
 */

package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.*;

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
