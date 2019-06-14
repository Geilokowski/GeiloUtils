package play.ai.dragonrealm.geiloutils.commands.ftblib;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.feed_the_beast.ftblib.lib.math.ChunkDimPos;
import com.feed_the_beast.ftbutilities.data.ClaimResult;
import com.feed_the_beast.ftbutilities.data.ClaimedChunk;
import com.feed_the_beast.ftbutilities.data.ClaimedChunks;
import com.feed_the_beast.ftbutilities.data.FTBUtilitiesTeamData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.FileEnum;
import play.ai.dragonrealm.geiloutils.new_configs.containers.FTBUtilsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.ChunkStorage;
import play.ai.dragonrealm.geiloutils.new_configs.models.SellablePlots;

import java.util.OptionalInt;
import java.util.Set;

public abstract class FTBIntegrationCommandBase extends CommandBase {

    public FTBUtilsConfig getConfig() {
        return (FTBUtilsConfig) GeiloUtils.getManager().getConfig(FileEnum.FTB_UTILITIES);
    }

    public void writeConfig() {
        GeiloUtils.getManager().writeToFile(FileEnum.FTB_UTILITIES);
    }

    public void messageSender(ICommandSender sender, String message, Object... args){
        String stringMsg = String.format(message, args);
        ITextComponent msg = new TextComponentString("[GeiloFTBLand] " + stringMsg);
        sender.sendMessage(msg);
    }

    /*
     * All of the following commands are here for FTB integration.
     */

    public boolean isTeamInUse(String name){
        return Universe.get().getTeam(name).isValid();
    }

    public void buildLandClaim(int price){
        ForgeTeam claimTeam = Universe.get().getTeam(getConfig().getSellingTeamName());
        ForgeTeam holdingTeam = Universe.get().getTeam(getConfig().getServerHoldingTeamName());
        Set<ClaimedChunk> claim = ClaimedChunks.instance.getTeamChunks(claimTeam, OptionalInt.empty());



        transferProperty(claim, holdingTeam);

        SellablePlots plots = new SellablePlots(price);
        for(ClaimedChunk chunk: claim){
            ChunkDimPos chunkDimPos = chunk.getPos();
            plots.addChunk(new ChunkStorage(chunkDimPos.posX, chunkDimPos.posX, chunkDimPos.dim));
        }

        getConfig().addSellables(plots);
    }

    /**
     * Transfers a set of chunks from one team to another.
     * @param claim the chunk claim by user.
     * @param team the team to transfer to.
     */
    public void transferProperty(Set<ClaimedChunk> claim, ForgeTeam team){
        for (ClaimedChunk chunk: claim) {
            ClaimedChunks.instance.unclaimChunk(chunk.getPos());

            FTBUtilitiesTeamData data = FTBUtilitiesTeamData.get(team);
            ClaimedChunk oldClaim = ClaimedChunks.instance.getChunk(chunk.getPos());
            if (oldClaim == null) {
                oldClaim = new ClaimedChunk(chunk.getPos(), data);
                ClaimedChunks.instance.addChunk(oldClaim);
            }
        }
    }

    public ForgeTeam getPlayerTeamFromConfig(String playerUID) {
        String name = getConfig().getTeamIfExist(playerUID);
        if(name == null){
            return null;
        }
        getConfig().clearTeamMemory(playerUID);
        return Universe.get().getTeam(name);
    }

    public void addPlayerToTeamMemory(String playerUID, String playerTeam){
        getConfig().addTeamMemory(playerUID, playerTeam);
        writeConfig();
    }

}
