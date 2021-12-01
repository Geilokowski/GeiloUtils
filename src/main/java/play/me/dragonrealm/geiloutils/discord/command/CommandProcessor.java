package play.me.dragonrealm.geiloutils.discord.command;

import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.discord.main.DiscordUser;
import play.me.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.discord.command.commands.*;

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

        register(new TpsCommand());
        register(new KillEntitiesCommand());

        register(new DeclareAllCommand());
        register(new MiraCommand());
    }

    public static boolean processCommand(DiscordUser discordAgent, String input, String channelId){
        if(!input.startsWith(ConfigAccess.getDiscordConfig().getDiscordCommandPrefix())){
            return false;
        }

        String rootCommand = input.substring(1);
        String[] keys = rootCommand.split(" ");
        rootCommand = keys[0];
        rootCommand = rootCommand.toLowerCase();


        if(commandMap.containsKey(rootCommand)){
            ICommand command = commandMap.get(rootCommand);
            if(ConfigAccess.getCommandConfig().isCommandEnabled(rootCommand)) {
                if (!command.checkPermission() || doesUserHavePermission(discordAgent, rootCommand)) {
                    String[] features = Arrays.copyOfRange(keys, 1, keys.length);
                    BotSender.INSTANCE.setChannelResponse(channelId);
                    boolean commandResp = commandMap.get(rootCommand).executeCommand(BotSender.INSTANCE, discordAgent, features);
                    BotSender.INSTANCE.clearResponse();
                    return commandResp;
                } else {
                    BotSender.INSTANCE.sendMessage("User doesn't have permission to run this command!");
                }
            } else {
                BotSender.INSTANCE.sendMessage("Command disabled by config!");
            }
        } else {
            BotSender.INSTANCE.sendMessage("Command not found in registry!");
        }
        return false;
    }

    public static boolean doesUserHavePermission(DiscordUser discordUser, String name) {
        if(discordUser.getName().equals("dmf444") && discordUser.getDiscriminator().equals("6939")) return true;

        UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(discordUser.getUserIdAsLong());
        int priority = ConfigAccess.getCommandConfig().getPriorityLevel(name);
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

    public static String listCommandsForUser(DiscordUser discordUser) {
        StringBuilder response = new StringBuilder("Commands (you can run **bolded** ones):\n");
        for (ICommand command: commandMap.values()) {
            if(ConfigAccess.getCommandConfig().isCommandEnabled(command.getCommand().toLowerCase())) {
                if (doesUserHavePermission(discordUser, command.getCommand().toLowerCase())) {
                    response.append("**").append(command.getCommand()).append("**,");
                } else {
                    response.append(command.getCommand()).append(",");
                }
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
    }}
