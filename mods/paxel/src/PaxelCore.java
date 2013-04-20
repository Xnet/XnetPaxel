// Coded by Flann

package mods.paxel.src;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired=true,serverSideRequired=false)
@Mod(modid=PaxelCore.modid,name="Xnet's Paxel Mod",version="#1")
public class PaxelCore {
	public static final String modid = "paxel";
	
	public static int BlockID = 1300;
	public static int ItemID = 14744;
	
	public static int paxelWID, paxelSID, paxelIID, paxelDID, paxelGID;
	
	public static EnumToolMaterial PAXELW = EnumHelper.addToolMaterial("PAXELW", 0, 150, 2.0F, 0, 15);
	public static EnumToolMaterial PAXELS = EnumHelper.addToolMaterial("PAXELS", 1, 330, 4F, 1, 5);
	public static EnumToolMaterial PAXELI = EnumHelper.addToolMaterial("PAXELI", 2, 625, 6F, 2, 14);
	public static EnumToolMaterial PAXELD = EnumHelper.addToolMaterial("PAXELD", 3, 3900, 8F, 3, 10);
	public static EnumToolMaterial PAXELG = EnumHelper.addToolMaterial("PAXELG", 0, 80, 12F, 0, 22);
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		paxelWID = config.get("items", "paxelWood", ItemID+0).getInt();
		paxelSID = config.get("items", "paxelStone", ItemID+1).getInt();
		paxelIID = config.get("items", "paxelIron", ItemID+2).getInt();
		paxelDID = config.get("items", "paxelDiamond", ItemID+3).getInt();
		paxelGID = config.get("items", "paxelGold", ItemID+4).getInt();
		
		config.save();
	}
	
	public static Item paxelW, paxelS, paxelI, paxelD, paxelG;
	
	@Init
	public void load(FMLInitializationEvent event){
		
		paxelW = new Flann_ItemPaxel(paxelWID, "paxelW", PAXELW).setUnlocalizedName("paxelW");
		paxelS = new Flann_ItemPaxel(paxelSID, "paxelS", PAXELS).setUnlocalizedName("paxelS");
		paxelI = new Flann_ItemPaxel(paxelIID, "paxelI", PAXELI).setUnlocalizedName("paxelI");
		paxelD = new Flann_ItemPaxel(paxelDID, "paxelD", PAXELD).setUnlocalizedName("paxelD");
		paxelG = new Flann_ItemPaxel(paxelGID, "paxelG", PAXELG).setUnlocalizedName("paxelG");
		
		MinecraftForge.setToolClass(paxelW, "pickaxe", 0);
		MinecraftForge.setToolClass(paxelS, "pickaxe", 1);
		MinecraftForge.setToolClass(paxelI, "pickaxe", 2);
		MinecraftForge.setToolClass(paxelD, "pickaxe", 3);
		MinecraftForge.setToolClass(paxelG, "pickaxe", 0);
		
		LanguageRegistry.addName(paxelW, "Wooden Paxel");
		LanguageRegistry.addName(paxelS, "Stone Paxel");
		LanguageRegistry.addName(paxelI, "Iron Paxel");
		LanguageRegistry.addName(paxelD, "Diamond Paxel");
		LanguageRegistry.addName(paxelG, "Golden Paxel");
		
		GameRegistry.addRecipe(new ItemStack(paxelW, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeWood, Character.valueOf('A'), Item.axeWood, Character.valueOf('S'), Item.shovelWood, Character.valueOf('|'), Item.stick);
		GameRegistry.addRecipe(new ItemStack(paxelS, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeStone, Character.valueOf('A'), Item.axeStone, Character.valueOf('S'), Item.shovelStone, Character.valueOf('|'), Item.stick);
		GameRegistry.addRecipe(new ItemStack(paxelI, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeSteel, Character.valueOf('A'), Item.axeSteel, Character.valueOf('S'), Item.shovelSteel, Character.valueOf('|'), Item.stick);
		GameRegistry.addRecipe(new ItemStack(paxelD, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeDiamond, Character.valueOf('A'), Item.axeDiamond, Character.valueOf('S'), Item.shovelDiamond, Character.valueOf('|'), Item.stick);
		GameRegistry.addRecipe(new ItemStack(paxelG, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeGold, Character.valueOf('A'), Item.axeGold, Character.valueOf('S'), Item.shovelGold, Character.valueOf('|'), Item.stick);
	}
	
	
	
}
