// Coded by Flann

package mods.paxel.src;

import static net.minecraftforge.common.Property.Type.BOOLEAN;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired=true,serverSideRequired=false)
@Mod(modid=CorePaxel.modid,name="Xnet's Paxel Mod",version="#6")
public class CorePaxel {
	
	public static final String modid = "Paxel";
	public static final String texLoc = "paxel:";
	
	public static int ItemID = 14500;
	
	public static boolean enableCoreSteel			= true;
	public static boolean enableCoreStickSteel		= false;
	public static boolean enableCoreIngotRedstone	= true;
	
	public static int vanillaID, emeraldID, netherrackID, obsidianID, redstoneID, steelID;
	public static boolean enableVanilla, enableEmerald, enableNetherrack, enableObsidian, enableRedstone, enableSteel;
	public static boolean advancedRecipes, placeTorch;
	
	public static CreativeTabs tabPAXEL = new Flann_CreativeTab("tabPAXEL");
	
	public static Item stickCrafter, stickV, bodyV, headV, paxelW, paxelS, paxelI, paxelD, paxelG;
	public static Item cataE, paxelE;
	public static Item cataN, paxelN;
	public static Item cataO, paxelO;
	public static Item cataR, paxelR;
	public static Item cataSt, paxelSt;
	
	public static EnumToolMaterial WOOD = EnumHelper.addToolMaterial("WOOD", 0, 150, 2F, 0, 15);
	public static EnumToolMaterial STONE = EnumHelper.addToolMaterial("STONE", 1, 330, 4F, 1, 5);
	public static EnumToolMaterial IRON = EnumHelper.addToolMaterial("IRON", 2, 625, 6F, 2, 14);
	public static EnumToolMaterial DIAMOND = EnumHelper.addToolMaterial("DIAMOND", 3, 3900, 8F, 3, 10);
	public static EnumToolMaterial GOLD = EnumHelper.addToolMaterial("GOLD", 0, 80, 12F, 0, 22);
	public static EnumToolMaterial EMERALD = EnumHelper.addToolMaterial("EMERALD", 3, 4250, 10F, 6, 25);
	public static EnumToolMaterial NETHERRACK = EnumHelper.addToolMaterial("NETHERRACK", 1, 410, 4F, 1, 5);
	public static EnumToolMaterial OBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 3, 2620, 10F, 5, 8);
	public static EnumToolMaterial REDSTONE = EnumHelper.addToolMaterial("REDSTONE", 2, 545, 7F, 2, 14);
	public static EnumToolMaterial STEEL = EnumHelper.addToolMaterial("STEEL", 2, 1250, 6F, 3, 14);
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
			vanillaID = config.get("item", "vanillaID", 	 ItemID+0).getInt()-256;
			emeraldID = config.get("item", "emeraldID", 	 ItemID+9).getInt()-256;
			netherrackID = config.get("item", "netherrackID",ItemID+11).getInt()-256;
			obsidianID = config.get("item", "obsidianID", 	 ItemID+13).getInt()-256;
			redstoneID = config.get("item", "redstoneID", 	 ItemID+15).getInt()-256;
			steelID = config.get("item", "steelID", 		 ItemID+17).getInt()-256;
			
			advancedRecipes = config.get("enable", "advancedRecipes", false, "Enable advanced recipes").getBoolean(false);
			placeTorch = config.get("enable", "placeTorch", false, "Enable if you can place torches on right click (Consumes torches!)").getBoolean(false);
			
			enableVanilla = config.get("enable", "enableVanilla", true).getBoolean(true);
			enableEmerald = config.get("enable", "enableEmerald", true).getBoolean(true);
			enableNetherrack = config.get("enable", "enableNetherrack", true).getBoolean(true);
			enableObsidian = config.get("enable", "enableObsidian", true).getBoolean(true);
			enableRedstone = config.get("enable", "enableRedstone", true).getBoolean(true);
			enableSteel = config.get("enable", "enableSteel", true).getBoolean(true);
		config.save();
		
		Configuration coreConfig = new Configuration(new File(event.getSuggestedConfigurationFile().getPath().replace(modid, "FlannCore")));
		coreConfig.load();
			if(enableCoreSteel && enableSteel)
				set(coreConfig, "general", "enableSteel", true);
			if(enableCoreStickSteel)
				set(coreConfig, "general", "enableStickSteel", true);
			if(enableCoreIngotRedstone && enableRedstone)
				set(coreConfig, "general", "enableIngotRedstone", true);
		coreConfig.save();
		
		init_pre();
	}
	
	@Init
	public void load(FMLInitializationEvent event){
		init();
	}
	
	public void init_pre(){
		if(enableVanilla || enableEmerald || enableNetherrack || enableObsidian || enableRedstone || enableSteel){
			LanguageRegistry.instance().addStringLocalization("itemGroup.tabPAXEL", "en_US", "Paxel Mod");
		}
		if(advancedRecipes){
			stickCrafter = new Flann_ItemStickCrafter(vanillaID+0, "Stick Crafter", texLoc+"stickCrafter").setUnlocalizedName("stickCrafter").setCreativeTab(tabPAXEL);
		}
		if(enableVanilla){
			if(advancedRecipes){
				GameRegistry.registerFuelHandler(new Flann_FuelHandler_Advanced());
				
				stickV = new Flann_ItemStickPaxel(vanillaID+1, texLoc).setUnlocalizedName("stickPaxelVanilla").setCreativeTab(tabPAXEL);
				bodyV = new Flann_ItemBodyPaxel(vanillaID+2, texLoc).setUnlocalizedName("bodyPaxelVanilla").setCreativeTab(tabPAXEL);
				headV = new Flann_ItemHeadPaxel(vanillaID+3, texLoc).setUnlocalizedName("headPaxelVanilla").setCreativeTab(tabPAXEL);
				
				paxelW = new Flann_ItemPaxel(vanillaID+4, placeTorch, "Wooden Paxel", texLoc+"paxelW_a", Block.planks, WOOD).setUnlocalizedName("paxelW").setCreativeTab(tabPAXEL);
				paxelS = new Flann_ItemPaxel(vanillaID+5, placeTorch, "Stone Paxel", texLoc+"paxelS_a", Block.cobblestone, STONE).setUnlocalizedName("paxelS").setCreativeTab(tabPAXEL);
				paxelI = new Flann_ItemPaxel(vanillaID+6, placeTorch, "Iron Paxel", texLoc+"paxelI_a", Item.ingotIron, IRON).setUnlocalizedName("paxelI").setCreativeTab(tabPAXEL);
				paxelD = new Flann_ItemPaxel(vanillaID+7, placeTorch, "Diamond Paxel", texLoc+"paxelD_a", Item.diamond, DIAMOND).setUnlocalizedName("paxelD").setCreativeTab(tabPAXEL);
				paxelG = new Flann_ItemPaxel(vanillaID+8, placeTorch, "Golden Paxel", texLoc+"paxelG_a", Item.ingotGold, GOLD).setUnlocalizedName("paxelG").setCreativeTab(tabPAXEL);
			}else{
				GameRegistry.registerFuelHandler(new Flann_FuelHandler_Simple());
				
				paxelW = new Flann_ItemPaxel(vanillaID+4, placeTorch, "Wooden Paxel", texLoc+"paxelW", Block.planks, WOOD).setUnlocalizedName("paxelW").setCreativeTab(tabPAXEL);
				paxelS = new Flann_ItemPaxel(vanillaID+5, placeTorch, "Stone Paxel", texLoc+"paxelS", Block.cobblestone, STONE).setUnlocalizedName("paxelS").setCreativeTab(tabPAXEL);
				paxelI = new Flann_ItemPaxel(vanillaID+6, placeTorch, "Iron Paxel", texLoc+"paxelI", Item.ingotIron, IRON).setUnlocalizedName("paxelI").setCreativeTab(tabPAXEL);
				paxelD = new Flann_ItemPaxel(vanillaID+7, placeTorch, "Diamond Paxel", texLoc+"paxelD", Item.diamond, DIAMOND).setUnlocalizedName("paxelD").setCreativeTab(tabPAXEL);
				paxelG = new Flann_ItemPaxel(vanillaID+8, placeTorch, "Golden Paxel", texLoc+"paxelG", Item.ingotGold, GOLD).setUnlocalizedName("paxelG").setCreativeTab(tabPAXEL);
			}
		}
		if(enableEmerald){
			String ingot = "emerald";
			String ingot2 = "emerald";
			boolean useOD = false;
			ItemStack ccm = new ItemStack(Item.emerald);
			boolean ccmDam = false;
			String Mat = "Emerald";
			String M = "E";
			if(advancedRecipes){
				cataE = new Flann_ItemCataPaxel(emeraldID+0, Mat, texLoc+"stickPaxel"+M, texLoc+"bodyPaxel"+M, texLoc+"headPaxel"+M).setUnlocalizedName("cataPaxel"+Mat).setCreativeTab(tabPAXEL);
				paxelE = new Flann_ItemPaxel(emeraldID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M+"_a", ccm, ccmDam, EMERALD).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}else{
				paxelE = new Flann_ItemPaxel(emeraldID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M, ccm, ccmDam, EMERALD).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}
		}
		if(enableNetherrack){
			String ingot = "netherrack";
			String ingot2 = "netherrack";
			boolean useOD = false;
			ItemStack ccm = new ItemStack(Block.netherrack);
			boolean ccmDam = false;
			String Mat = "Netherrack";
			String M = "N";
			if(advancedRecipes){
				cataN = new Flann_ItemCataPaxel(netherrackID+0, Mat, texLoc+"stickPaxel"+M, texLoc+"bodyPaxel"+M, texLoc+"headPaxel"+M).setUnlocalizedName("cataPaxel"+Mat).setCreativeTab(tabPAXEL);
				paxelN = new Flann_ItemPaxel(netherrackID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M+"_a", ccm, ccmDam, NETHERRACK).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}else{
				paxelN = new Flann_ItemPaxel(netherrackID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M, ccm, ccmDam, NETHERRACK).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}
		}
		if(enableObsidian){
			String ingot = "obsidian";
			String ingot2 = "obsidian";
			boolean useOD = false;
			ItemStack ccm = new ItemStack(Block.obsidian);
			boolean ccmDam = false;
			String Mat = "Obsidian";
			String M = "O";
			if(advancedRecipes){
				cataO = new Flann_ItemCataPaxel(obsidianID+0, Mat, texLoc+"stickPaxel"+M, texLoc+"bodyPaxel"+M, texLoc+"headPaxel"+M).setUnlocalizedName("cataPaxel"+Mat).setCreativeTab(tabPAXEL);
				paxelO = new Flann_ItemPaxel(obsidianID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M+"_a", ccm, ccmDam, OBSIDIAN).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}else{
				paxelO = new Flann_ItemPaxel(obsidianID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M, ccm, ccmDam, OBSIDIAN).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}
		}
		if(enableRedstone){
			String ingot = "ingotRedstone";
			String ingot2 = "ingotRedstone";
			boolean useOD = true;
			ItemStack ccm = new ItemStack(Item.ingotIron);
			boolean ccmDam = false;
			String Mat = "Redstone";
			String M = "R";
			if(advancedRecipes){
				cataR = new Flann_ItemCataPaxel(redstoneID+0, Mat, texLoc+"stickPaxel"+M, texLoc+"bodyPaxel"+M, texLoc+"headPaxel"+M).setUnlocalizedName("cataPaxel"+Mat).setCreativeTab(tabPAXEL);
				paxelR = new Flann_ItemPaxel(redstoneID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M+"_a", ccm, ccmDam, REDSTONE).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}else{
				paxelR = new Flann_ItemPaxel(redstoneID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M, ccm, ccmDam, REDSTONE).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}
		}
		if(enableSteel){
			String ingot = "ingotSteel";
			String ingot2 = "ingotSteelUnhardened";
			boolean useOD = true;
			ItemStack ccm = new ItemStack(Item.ingotIron);
			boolean ccmDam = false;
			String Mat = "Steel";
			String M = "St";
			if(advancedRecipes){
				cataSt = new Flann_ItemCataPaxel(steelID+0, Mat, texLoc+"stickPaxel"+M, texLoc+"bodyPaxel"+M, texLoc+"headPaxel"+M).setUnlocalizedName("cataPaxel"+Mat).setCreativeTab(tabPAXEL);
				paxelSt = new Flann_ItemPaxel(steelID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M+"_a", ccm, ccmDam, STEEL).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}else{
				paxelSt = new Flann_ItemPaxel(steelID+1, placeTorch, Mat+" Paxel", texLoc+"paxel"+M, ccm, ccmDam, STEEL).setUnlocalizedName("paxel"+Mat).setCreativeTab(tabPAXEL);
			}
		}
	}
	public void init(){
		if(advancedRecipes){
			GameRegistry.addShapelessRecipe(new ItemStack(stickCrafter), Item.flint, Block.cobblestone);
		}
		if(enableVanilla){
			MinecraftForge.setToolClass(paxelW, "pickaxe", 0);
			MinecraftForge.setToolClass(paxelS, "pickaxe", 1);
			MinecraftForge.setToolClass(paxelI, "pickaxe", 2);
			MinecraftForge.setToolClass(paxelD, "pickaxe", 3);
			MinecraftForge.setToolClass(paxelG, "pickaxe", 0);
			
			OreDictionary.registerOre("paxelWood", paxelW);
			OreDictionary.registerOre("paxelStone", paxelS);
			OreDictionary.registerOre("paxelIron", paxelI);
			OreDictionary.registerOre("paxelDiamond", paxelD);
			OreDictionary.registerOre("paxelGold", paxelG);
			
			if(advancedRecipes){
				
				OreDictionary.registerOre("stickCrafter", stickCrafter);
				OreDictionary.registerOre("stickPaxel_W", new ItemStack(stickV, 1, 0));
				OreDictionary.registerOre("stickPaxel_S", new ItemStack(stickV, 1, 1));
				OreDictionary.registerOre("stickPaxel_I", new ItemStack(stickV, 1, 2));
				OreDictionary.registerOre("stickPaxel_D", new ItemStack(stickV, 1, 3));
				OreDictionary.registerOre("stickPaxel_G", new ItemStack(stickV, 1, 4));
				
				OreDictionary.registerOre("bodyPaxel_W", new ItemStack(bodyV, 1, 0));
				OreDictionary.registerOre("bodyPaxel_S", new ItemStack(bodyV, 1, 1));
				OreDictionary.registerOre("bodyPaxel_I", new ItemStack(bodyV, 1, 2));
				OreDictionary.registerOre("bodyPaxel_D", new ItemStack(bodyV, 1, 3));
				OreDictionary.registerOre("bodyPaxel_G", new ItemStack(bodyV, 1, 4));
				
				OreDictionary.registerOre("headPaxel_W", new ItemStack(headV, 1, 0));
				OreDictionary.registerOre("headPaxel_S", new ItemStack(headV, 1, 1));
				OreDictionary.registerOre("headPaxel_I", new ItemStack(headV, 1, 2));
				OreDictionary.registerOre("headPaxel_D", new ItemStack(headV, 1, 3));
				OreDictionary.registerOre("headPaxel_G", new ItemStack(headV, 1, 4));
				
				
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(stickV,1,0),stickCrafter,"plankWood","plankWood"));
				GameRegistry.addShapelessRecipe(new ItemStack(stickV,1,1),stickCrafter,Block.cobblestone,Block.cobblestone);
				GameRegistry.addShapelessRecipe(new ItemStack(stickV,1,2),stickCrafter,Item.ingotIron,Item.ingotIron);
				GameRegistry.addShapelessRecipe(new ItemStack(stickV,1,3),stickCrafter,Item.diamond,Item.diamond);
				GameRegistry.addShapelessRecipe(new ItemStack(stickV,1,4),stickCrafter,Item.ingotGold,Item.ingotGold);
				
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bodyV,1,0),"I  "," S ","  I",'S',"stickPaxel_W",'I',"plankWood"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bodyV,1,1),"I  "," S ","  I",'S',"stickPaxel_S",'I',Block.cobblestone));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bodyV,1,2),"I  "," S ","  I",'S',"stickPaxel_I",'I',Item.ingotIron));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bodyV,1,3),"I  "," S ","  I",'S',"stickPaxel_D",'I',Item.diamond));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bodyV,1,4),"I  "," S ","  I",'S',"stickPaxel_G",'I',Item.ingotGold));

				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(headV,1,0)," I","S ",'S',"stickPaxel_W",'I',"plankWood"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(headV,1,1)," I","S ",'S',"stickPaxel_S",'I',Block.cobblestone));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(headV,1,2)," I","S ",'S',"stickPaxel_I",'I',Item.ingotIron));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(headV,1,3)," I","S ",'S',"stickPaxel_D",'I',Item.diamond));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(headV,1,4)," I","S ",'S',"stickPaxel_G",'I',Item.ingotGold));
				
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelW,1),"  h"," b ","s  ",'h',"headPaxel_W",'b',"bodyPaxel_W",'s',"stickPaxel_W"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelS,1),"  h"," b ","s  ",'h',"headPaxel_S",'b',"bodyPaxel_S",'s',"stickPaxel_S"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelI,1),"  h"," b ","s  ",'h',"headPaxel_I",'b',"bodyPaxel_I",'s',"stickPaxel_I"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelD,1),"  h"," b ","s  ",'h',"headPaxel_D",'b',"bodyPaxel_D",'s',"stickPaxel_D"));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelG,1),"  h"," b ","s  ",'h',"headPaxel_G",'b',"bodyPaxel_G",'s',"stickPaxel_G"));
			}else{
				GameRegistry.addRecipe(new ItemStack(paxelW, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeWood, Character.valueOf('A'), Item.axeWood, Character.valueOf('S'), Item.shovelWood, Character.valueOf('|'), Item.stick);
				GameRegistry.addRecipe(new ItemStack(paxelS, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeStone, Character.valueOf('A'), Item.axeStone, Character.valueOf('S'), Item.shovelStone, Character.valueOf('|'), Item.stick);
				GameRegistry.addRecipe(new ItemStack(paxelI, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeIron, Character.valueOf('A'), Item.axeIron, Character.valueOf('S'), Item.shovelIron, Character.valueOf('|'), Item.stick);
				GameRegistry.addRecipe(new ItemStack(paxelD, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeDiamond, Character.valueOf('A'), Item.axeDiamond, Character.valueOf('S'), Item.shovelDiamond, Character.valueOf('|'), Item.stick);
				GameRegistry.addRecipe(new ItemStack(paxelG, 1), "PAS", " | ", " | ", Character.valueOf('P'), Item.pickaxeGold, Character.valueOf('A'), Item.axeGold, Character.valueOf('S'), Item.shovelGold, Character.valueOf('|'), Item.stick);
			}
		}
		if(enableEmerald){
			String ingot = "emerald";
			String ingot2 = "emerald";
			boolean useOD = false;
			ItemStack ccm = new ItemStack(Item.emerald);
			boolean ccmDam = false;
			String Mat = "Emerald";
			String M = "E";
			
			MinecraftForge.setToolClass(paxelE, "pickaxe", EMERALD.getHarvestLevel());
			OreDictionary.registerOre("paxel"+Mat, paxelE);
			
			if(advancedRecipes){
				OreDictionary.registerOre("stickPaxel_"+M, new ItemStack(cataE, 1, 0));
				OreDictionary.registerOre("bodyPaxel_"+M, new ItemStack(cataE, 1, 1));
				OreDictionary.registerOre("headPaxel_"+M, new ItemStack(cataE, 1, 2));
				
				if(useOD){
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataE,1,0),"stickCrafter",ingot,ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataE,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataE,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot));
					if(!ingot2.equals("") && !ingot2.equals(ingot)){
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataE,1,0),"stickCrafter",ingot2,ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataE,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataE,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot2));
					}
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelE,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}else{
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataE,1,0),"stickCrafter",ccm,ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataE,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataE,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelE,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}
				
			}else{
				String pick = "pickaxe"+Mat;
				String axe = "axe"+Mat;
				String spade = "shovel"+Mat;
				
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelE, 1), "PAS", " | ", " | ",Character.valueOf('P'), pick, Character.valueOf('A'), axe, Character.valueOf('S'), spade, Character.valueOf('|'), Item.stick));
			}
		}
		if(enableNetherrack){
			String ingot = "netherrack";
			String ingot2 = "netherrack";
			boolean useOD = false;
			ItemStack ccm = new ItemStack(Block.netherrack);
			boolean ccmDam = false;
			String Mat = "Netherrack";
			String M = "N";
			
			MinecraftForge.setToolClass(paxelN, "pickaxe", NETHERRACK.getHarvestLevel());
			OreDictionary.registerOre("paxel"+Mat, paxelN);
			
			if(advancedRecipes){
				OreDictionary.registerOre("stickPaxel_"+M, new ItemStack(cataN, 1, 0));
				OreDictionary.registerOre("bodyPaxel_"+M, new ItemStack(cataN, 1, 1));
				OreDictionary.registerOre("headPaxel_"+M, new ItemStack(cataN, 1, 2));
				
				if(useOD){
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataN,1,0),"stickCrafter",ingot,ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataN,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataN,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot));
					if(!ingot2.equals("") && !ingot2.equals(ingot)){
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataN,1,0),"stickCrafter",ingot2,ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataN,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataN,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot2));
					}
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelN,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}else{
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataN,1,0),"stickCrafter",ccm,ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataN,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataN,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelN,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}
				
			}else{
				String pick = "pickaxe"+Mat;
				String axe = "axe"+Mat;
				String spade = "shovel"+Mat;
				
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelN, 1), "PAS", " | ", " | ",Character.valueOf('P'), pick, Character.valueOf('A'), axe, Character.valueOf('S'), spade, Character.valueOf('|'), Item.stick));
			}
		}
		if(enableObsidian){
			String ingot = "obsidian";
			String ingot2 = "obsidian";
			boolean useOD = false;
			ItemStack ccm = new ItemStack(Block.obsidian);
			boolean ccmDam = false;
			String Mat = "Obsidian";
			String M = "O";
			
			MinecraftForge.setToolClass(paxelO, "pickaxe", OBSIDIAN.getHarvestLevel());
			OreDictionary.registerOre("paxel"+Mat, paxelO);
			
			if(advancedRecipes){
				OreDictionary.registerOre("stickPaxel_"+M, new ItemStack(cataO, 1, 0));
				OreDictionary.registerOre("bodyPaxel_"+M, new ItemStack(cataO, 1, 1));
				OreDictionary.registerOre("headPaxel_"+M, new ItemStack(cataO, 1, 2));
				
				if(useOD){
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataO,1,0),"stickCrafter",ingot,ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataO,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataO,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot));
					if(!ingot2.equals("") && !ingot2.equals(ingot)){
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataO,1,0),"stickCrafter",ingot2,ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataO,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataO,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot2));
					}
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelO,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}else{
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataO,1,0),"stickCrafter",ccm,ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataO,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataO,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelO,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}
				
			}else{
				String pick = "pickaxe"+Mat;
				String axe = "axe"+Mat;
				String spade = "shovel"+Mat;
				
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelO, 1), "PAS", " | ", " | ",Character.valueOf('P'), pick, Character.valueOf('A'), axe, Character.valueOf('S'), spade, Character.valueOf('|'), Item.stick));
			}
		}
		if(enableRedstone){
			String ingot = "ingotRedstone";
			String ingot2 = "ingotRedstone";
			boolean useOD = true;
			ItemStack ccm = new ItemStack(Item.ingotIron);
			boolean ccmDam = false;
			String Mat = "Redstone";
			String M = "R";
			
			MinecraftForge.setToolClass(paxelR, "pickaxe", REDSTONE.getHarvestLevel());
			OreDictionary.registerOre("paxel"+Mat, paxelR);
			
			if(advancedRecipes){
				OreDictionary.registerOre("stickPaxel_"+M, new ItemStack(cataR, 1, 0));
				OreDictionary.registerOre("bodyPaxel_"+M, new ItemStack(cataR, 1, 1));
				OreDictionary.registerOre("headPaxel_"+M, new ItemStack(cataR, 1, 2));
				
				if(useOD){
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataR,1,0),"stickCrafter",ingot,ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataR,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataR,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot));
					if(!ingot2.equals("") && !ingot2.equals(ingot)){
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataR,1,0),"stickCrafter",ingot2,ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataR,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataR,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot2));
					}
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelR,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}else{
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataR,1,0),"stickCrafter",ccm,ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataR,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataR,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelR,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}
				
			}else{
				String pick = "pickaxe"+Mat;
				String axe = "axe"+Mat;
				String spade = "shovel"+Mat;
				
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelR, 1), "PAS", " | ", " | ",Character.valueOf('P'), pick, Character.valueOf('A'), axe, Character.valueOf('S'), spade, Character.valueOf('|'), Item.stick));
			}
		}
		if(enableSteel){
			String ingot = "ingotSteel";
			String ingot2 = "ingotSteelUnhardened";
			boolean useOD = true;
			ItemStack ccm = new ItemStack(Item.ingotIron);
			boolean ccmDam = false;
			String Mat = "Steel";
			String M = "St";
			
			MinecraftForge.setToolClass(paxelSt, "pickaxe", STEEL.getHarvestLevel());
			OreDictionary.registerOre("paxel"+Mat, paxelSt);
			
			if(advancedRecipes){
				OreDictionary.registerOre("stickPaxel_"+M, new ItemStack(cataSt, 1, 0));
				OreDictionary.registerOre("bodyPaxel_"+M, new ItemStack(cataSt, 1, 1));
				OreDictionary.registerOre("headPaxel_"+M, new ItemStack(cataSt, 1, 2));
				
				if(useOD){
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataSt,1,0),"stickCrafter",ingot,ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataSt,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataSt,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot));
					if(!ingot2.equals("") && !ingot2.equals(ingot)){
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataSt,1,0),"stickCrafter",ingot2,ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataSt,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ingot2));
						GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataSt,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ingot2));
					}
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelSt,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}else{
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cataSt,1,0),"stickCrafter",ccm,ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataSt,1,1),"I  "," S ","  I",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cataSt,1,2)," I","S ",'S',"stickPaxel_"+M,'I',ccm));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelSt,1),"  h"," b ","s  ",'h',"headPaxel_"+M,'b',"bodyPaxel_"+M,'s',"stickPaxel_"+M));
				}
				
			}else{
				String pick = "pickaxe"+Mat;
				String axe = "axe"+Mat;
				String spade = "shovel"+Mat;
				
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paxelSt, 1), "PAS", " | ", " | ",Character.valueOf('P'), pick, Character.valueOf('A'), axe, Character.valueOf('S'), spade, Character.valueOf('|'), Item.stick));
			}
		}
	}
	
	public Property set(Configuration config, String category, String key, boolean defaultValue)
    {
		return set(config, category, key, defaultValue, null);
    }
	public Property set(Configuration config, String category, String key, boolean defaultValue, String comment)
    {
        Property prop = config.get(category, key, Boolean.toString(defaultValue), comment, BOOLEAN);
        prop.set(defaultValue);
        return prop;
    }
}
