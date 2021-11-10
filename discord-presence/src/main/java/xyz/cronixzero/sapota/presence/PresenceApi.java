/* 
Coded for Sapota
Made by CronixZero
Created 15.10.2021 - 03:14
 */

package xyz.cronixzero.sapota.presence;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PresenceApi {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final JDA bot;
    private final String configPath;
    private final List<Presence> presences = new ArrayList<>();

    private int currentPresence;

    public PresenceApi(JDA bot) {
        this.bot = bot;
        this.configPath = "presences.json";
    }

    public PresenceApi(JDA bot, String configPath) {
        this.bot = bot;
        this.configPath = configPath;
    }

    public void updatePresence() throws IOException {
        considerUpdatingPresences();

        if (presences.isEmpty())
            throw new IllegalStateException("No Presences were added");

        if (currentPresence >= presences.size())
            currentPresence = 0;

        Presence presence = presences.get(currentPresence);

        if (presence.getUrl() == null)
            bot.getPresence().setPresence(presence.getStatus(), Activity.of(presence.getType(), presence.getText()));
        else
            bot.getPresence().setPresence(presence.getStatus(), Activity.of(presence.getType(), presence.getText(), presence.getUrl()));

        currentPresence++;
    }

    private void considerUpdatingPresences() throws IOException {
        File configFile = new File(configPath);

        if (!configFile.exists()) {
            InputStream stream = getClass().getResourceAsStream(configPath);

            if (stream == null) {
                throw new NullPointerException(configPath + " does not exist");
            }

            Files.copy(stream, Paths.get("./"));

            JsonArray configPresences;
            try {
                configPresences = new Gson().fromJson(new FileReader(configFile), JsonArray.class);
            } catch (FileNotFoundException e) {
                logger.atSevere().withCause(e).log("The Configuration File '" + configPath + "' could not be found");
                return;
            }

            if (configPresences == null)
                throw new IllegalStateException("The Configuration File '" + configPath + "' could not be read");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Presence.class, new PresenceTypeAdapter());
            Gson gson = gsonBuilder.create();

            presences.clear();

            for (int i = 0; i < configPresences.size(); i++) {
                Presence presence = gson.fromJson(configPresences.get(i), Presence.class);
                presences.add(presence);
            }
        }
    }
}
