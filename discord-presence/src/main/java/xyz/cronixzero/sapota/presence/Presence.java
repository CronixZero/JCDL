/* 
Coded for Sapota
Made by CronixZero
Created 15.10.2021 - 02:50
 */

package xyz.cronixzero.sapota.presence;

import com.google.common.base.MoreObjects;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Presence {

    private final String text;
    private final Activity.ActivityType type;
    private final OnlineStatus status;

    private String url;

    public Presence(String text, Activity.ActivityType type, OnlineStatus status) {
        this.text = text;
        this.type = type;
        this.status = status;
    }

    public Presence(String text, Activity.ActivityType type, OnlineStatus status, String url) {
        this.text = text;
        this.type = type;
        this.status = status;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public Activity.ActivityType getType() {
        return type;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("type", type)
                .add("status", status)
                .add("url", url)
                .toString();
    }
}
