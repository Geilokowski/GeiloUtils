package play.me.dragonrealm.geiloutils.configs.containers;


import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.models.CommandsData;
import play.me.dragonrealm.geiloutils.discord.command.CommandProcessor;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class DiscordCommandConfig implements IJsonFile {

    private HashMap<String, CommandsData> commandMeta;

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
                ConfigAccess.writeDiscordCommandFile();
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
                ConfigAccess.writeDiscordFile();
                return newData.getMinPriorityLvl();
            } else {
                return -1;
            }
        }
    }

}
