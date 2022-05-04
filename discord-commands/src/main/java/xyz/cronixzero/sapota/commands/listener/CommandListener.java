package xyz.cronixzero.sapota.commands.listener;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.cronixzero.sapota.commands.CommandHandler;
import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.commands.result.CommandResult;

public class CommandListener extends ListenerAdapter {

    private final CommandHandler commandHandler;

    public CommandListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        User user = event.getUser();
        String command = event.getName();
        String group = event.getSubcommandGroup();
        String sub = event.getSubcommandName();
        CommandResult<?> result;

        if (sub == null) {
            result = commandHandler.dispatchCommand(command, user, event);
        } else if (group != null) {
            result = commandHandler.dispatchSubCommand(command, group, sub, user, event);
        } else {
            result = commandHandler.dispatchSubCommand(command, sub, user, event);
        }

        if (result == null)
            throw new IllegalStateException("Got null returned as CommandResult from Command " + command);

        MessageContainer messages = commandHandler.getMessageContainer();
        switch (result.getType()) {
            case ERROR:
                if (messages.isErrorMessageEnabled()) {
                    Message message = new MessageBuilder(String.format(messages.getErrorMessage(), user.getAsMention()))
                            .build();
                    event.getChannel().sendMessage(message).queue();
                }
                break;

            case NO_PERMISSIONS:
                if (messages.isNoPermissionMessageEnabled()) {
                    Message message = new MessageBuilder(String.format(messages.getNoPermissionMessage(), user.getAsMention()))
                            .build();
                    event.getChannel().sendMessage(message).queue();
                }
                break;

            default:
                break;
        }
    }
}
