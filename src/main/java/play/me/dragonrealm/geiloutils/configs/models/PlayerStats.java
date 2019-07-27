package play.me.dragonrealm.geiloutils.configs.models;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats {
    private String name = "";
    private String uuid = "";
    private double money = 0.0;
    private String rank = "";
    private List<KitLastUsed> kitLastUsed = new ArrayList<>();
    private List<String> mutedChats = new ArrayList<>();
    private boolean directDeposit;
    private Long discordID;
    private boolean mutePaymentMsg = false;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public List<KitLastUsed> getKitLastUsed() {
        return kitLastUsed;
    }
    public void setKitLastUsed(List<KitLastUsed> kitLastUsed) {
        this.kitLastUsed = kitLastUsed;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getMutedChats() {
        return mutedChats;
    }

    public void setMutedChats(List<String> mutedChats) {
        this.mutedChats = mutedChats;
    }

    public boolean shouldDirectDeposit() {
        return directDeposit;
    }

    public void setDirectDeposit(boolean directDeposit) {
        this.directDeposit = directDeposit;
    }

    public void setMutePaymentMsg(boolean mutePaymentMsg) {
        this.mutePaymentMsg = mutePaymentMsg;
    }

    public boolean isPaymentMsgMuted() {
        return mutePaymentMsg;
    }

    public boolean isDiscordVerified(){
        return discordID != null;
    }

    public Long getDiscordID() {
        return discordID;
    }

    public void setDiscordID(Long discordID) {
        this.discordID = discordID;
    }

    @Override
    public String toString() {
        return "Player: " + getName() + " (" + getUuid() + ")";
    }
}
