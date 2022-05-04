/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 22:05
 */

package xyz.cronixzero.sapota.commands.messaging;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageContainer {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private static final String DEFAULT_CONFIGURATION_FILE = "./commands.json";

    private String noPermissionMessage;
    private String errorMessage;

    private boolean noPermissionMessageEnabled;
    private boolean errorMessageEnabled;

    private MessageContainer(String noPermissionMessage, String errorMessage,
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

    /*
     * STATIC FACTORY METHODS
     * */

    public static MessageContainer create(String errorMessage, String noPermissionMessage) {
        return new MessageContainer(noPermissionMessage, errorMessage,
                noPermissionMessage != null, errorMessage != null);
    }

    public static MessageContainer fromFile(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Could not find file '" + file.getName() + "'");
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MessageContainer.class, new ConfigurationTypeAdapter())
                .create();

        try {
            return gson.fromJson(new FileReader(file), MessageContainer.class);
        } catch (FileNotFoundException e) {
            logger.atSevere().withCause(e).log("Could not parse '" + file.getName() + "' because it does not exist.");
        }

        return null;
    }

    public static MessageContainer fromDefaultConfiguration() {
        File file = new File(DEFAULT_CONFIGURATION_FILE);

        if (!file.exists()) {
            InputStream stream = MessageContainer.class.getResourceAsStream(DEFAULT_CONFIGURATION_FILE);

            if (stream == null) {
                throw new NullPointerException("Could not create a MessageContainer from the Configuration File because it does not exist");
            }

            try {
                Files.copy(stream, Paths.get("./"));
            } catch (IOException e) {
                logger.atSevere().withCause(e).log("Could not copy Configuration File outside");
                return null;
            }
        }

        return fromFile(file);
    }
}
