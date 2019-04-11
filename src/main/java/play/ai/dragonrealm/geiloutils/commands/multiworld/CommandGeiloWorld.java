package play.ai.dragonrealm.geiloutils.commands.multiworld;

import play.ai.dragonrealm.geiloutils.commands.CmdTreeBase;
import play.ai.dragonrealm.geiloutils.commands.multiworld.subcommands.CommandGeiloWorldCreate;
import play.ai.dragonrealm.geiloutils.commands.multiworld.subcommands.CommandGeiloWorldList;
import play.ai.dragonrealm.geiloutils.commands.multiworld.subcommands.CommandGeiloWorldSetDimensionCount;

public class CommandGeiloWorld extends CmdTreeBase {
    public CommandGeiloWorld() {
        super("geiloworld");
        addSubcommand(new CommandGeiloWorldCreate());
        addSubcommand(new CommandGeiloWorldList());
        addSubcommand(new CommandGeiloWorldSetDimensionCount());
    }
}
