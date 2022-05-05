/* 
Coded for Sapota
Made by CronixZero
Created 18.10.2021 - 21:06
 */

package xyz.cronixzero.sapota.botdriver;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.ApiStatus;
import xyz.cronixzero.sapota.commands.CommandHandler;

public abstract class BotDriver {

    private BotDescription description;
    private CommandHandler commandHandler;
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
     * This method is called when the Application Commands are getting constructed and send to Discord
     *
     * @param handler The CommandHandler to register the commands at
     * @see CommandHandler
     * @see xyz.cronixzero.sapota.commands.Command
     * @see xyz.cronixzero.sapota.commands.DefaultCommandHandler
     */
    public void registerCommands(CommandHandler handler) {
    }

    @ApiStatus.Internal
    public void setBot(JDA bot) {
        if (this.bot != null)
            throw new IllegalStateException("JDA Bot Instance can only be set once");

        this.bot = bot;
    }

    @ApiStatus.Internal
    public void setDescription(BotDescription description) {
        this.description = description;
    }

    @ApiStatus.Internal
    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public JDA getBot() {
        return bot;
    }

    public BotDescription getDescription() {
        return description;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
