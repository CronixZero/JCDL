/* 
Coded for Sapota
Made by CronixZero
Created 15.10.2021 - 03:37
 */

package xyz.cronixzero.sapota.presence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.io.IOException;
import java.util.Locale;

public class PresenceTypeAdapter extends TypeAdapter<Presence> {

    @Override
    public void write(JsonWriter out, Presence value) throws IOException {
        out.beginObject();

        out.name("text");
        out.value(value.getText());

        out.name("type");
        out.value(value.getType().toString());

        out.name("status");
        out.value(value.getStatus().toString());

        if (value.getUrl() != null) {
            out.name("url");
            out.value(value.getUrl());
        }

        out.endObject();
    }

    @Override
    public Presence read(JsonReader in) throws IOException {
        String text = "Failed to load Presence... [" + in.getPath() + "]";
        String type = "COMPETING";
        String status = "DO_NOT_DISTURB";
        String url = null;

        in.beginObject();

        while (in.hasNext()) {
            if (in.peek().equals(JsonToken.END_OBJECT)) {
                in.endObject();
                break;
            }

            switch (in.nextName().toLowerCase(Locale.ROOT)) {
                case "text":
                    text = in.nextString();
                    break;

                case "type":
                    type = in.nextString();
                    break;

                case "status":
                    status = in.nextString();
                    break;

                case "url":
                    url = in.nextString();
                    break;

                default:
                    in.skipValue();
                    break;
            }
        }

        return new Presence(text, Activity.ActivityType.valueOf(type), OnlineStatus.valueOf(status), url);
    }
}
