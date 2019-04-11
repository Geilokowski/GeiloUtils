package play.ai.dragonrealm.geiloutils.commands.multiworld.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.multiworld.DimensionTypeEnum;
import play.ai.dragonrealm.geiloutils.multiworld.WorldManager;
import play.ai.dragonrealm.geiloutils.multiworld.WorldVoid;

import java.util.ArrayList;
import java.util.List;

public class CommandGeiloWorldCreate extends CmdBase {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    // This is only used for auto completion which is currently not implemented
    private List<String> dimensionTypes(){
        List<String> dimensionTypeList = new ArrayList<>();
        for(DimensionTypeEnum dimensionType : DimensionTypeEnum.values()){
            dimensionTypeList.add(dimensionType.name().toLowerCase());
        }

        return dimensionTypeList;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2 && sender instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) sender;
            String dimensionName = args[0];
            if(WorldManager.dimensionNameList().contains(dimensionName)){
                sendMsg(sender, "Error: Dimension with the name " + dimensionName + " already exists");

            }else{
                try {
                    DimensionTypeEnum dimensionType = DimensionTypeEnum.valueOf(args[1].toUpperCase());

                    WorldManager.registerDimension(new WorldVoid(dimensionName, dimensionType, player.getCachedUniqueIdString(), true));
                }catch (IllegalArgumentException e){
                    sendMsg(sender, "Valid types of dimensions are Void, Overworld and Miningworld. You can also use Tab completion.");
                }
            }
        }
    }
}
