/* 
Coded for Notazo
Made by CronixZero
Created 19.10.2021 - 00:21
 */

package xyz.cronixzero.sapota.botdriver;

import com.google.common.base.MoreObjects;

public class BotDescription {

    private final String main;
    private final String token;
    private final boolean handlingOwnPresence;
    private final long presenceDuration;

    public BotDescription(String main, String token, boolean handlingOwnPresence, long presenceDuration) {
        this.main = main;
        this.token = token;
        this.handlingOwnPresence = handlingOwnPresence;
        this.presenceDuration = presenceDuration;
    }

    public String getMain() {
        return main;
    }

    public String getToken() {
        return token;
    }

    public boolean isHandlingOwnPresence() {
        return handlingOwnPresence;
    }

    public long getPresenceDuration() {
        return presenceDuration;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("main", main)
                .add("token", token)
                .add("handlingOwnPresence", handlingOwnPresence)
                .add("presenceSwitchDuration", presenceDuration)
                .toString();
    }
}
