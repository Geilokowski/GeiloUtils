package play.ai.dragonrealm.geiloutils.commands.multiworld;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.multiworld.DimensionTypeEnum;
import play.ai.dragonrealm.geiloutils.multiworld.WorldManager;
import play.ai.dragonrealm.geiloutils.multiworld.WorldVoid;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandGeiloWorld extends CmdBase {
    @Override
    public String getName() {
        return "geiloworld";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        List<String> tmpList = new ArrayList<>();

        if(args.length == 1) {
            tmpList.add("setDimensionCount");
            tmpList.add("list");
            tmpList.add("create");

            return ArrayUtils.startsWith(tmpList, args[0]);
        }

        if(args.length == 3){
            tmpList.addAll(dimensionTypes());
            return ArrayUtils.startsWith(tmpList, args[2]);
        }

        return tmpList;
    }

    private List<String> dimensionTypes(){
        List<String> dimensionTypeList = new ArrayList<>();
        for(DimensionTypeEnum dimensionType : DimensionTypeEnum.values()){
            dimensionTypeList.add(dimensionType.name().toLowerCase());
        }

        return dimensionTypeList;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player;
        if(sender instanceof EntityPlayer){
            player = (EntityPlayer) sender;
        }else{
            return;
        }

        ITextComponent msg;
        if(args[0].equalsIgnoreCase("create") && args.length == 3){
            String dimensionName = args[1];
            if(WorldManager.dimensionNameList().contains(dimensionName)){
                sendMsg(sender, "Error: Dimension with the name " + dimensionName + " already exists");
                
            }else{
                try {
                    DimensionTypeEnum dimensionType = DimensionTypeEnum.valueOf(args[2].toUpperCase());

                    WorldManager.registerDimension(new WorldVoid(dimensionName, dimensionType, player.getCachedUniqueIdString(), true));
                }catch (IllegalArgumentException e){
                    sendMsg(sender, "Valid types of dimensions are Void, Overworld and Miningworld. You can also use Tab completion.");
                    
                }
            }
        }

        if(args[0].equalsIgnoreCase("list") && args.length == 1){
            List<String> nameList = WorldManager.dimensionNameList();
            String tmp = ConfigAccess.getGeneralConfig().getCommandPrefix() + "Found " + nameList.size() + " dimensions (including non-geilutils one): ";
            for(String name : nameList){
                tmp = tmp + name + "[" + WorldManager.dimensionMap().get(name) + "]" + ", ";
            }

            msg = new TextComponentString(tmp.substring(0, tmp.length() - 2));
            
        }

        if(args[0].equalsIgnoreCase("setDimensionCount") && args.length == 3){
            if(PermissionUtils.doesPermissionExist(args[1])){
                int numberOfDimensions = Integer.parseInt(args[2]);
                if(ConfigAccess.getMultiworldConfig().getNumberOfDimensionForPermission().containsKey(args[1])){
                    ConfigAccess.getMultiworldConfig().getNumberOfDimensionForPermission().replace(args[1], numberOfDimensions);
                }else{
                    ConfigAccess.getMultiworldConfig().getNumberOfDimensionForPermission().put(args[1], numberOfDimensions);
                }

                ConfigAccess.writeMultiworldFile(); //TODO: Remove this
                sendMsg(sender, "Everyone who has the permission '" + args[1] + "' can now create " + numberOfDimensions + " dimensions");
                
            }else{
                sendMsg(sender, "Couldn't find the permissions '" + args[1] + "'");
                
            }
        }
    }
}
