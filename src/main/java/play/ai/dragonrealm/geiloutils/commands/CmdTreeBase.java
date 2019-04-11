package play.ai.dragonrealm.geiloutils.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CmdTreeBase extends CommandTreeBase {
    private final String name;
    public CmdTreeBase(String name){
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Well, i dont know";
    }

    //TODO: Some sort of permission check
}
