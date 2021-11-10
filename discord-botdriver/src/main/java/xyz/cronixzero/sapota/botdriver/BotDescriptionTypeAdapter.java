/* 
Coded for Sapota
Made by CronixZero
Created 19.10.2021 - 00:28
 */

package xyz.cronixzero.sapota.botdriver;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Locale;

public class BotDescriptionTypeAdapter extends TypeAdapter<BotDescription> {

    @Override
    public void write(JsonWriter out, BotDescription value) throws IOException {
        out.beginObject();

        out.name("main");
        out.value(value.getMain());

        out.name("token");
        out.value(value.getToken());

        out.name("handlingOwnPresence");
        out.value(value.isHandlingOwnPresence());

        out.name("presenceSwitchDuration");
        out.value(value.getPresenceDuration());

        out.endObject();
    }

    @Override
    public BotDescription read(JsonReader in) throws IOException {
        String main = null;
        String token = null;
        boolean handlingOwnPresence = false;
        long presenceDuration = 35;

        in.beginObject();

        while (in.hasNext()) {
            if (in.peek().equals(JsonToken.END_OBJECT)) {
                in.endObject();
                break;
            }

            switch (in.nextName().toLowerCase(Locale.ROOT)) {
                case "main":
                    main = in.nextString();
                    break;

                case "token":
                    token = in.nextString();
                    break;

                case "handlingownpresence":
                    handlingOwnPresence = in.nextBoolean();
                    break;

                case "presenceswitchduration":
                    presenceDuration = in.nextLong();
                    break;

                default:
                    in.skipValue();
                    break;
            }
        }

        in.endObject();

        if (main == null || token == null)
            throw new IllegalArgumentException("Main and Token must be defined");

        return new BotDescription(main, token, handlingOwnPresence, presenceDuration);
    }
}
