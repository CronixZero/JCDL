package xyz.cronixzero.sapota.commands.modifier;

import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public interface SubCommandDataModifier {

    SubcommandData modify(SubcommandData data);

}
