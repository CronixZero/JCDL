/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:26
 */

package xyz.cronixzero.sapota.commands.result;

import java.lang.annotation.*;

/**
 * This Annotation defines a Method as a ResponseHandler.
 * <p>
 * <pre>{@code
 *      @CommandResponseHandler(type = CommandResultType.SUCCESS) // or any other Type
 *      public void onResponse(CommandResult<?> result) {
 *          System.out.println("Received Result of Type SUCCESS");
 *      }
 * }</pre>
 *
 * @see xyz.cronixzero.sapota.commands.Command
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandResponseHandler {

    CommandResultType type();

}
