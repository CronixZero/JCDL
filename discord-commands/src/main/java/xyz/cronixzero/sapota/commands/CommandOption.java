package xyz.cronixzero.sapota.commands;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Collection;

public interface CommandOption {

    Collection<OptionData> getOptions();

}
