package xyz.cronixzero.sapota.commands.listener;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.cronixzero.sapota.commands.Command;
import xyz.cronixzero.sapota.commands.CommandHandler;
import xyz.cronixzero.sapota.commands.messaging.MessageContainer;
import xyz.cronixzero.sapota.commands.result.CommandResult;
import xyz.cronixzero.sapota.commands.user.CommandUser;

import java.util.function.Consumer;

public class CommandListener extends ListenerAdapter {

    private final CommandHandler commandHandler;
    private final MessageContainer messages;
    private final Consumer<Command> commandSuccessAction;

    public CommandListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;

        messages = commandHandler.getMessageContainer();
        commandSuccessAction = commandHandler.getCommandSuccessAction();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
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

        switch (result.getType()) {
            case SUCCESS:
                if (result.getCommand() == null || commandSuccessAction == null) {
                    break;
                }

                commandSuccessAction.accept(result.getCommand());
                break;

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

            case WRONG_CHANNEL_TYPE:
                Message message = new MessageBuilder(String.format(messages.getWrongChannelType(),
                        user.getUser().getAsMention())).build();
                event.reply(message).queue();
                break;

            default:
                break;
        }
    }
}
