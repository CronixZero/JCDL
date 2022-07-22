package xyz.cronixzero.sapota.commands.modifier;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface CommandDataModifier {

    SlashCommandData modify(SlashCommandData original);

}
