package play.ai.dragonrealm.geiloutils.discord.command;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.ICommandSender;

public interface ICommand {

    /**
     * Get a localized command name, should be human readable.
     * @return the human readable command name
     */
    public String getCommandDesc();

    /**
     * Gets the command, this should be the commandline one
     * @return the commandline based command
     */
    public String getCommand();


    /**
     * Get the usage of the command, as properly formatted
     * @return a properly formatted command usage
     */
    public String getCommandUsage();

    /**
     * Called to execute the command. Return true to delete the
     * the !command that called this.
     */
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures);


    public boolean checkPermission();


    public boolean doesUserHavePermission(User discordUser);
}
