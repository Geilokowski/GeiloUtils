package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import play.ai.dragonrealm.geiloutils.new_configs.models.BannedBlock;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

public class CraftingUtils {
	private static void removeRecipe(String registryName)
	  {
		ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry)ForgeRegistries.RECIPES;
	    ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValuesCollection());
	    for (IRecipe r : recipes)
	    {
	      ItemStack output = r.getRecipeOutput();
	      if (output.getItem().equals(ItemUtils.getItemFromMod(registryName)))
	      {
	        recipeRegistry.remove(r.getRegistryName());
	        recipeRegistry.register(DummyRecipe.from(r));
	      }
	    }
	  }
	  
	  public static void removeAllRecipes()
	  {
		  for (BannedBlock bannedBlock : ConfigAccess.getBannedBlocksConfig().getBannedBlocks())
		    {
		      if (bannedBlock.getDimension().equals("all")) {
		        removeRecipe(bannedBlock.getRegistryName());
		      }
		    }
	  }
}
