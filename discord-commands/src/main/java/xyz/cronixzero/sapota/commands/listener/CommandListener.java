package xyz.cronixzero.sapota.commands.listener;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.cronixzero.sapota.commands.CommandHandler;
import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.commands.result.CommandResult;
import xyz.cronixzero.sapota.commands.user.CommandUser;

public class CommandListener extends ListenerAdapter {

    private final CommandHandler commandHandler;

    public CommandListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        CommandUser user = new CommandUser(event.getUser(), event.getMember());
        String command = event.getName();
        String group = event.getSubcommandGroup();
        String sub = event.getSubcommandName();
        CommandResult<?> result;

        event.getMember();

        // Execute the Commands
        if (sub == null) {
            result = commandHandler.dispatchCommand(command, user, event);
        } else if (group != null) {
            result = commandHandler.dispatchSubCommand(command, group, sub, user, event);
        } else {
            result = commandHandler.dispatchSubCommand(command, sub, user, event);
        }

        if (result == null)
            throw new IllegalStateException("Got null returned as CommandResult from Command " + command);

        // Send Messages from the MessageContainer if the User has no Permissions or an Error happened
        MessageContainer messages = commandHandler.getMessageContainer();
        switch (result.getType()) {
            case ERROR:
                if (messages.isErrorMessageEnabled()) {
                    Message message = new MessageBuilder(String.format(messages.getErrorMessage(),
                            user.getUser().getAsMention())).build();
                    event.reply(message).queue();
                }
                break;

            case NO_PERMISSIONS:
                if (messages.isNoPermissionMessageEnabled()) {
                    Message message = new MessageBuilder(String.format(messages.getNoPermissionMessage(),
                            user.getUser().getAsMention())).build();
                    event.reply(message).queue();
                }
                break;

            default:
                break;
        }
    }
}
