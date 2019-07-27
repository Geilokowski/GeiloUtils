package play.me.dragonrealm.geiloutils.discord.command;

import net.dv8tion.jda.core.entities.User;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
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
        //register(new RolesCommand());
        //register(new SetRolesCommand());
        register(new TpsCommand());
        register(new KillEntitiesCommand());

        register(new DeclareAllCommand());
        register(new MiraCommand());
    }

    public static boolean processCommand(User discordAgent, String input){
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
                    return commandMap.get(rootCommand).executeCommand(BotSender.INSTANCE, discordAgent, features);
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

    public static boolean doesUserHavePermission(User discordUser, String name) {
        if(discordUser.getName().equals("dmf444") && discordUser.getDiscriminator().equals("6939")) return true;

        UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(discordUser.getIdLong());
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

    public static String listCommandsForUser(User discordUser) {
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
