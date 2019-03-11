package play.me.dragonrealm.geiloutils.discord.command;


import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;

import javax.annotation.Nullable;
import java.util.Set;

public class BotSender implements ConsoleCommandSender {

    public static BotSender INSTANCE = new BotSender();
    public static BotSender BLOCK_INSTANCE = new BotSender(true);
    public static BotSender SILENT_BOT = new BotSender(false, true);



    private boolean useBlockResp = false;
    private boolean isSilent = false;
    private ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();

    public BotSender(boolean useBlockResp) {
        this.useBlockResp = useBlockResp;
    }

    public BotSender(boolean useBlockResp, boolean isSilent){
        this.useBlockResp = useBlockResp;
        this.isSilent = isSilent;
    }

    public BotSender(){}

    @Override
    public String getName() {
        return "[GeiloBot]";
    }

    @Override
    public Spigot spigot() {
        return sender.spigot();
    }


    @Override
    public void sendMessage(String message) {
        if (!isSilent) {
            if (useBlockResp) {
                DiscordBotMain.getInstance().sendMessageDiscord("```" + message + "```");
                return;
            }
            DiscordBotMain.getInstance().sendMessageDiscord(message);
        }
    }

    @Override
    public void sendMessage(String[] messages) {
        for(String message : messages) {
            this.sendMessage(message);
        }
    }

    @Override
    public Server getServer() {
        return sender.getServer();
    }


    @Override
    public boolean isPermissionSet(String name) {
        return sender.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return sender.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(String name) {
        return sender.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return sender.hasPermission(perm);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return sender.addAttachment(plugin, name, value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return sender.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return sender.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return sender.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        sender.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        sender.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return sender.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return sender.isOp();
    }

    @Override
    public void setOp(boolean value) {
        sender.setOp(value);
    }

    @Override
    public boolean isConversing() {
        return sender.isConversing();
    }

    @Override
    public void acceptConversationInput(String input) {
        sender.acceptConversationInput(input);
    }

    @Override
    public boolean beginConversation(Conversation conversation) {
        return sender.beginConversation(conversation);
    }

    @Override
    public void abandonConversation(Conversation conversation) {
        sender.abandonConversation(conversation);
    }

    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        sender.abandonConversation(conversation, details);
    }

    @Override
    public void sendRawMessage(String message) {
        this.sendMessage(message);
    }
}
