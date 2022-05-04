/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:26
 */

package xyz.cronixzero.sapota.commands.result;

import java.lang.annotation.*;

/**
 * Add this Annotation to a Method inside a {@link xyz.cronixzero.sapota.commands.Command}
 * and add a {@link CommandResult} Parameter to the Method to receive Updates on the desired
 * CommandResultType inside the Command
 *
 * @see xyz.cronixzero.sapota.commands.Command
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandResponseHandler {

    CommandResultType type();

}
