/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 18:53
 */

package xyz.cronixzero.sapota.commands.result;

public enum CommandResultType {
    /**
     * Successfully executed
     */
    SUCCESS,
    /**
     * Executed with Error
     */
    ERROR,
    /**
     * User has no Permissions for Command
     */
    NO_PERMISSIONS,
    /**
     * Something unknown happened
     */
    UNKNOWN,
    /**
     * Custom Response
     */
    DYNAMIC,
    /**
     * User executed Command via Direct Messages, although it's a GuildCommand
     */
    WRONG_CHANNEL_TYPE

}
