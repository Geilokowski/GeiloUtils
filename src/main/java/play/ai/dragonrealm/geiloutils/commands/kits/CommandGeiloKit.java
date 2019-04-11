package play.ai.dragonrealm.geiloutils.commands.kits;

import play.ai.dragonrealm.geiloutils.commands.CmdTreeBase;
import play.ai.dragonrealm.geiloutils.commands.kits.subcommands.*;

public class CommandGeiloKit extends CmdTreeBase {

	public CommandGeiloKit() {
		super("geilokit");
		addSubcommand(new CommandKitAddInv());
		addSubcommand(new CommandKitAddItem());
		addSubcommand(new CommandKitAddPermission());
		addSubcommand(new CommandKitCreate());
		addSubcommand(new CommandKitDelete());
		addSubcommand(new CommandKitRemoveItem());
		addSubcommand(new CommandKitInfo());
		addSubcommand(new CommandKitList());
		addSubcommand(new CommandKitRemovePermission());
		addSubcommand(new CommandKitSetCooldown());
	}
}
