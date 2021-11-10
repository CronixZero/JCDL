/* 
Coded for Sapota
Made by CronixZero
Created 18.10.2021 - 21:58
 */

package xyz.cronixzero.sapota.botdriver;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.cronixzero.notazo.presence.PresenceApi;
import de.cronixzero.notazo.presence.PresenceTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BotDriverBootstrap {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public static void main(String[] args) {
        sendGreetings();

        File configuration = new File("./bot_driver.json");
        BotDescription description;

        if (!configuration.exists()) {
            InputStream stream = BotDriverBootstrap.class.getResourceAsStream("bot_driver.json");

            if (stream == null) {
                throw new NullPointerException("bot_driver.json is not existent");
            }

            try {
                Files.copy(stream, Paths.get("./"));
            } catch (IOException e) {
                logger.atSevere().withCause(e).log("Could not create bot_driver.json");
            }

            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BotDescription.class, new BotDescriptionTypeAdapter());
        Gson gson = gsonBuilder.create();

        try {
            description = gson.fromJson(new FileReader(configuration), BotDescription.class);
        } catch (FileNotFoundException e) {
            logger.atSevere().withCause(e).log("'%s' cold not be loaded properly", configuration.getAbsolutePath());
            return;
        }

        if (description == null)
            throw new IllegalStateException("The configuration could not be loaded properly");

        Class<?> mainClass;

        try {
            mainClass = Class.forName(description.getMain(), true, BotDriverBootstrap.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.atSevere().withCause(e).log("Could not load Main Class '%s'", description.getMain());
            return;
        }

        Class<? extends BotDriver> driverClass;
        try {
            driverClass = mainClass.asSubclass(BotDriver.class);
        } catch (ClassCastException e) {
            logger.atSevere().withCause(e).log("main class '%s' does not extend BotDriver", description.getMain());
            return;
        }


        BotDriver driver;

        try {
            driver = driverClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.atSevere().withCause(e).log("Could not instantiate Main Class '%s'", description.getMain());
            return;
        }

        driver.setDescription(description);

        prepareBot(driver);
    }

    private static void sendGreetings() {
        String greetingTitle = "\n" +
                " ____   ____  ___   ___  _____  ____  ____     ____  _____  ____    ____   ____  ____  _  _  ____  ____ \n" +
                "(  _ \\ (_  _)/ __) / __)(  _  )(  _ \\(  _ \\   (  _ \\(  _  )(_  _)  (  _ \\ (  _ \\(_  _)( \\/ )( ___)(  _ \\\n" +
                " )(_) ) _)(_ \\__ \\( (__  )(_)(  )   / )(_) )   ) _ < )(_)(   )(     )(_) ) )   / _)(_  \\  /  )__)  )   /\n" +
                "(____/ (____)(___/ \\___)(_____)(_)\\_)(____/   (____/(_____) (__)   (____/ (_)\\_)(____)  \\/  (____)(_)\\_)\n";
        String greetingDescription = "Starting this bot using Discord Bot Driver v1.0 by CronixZero!\n\n";

        logger.atInfo().log(greetingTitle + greetingDescription);
    }

    private static void prepareBot(BotDriver driver) {
        JDABuilder botBuilder = JDABuilder.createDefault(driver.getDescription().getToken());
        JDA bot;

        try {
            bot = botBuilder.build();
        } catch (LoginException e) {
            logger.atSevere().withCause(e).log("Could not login to Discord Bot");
            return;
        }

        driver.setBot(bot);
        driver.onLoad(bot);

        if (!driver.getDescription().isHandlingOwnPresence())
            new PresenceTask(new PresenceApi(bot), driver.getDescription().getPresenceDuration()).startAsync();

        try {
            bot.awaitReady();
        } catch (InterruptedException e) {
            logger.atSevere().withCause(e).log("Could not await ready");
            Thread.currentThread().interrupt();
        }
    }

}
