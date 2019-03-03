package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.models.CommandsData;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class DiscordCommandConfig implements IJsonFile {

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
        if(data != null) {
            return data.isEnabled();
        } else {
            DiscordCommandConfig config = (DiscordCommandConfig) getDefaultJson();
            CommandsData newData = config.commandMeta.get(commandName);
            if(newData != null){
                commandMeta.put(commandName, newData);
                ConfigManager.writeDiscordCommandFile();
                return newData.isEnabled();
            } else {
                return false;
            }
        }
    }

    public int getPriorityLevel(String commandName) {
        CommandsData data = commandMeta.get(commandName);
        if(data != null) {
            return data.getMinPriorityLvl();
        } else {
            DiscordCommandConfig config = (DiscordCommandConfig) getDefaultJson();
            CommandsData newData = config.commandMeta.get(commandName);
            if(newData != null){
                commandMeta.put(commandName, newData);
                ConfigManager.writeDiscordFile();
                return newData.getMinPriorityLvl();
            } else {
                return -1;
            }
        }
    }

}
