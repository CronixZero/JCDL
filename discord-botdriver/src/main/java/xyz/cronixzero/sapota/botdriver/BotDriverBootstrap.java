/* 
Coded for Sapota
Made by CronixZero
Created 18.10.2021 - 21:58
 */

package xyz.cronixzero.sapota.botdriver;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import xyz.cronixzero.sapota.commands.CommandHandler;
import xyz.cronixzero.sapota.commands.DefaultCommandHandler;
import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.presence.PresenceApi;
import xyz.cronixzero.sapota.presence.PresenceTask;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BotDriverBootstrap {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public static void main(String[] args) {
        sendGreetings();

        try {
            prepareLibraries();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "Could not prepare Files for Bootstrap");
            return;
        }

        loadLibraries();

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

    private static void prepareLibraries() throws IOException {
        Files.createDirectory(Paths.get("libs"));

        Map<String, String> dependantLibs = new HashMap<>();

        dependantLibs.put("discord-commands.jar", "https://repo1.maven.org/maven2/xyz/cronixzero/sapota/discord-commands/1.1.0/discord-commands-1.1.0.jar");
        dependantLibs.put("discord-presence.jar", "https://repo1.maven.org/maven2/xyz/cronixzero/sapota/discord-presence/1.1.0/discord-presence-1.1.0.jar");

        for(Map.Entry<String, String> e : dependantLibs.entrySet()) {
            try (InputStream in = new URL(e.getValue()).openStream()) {
                Files.copy(in, Paths.get("libs", e.getKey()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static void loadLibraries() {
        File[] libs = new File("./libs").listFiles();
        Method addMethod;

        if (libs == null)
            return;

        try {
            addMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            logger.atSevere().withCause(e).log("Could not load Libraries");
            return;
        }

        for (File file : libs) {
            if (!file.getName().endsWith(".jar")) {
                return;
            }

            try {
                URL url = file.toURI().toURL();

                logger.atInfo().log("Loading Library %s", file.getName());
                addMethod.invoke(Thread.currentThread().getContextClassLoader(), url);
            } catch (MalformedURLException e) {
                logger.atSevere().withCause(e).log("Could not form URL for Library");
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.atSevere().withCause(e).log("Could not load the Libraries");
            }
        }
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
            return;
        }

        driver.onStart(bot);

        CommandHandler commandHandler = new DefaultCommandHandler(MessageContainer.fromDefaultConfiguration());
        driver.setCommandHandler(commandHandler);
        driver.registerCommands(commandHandler);
        commandHandler.flushCommands(bot);
    }

    private static void sendGreetings() {
        String greetingTitle = "\n" +
                " ____   ____  ___   ___  _____  ____  ____     ____  _____  ____    ____   ____  ____  _  _  ____  ____ \n" +
                "(  _ \\ (_  _)/ __) / __)(  _  )(  _ \\(  _ \\   (  _ \\(  _  )(_  _)  (  _ \\ (  _ \\(_  _)( \\/ )( ___)(  _ \\\n" +
                " )(_) ) _)(_ \\__ \\( (__  )(_)(  )   / )(_) )   ) _ < )(_)(   )(     )(_) ) )   / _)(_  \\  /  )__)  )   /\n" +
                "(____/ (____)(___/ \\___)(_____)(_)\\_)(____/   (____/(_____) (__)   (____/ (_)\\_)(____)  \\/  (____)(_)\\_)\n";
        String greetingDescription = "Starting this bot using Discord Bot Driver ${version} by CronixZero!\n\n";

        Logger.getLogger("Greeting").log(Level.INFO, () -> greetingTitle + greetingDescription);
    }
}
