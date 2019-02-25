package play.ai.dragonrealm.geiloutils.new_configs.discord;

import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class DiscordCommandConfig implements IJsonFile {
    public static final String DISCORD_COMMAND_NAME = "discordCommands";

    HashMap<String, CommandsData> commandMeta;

    @Nonnull
    @Override
    public String getFileName() {
        return "discord_commands.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        DiscordCommandConfig discordCommandConfig = new DiscordCommandConfig();
        HashMap<String, CommandsData> data = new HashMap<>();
        for(String command : CommandProcessor.getCommandNames()) {
            CommandsData commandsData = new CommandsData(true, -1);
            data.put(command, commandsData);
        }
        discordCommandConfig.setCommandMeta(data);
        return discordCommandConfig;
    }


    public void setCommandMeta(HashMap<String, CommandsData> commandMeta) {
        this.commandMeta = commandMeta;
    }

    public boolean isCommandEnabled(String commandName) {
        CommandsData data = commandMeta.get(commandName);
        return data.isEnabled();
    }

    public int getPriorityLevel(String commandName) {
        CommandsData data = commandMeta.get(commandName);
        return data.getMinPriorityLvl();
    }

}
