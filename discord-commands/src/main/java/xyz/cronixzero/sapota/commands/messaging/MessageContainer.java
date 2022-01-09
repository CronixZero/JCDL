/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 22:05
 */

package xyz.cronixzero.sapota.commands.messaging;

public class MessageContainer {

    private String noPermissionMessage;
    private String errorMessage;

    private boolean noPermissionMessageEnabled;
    private boolean errorMessageEnabled;

    public MessageContainer(String noPermissionMessage, String errorMessage,
                            boolean noPermissionMessageEnabled, boolean errorMessageEnabled) {
        this.noPermissionMessage = noPermissionMessage;
        this.errorMessage = errorMessage;
        this.noPermissionMessageEnabled = noPermissionMessageEnabled;
        this.errorMessageEnabled = errorMessageEnabled;
    }

    public void setErrorMessageEnabled(boolean errorMessageEnabled) {
        this.errorMessageEnabled = errorMessageEnabled;
    }

    public void setNoPermissionMessageEnabled(boolean noPermissionMessageEnabled) {
        this.noPermissionMessageEnabled = noPermissionMessageEnabled;
    }

    public void setNoPermissionMessage(String noPermissionMessage) {
        this.noPermissionMessage = noPermissionMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isNoPermissionMessageEnabled() {
        return noPermissionMessageEnabled;
    }

    public boolean isErrorMessageEnabled() {
        return errorMessageEnabled;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }
}
