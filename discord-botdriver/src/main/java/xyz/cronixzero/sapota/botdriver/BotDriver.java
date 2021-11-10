/* 
Coded for Sapota
Made by CronixZero
Created 18.10.2021 - 21:06
 */

package xyz.cronixzero.sapota.botdriver;

import net.dv8tion.jda.api.JDA;

public abstract class BotDriver {

    private BotDescription description;
    private JDA bot;

    /**
     * This method is called while constructing the Bot
     *
     * @param bot The Bot Instance before final initialization
     * @see JDA
     */
    public void onLoad(JDA bot) {
    }

    /**
     * This method is called after constructing the bot
     *
     * @param bot The Bot Instance after final initialization
     * @see JDA
     */
    public void onStart(JDA bot) {
    }

    /**
     * @deprecated Only for internal uses
     */
    @Deprecated
    public void setBot(JDA bot) {
        if (this.bot != null)
            throw new IllegalStateException("JDA Bot Instance can only be set once");

        this.bot = bot;
    }

    public void setDescription(BotDescription description) {
        this.description = description;
    }

    public JDA getBot() {
        return bot;
    }

    public BotDescription getDescription() {
        return description;
    }
}
