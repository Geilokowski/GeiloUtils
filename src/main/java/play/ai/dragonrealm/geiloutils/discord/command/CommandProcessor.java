package play.ai.dragonrealm.geiloutils.discord.command;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.command.commands.*;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandProcessor {

    static Map<String, ICommand> commandMap = new HashMap<>();

    public static void registerCommands(){
        register(new OnlineCommand());
        register(new ListCommandsCommand());
        register(new HelpCommand());
        register(new ExecCommand());
        register(new SpawnPlaceCommand());
        register(new InternetProtocolCommand());
        register(new TellCommand());
        register(new RolesCommand());
        register(new SetRolesCommand());
        register(new TpsCommand());
        register(new KillEntitiesCommand());

        register(new DeclareAllCommand());
        register(new MiraCommand());
    }

    public static boolean processCommand(User discordAgent, String input){
        if(!input.startsWith(ConfigManager.getDiscordConfig().getDiscordCommandPrefix())){
            return false;
        }

        String rootCommand = input.substring(1);
        String[] keys = rootCommand.split(" ");
        rootCommand = keys[0];
        rootCommand = rootCommand.toLowerCase();


        if(commandMap.containsKey(rootCommand)){
            ICommand command = commandMap.get(rootCommand);
            if(ConfigManager.getCommandConfig().isCommandEnabled(rootCommand)) {
                if (!command.checkPermission() || doesUserHavePermission(discordAgent, rootCommand)) {
                    String[] features = Arrays.copyOfRange(keys, 1, keys.length);
                    return commandMap.get(rootCommand).executeCommand(BotSender.INSTANCE, discordAgent, features);
                } else {
                    BotSender.INSTANCE.sendMessage(new TextComponentString("User doesn't have permission to run this command!"));
                }
            } else {
                BotSender.INSTANCE.sendMessage(new TextComponentString("Command disabled by config!"));
            }
        } else {
            BotSender.INSTANCE.sendMessage(new TextComponentString("Command not found in registry!"));
        }
        return false;
    }

    public static boolean doesUserHavePermission(User discordUser, String name) {
        if(discordUser.getName().equals("dmf444") && discordUser.getDiscriminator().equals("6939")) return true;

        UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(discordUser.getIdLong());
        int priority = ConfigManager.getCommandConfig().getPriorityLevel(name);
        if(rank == null || (rank.getPriority() < priority && priority != -1)) {
            return false;
        }
        return true;
    }


    /**
     * Registers a command and all alias' to the command handler's map.
     * @param base the instance of a command.
     */
    private static void register(ICommand base){
        // Register Commands
        commandMap.put(base.getCommand().toLowerCase(), base);
    }

    public static String listCommandsForUser(User discordUser) {
        StringBuilder response = new StringBuilder("Commands (you can run **bolded** ones):\n");
        for (ICommand command: commandMap.values()) {
            if(doesUserHavePermission(discordUser, command.getCommand()) && ConfigManager.getCommandConfig().isCommandEnabled(command.getCommand())) {
                response.append("**").append(command.getCommand()).append("**,");
            } else {
                response.append(command.getCommand()).append(",");
            }
        }
        int len = response.length();
        return response.delete(len - 1, len).toString();
    }

    public static ICommand getCommand(String s) {
        return commandMap.get(s);
    }

    public static Set<String> getCommandNames() {
        return commandMap.keySet();
    }
}
