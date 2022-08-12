/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 22:05
 */

package xyz.cronixzero.sapota.commands.messaging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This Class contains Messages that will be sent to {@link net.dv8tion.jda.api.entities.User}s in case of an Error
 * or an NoPermission {@link xyz.cronixzero.sapota.commands.result.CommandResult}
 */
public class MessageContainer {

    private static final Logger logger = LogManager.getLogger();

    private static final String DEFAULT_CONFIGURATION_FILE = "./commands.json";

    private String noPermissionMessage;
    private String errorMessage;
    private String wrongChannelType;

    private boolean noPermissionMessageEnabled;
    private boolean errorMessageEnabled;

    private MessageContainer(String noPermissionMessage, String errorMessage, String wrongChannelType,
                             boolean noPermissionMessageEnabled, boolean errorMessageEnabled) {
        this.noPermissionMessage = noPermissionMessage;
        this.errorMessage = errorMessage;
        this.wrongChannelType = wrongChannelType;
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

    public void setWrongChannelType(String wrongChannelType) {
        this.wrongChannelType = wrongChannelType;
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

    public String getWrongChannelType() {
        return wrongChannelType;
    }

    /*
     * STATIC FACTORY METHODS
     * */

    public static MessageContainer create(String wrongChannelType, String errorMessage, String noPermissionMessage) {
        if (wrongChannelType == null)
            throw new IllegalArgumentException("The Message for the Wrong Channel Type must be set");

        return new MessageContainer(noPermissionMessage, errorMessage, wrongChannelType,
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
            logger.atFatal().withThrowable(e).log("Could not parse '" + file.getName() + "' because it does not exist.");
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
                logger.atFatal().withThrowable(e).log("Could not copy Configuration File outside");
                return null;
            }
        }

        return fromFile(file);
    }
}
