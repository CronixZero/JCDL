/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 21:43
 */

package xyz.cronixzero.sapota.commands.options;

import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandOption {

    String name();

    OptionType type();

    String description() default "";

    boolean required() default false;

}
