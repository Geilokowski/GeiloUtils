package play.me.dragonrealm.geiloutils.commands;

import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandBase extends BukkitCommand {


    protected CommandBase(String name) {
        super(name);
        this.setDescription(this.getCmdDesc());
        this.setUsage(this.getCmdUsage());
        this.setAliases(getCmdAliases());
    }

    public abstract String getCmdDesc();
    public abstract String getCmdUsage();

    public List<String> getCmdAliases() {
        return new ArrayList<>();
    }
}
