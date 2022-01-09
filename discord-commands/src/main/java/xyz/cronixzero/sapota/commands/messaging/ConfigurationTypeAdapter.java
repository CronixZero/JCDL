/* 
Coded for sapota
Made by CronixZero
Created 09.01.2022 - 22:20
 */

package xyz.cronixzero.sapota.commands.messaging;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Locale;

public class ConfigurationTypeAdapter extends TypeAdapter<MessageContainer> {

    @Override
    public void write(JsonWriter out, MessageContainer value) throws IOException {
        out.beginObject();

        out.name("noPermissionMessage");
        out.value(value.getNoPermissionMessage());

        out.name("errorMessage");
        out.value(value.getErrorMessage());

        out.name("errorMessageEnabled");
        out.value(value.isErrorMessageEnabled());

        out.name("noPermMessageEnabled");
        out.value(value.isErrorMessageEnabled());

        out.endObject();
    }

    @Override
    public MessageContainer read(JsonReader in) throws IOException {
        String noPermissionMessage = null;
        String errorMessage = null;
        boolean errorMessageEnabled = true;
        boolean noPermissionMessageEnabled = true;

        in.beginObject();

        while(in.hasNext()) {
            if(in.peek().equals(JsonToken.END_OBJECT)) {
                in.endObject();
                break;
            }

            switch(in.nextName().toLowerCase(Locale.ROOT)) {
                case "nopermissionmessage":
                    noPermissionMessage = in.nextString();
                    break;

                case "errormessage":
                    errorMessage = in.nextString();
                    break;

                case "errormessageenabled":
                    errorMessageEnabled = in.nextBoolean();
                    break;

                case "nopermmessageenabled":
                    noPermissionMessageEnabled = in.nextBoolean();
                    break;

                default:
                    break;
            }
        }

        return new MessageContainer(noPermissionMessage, errorMessage, errorMessageEnabled, noPermissionMessageEnabled);
    }
}
