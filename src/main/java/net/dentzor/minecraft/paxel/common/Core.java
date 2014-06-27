package net.dentzor.minecraft.paxel.common;

import static net.minecraftforge.common.config.Property.Type.BOOLEAN;

import java.io.File;

import net.dentzor.minecraft.paxel.item.ItemBodyPaxel;
import net.dentzor.minecraft.paxel.item.ItemCatalystPaxel;
import net.dentzor.minecraft.paxel.item.ItemHeadPaxel;
import net.dentzor.minecraft.paxel.item.ItemPaxel;
import net.dentzor.minecraft.paxel.item.ItemStickCrafter;
import net.dentzor.minecraft.paxel.item.ItemStickPaxel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = net.dentzor.minecraft.paxel.common.Core.modid, name = net.dentzor.minecraft.paxel.common.Core.modname, version = net.dentzor.minecraft.paxel.common.Core.modversion)
public class Core {
    @SidedProxy(clientSide = "net.dentzor.minecraft.paxel.common.ClientProxy", serverSide = "net.dentzor.minecraft.paxel.common.CommonProxy")
    public static CommonProxy  proxy;

    // Set mod-specific names etc.
    public static final String modid                           = "paxel";
    public static final String modname                         = "Xnet's Paxel Mod";
    public static final String modversion                      = "#8";
    public static final String textureLocation                 = Core.modid + ":";

    // FlannCore settings
    public static boolean      enableCoreSteel                 = true;
    public static boolean      enableCoreStickSteel            = false;
    public static boolean      enableCoreIngotRedstone         = true;
    public static boolean      commonConfig;

    // Additional Material Paxel (using arrays to ease the process)
    static String[]            ingot1                          = { "emerald", "netherrack", "obsidian", "ingotRedstone", "ingotSteel" };
    static String[]            ingot2                          = { "emerald", "netherrack", "obsidian", "ingotRedstone", "ingotSteelUnhardened" };
    static boolean[]           useOreDictionary                = { false, false, false, true, true };
    static ItemStack[]         customCraftingMaterial          = { new ItemStack(Items.emerald), new ItemStack(Blocks.netherrack), new ItemStack(Blocks.obsidian),
            new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot) };
    static boolean[]           customCraftingMaterialHasDamage = { false, false, false, false, false };
    static String[]            materialName                    = { "Emerald", "Netherrack", "Obsidian", "Redstone", "Steel" };
    static String[]            materialLetter                  = { "E", "N", "O", "R", "St" };

    // Choose which creativetab all the items should be in
    public static CreativeTabs tabPaxel                        = CreativeTabs.tabTools;

    // Enable tool-sets
    public static boolean      enableVanilla, enableEmerald, enableNetherrack, enableObsidian, enableRedstone, enableSteel;

    // Enable functions
    public static boolean      advancedRecipes, placeTorch, checkForInfinity, checkForCreative;
    
    // Additional torch settings
    public static String blockToPlace, itemToConsume;
    public static int amountToConsume;

    // Create all items in the paxel mod (NOT initialize)
    public static Item         stickCrafter, stickVanilla, bodyVanilla, headVanilla, paxelWood, paxelStone, paxelIron, paxelDiamond, paxelGold, catalystEmerald, paxelEmerald,
            catalystNetherrack, paxelNetherrack, catalystObsidian, paxelObsidian, catalystRedstone, paxelRedstone, catalystSteel, paxelSteel;

    // Create all the tool-materials used in the paxel mod (NOT initialize)
    public static Item.ToolMaterial WOOD, STONE, IRON, DIAMOND, GOLD, EMERALD, NETHERRACK, OBSIDIAN, REDSTONE, STEEL;

    // Constructor
    public Core() {

    }

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Core.loadConfig(event);
        Core.init_materials();
        Core.init_items();
        Core.registerItems();
    }

    @EventHandler
    public static void atInit(FMLInitializationEvent event) {
        Core.registerOreDictionary();
        Core.init_crafting();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }

    // Register all items in forge GameRegistry
    public static void registerItems() {
        if (Core.advancedRecipes) {
            GameRegistry.registerItem(Core.stickCrafter, "stickCrafter");
        }
        if (Core.enableVanilla) {
            GameRegistry.registerItem(Core.paxelWood, "paxelWood");
            GameRegistry.registerItem(Core.paxelStone, "paxelStone");
            GameRegistry.registerItem(Core.paxelIron, "paxelIron");
            GameRegistry.registerItem(Core.paxelDiamond, "paxelDiamond");
            GameRegistry.registerItem(Core.paxelGold, "paxelGold");
            if (advancedRecipes) {
                GameRegistry.registerItem(stickVanilla, "stickPaxelVanilla");
                GameRegistry.registerItem(bodyVanilla, "bodyPaxelVanilla");
                GameRegistry.registerItem(headVanilla, "headPaxelVanilla");
            }
        }
        if (Core.enableEmerald) {
            GameRegistry.registerItem(Core.paxelEmerald, "paxelEmerald");
            if (Core.advancedRecipes) {
                GameRegistry.registerItem(Core.catalystEmerald, "catalystEmerald");
            }
        }
        if (Core.enableNetherrack) {
            GameRegistry.registerItem(Core.paxelNetherrack, "paxelNetherrack");
            if (Core.advancedRecipes) {
                GameRegistry.registerItem(Core.catalystNetherrack, "catalystNetherrack");
            }
        }
        if (Core.enableObsidian) {
            GameRegistry.registerItem(Core.paxelObsidian, "paxelObsidian");
            if (Core.advancedRecipes) {
                GameRegistry.registerItem(Core.catalystObsidian, "catalystObsdian");
            }
        }
        if (Core.enableRedstone) {
            GameRegistry.registerItem(Core.paxelRedstone, "paxelRedstone");
            if (Core.advancedRecipes) {
                GameRegistry.registerItem(Core.catalystRedstone, "catalystRedstone");
            }
        }
        if (Core.enableSteel) {
            GameRegistry.registerItem(Core.paxelSteel, "paxelSteel");
            if (Core.advancedRecipes) {
                GameRegistry.registerItem(Core.catalystSteel, "catalystSteel");
            }
        }
    }

    // Initialize materials
    public static void init_materials() {
        if (advancedRecipes) { // x3,2142857142857142857142857142857
            // Vanilla
            Core.WOOD = EnumHelper.addToolMaterial("WOOD", 0, 190, 2F, 0, 15); // 59
            Core.STONE = EnumHelper.addToolMaterial("STONE", 1, 421, 4F, 1, 5); // 131
            Core.IRON = EnumHelper.addToolMaterial("IRON", 2, 804, 6F, 2, 14); // 250
            Core.DIAMOND = EnumHelper.addToolMaterial("DIAMOND", 3, 5018, 8F, 3, 10); // 1561
            Core.GOLD = EnumHelper.addToolMaterial("GOLD", 0, 103, 12F, 0, 22); // 32
            // New
            Core.EMERALD = EnumHelper.addToolMaterial("EMERALD", 3, 411, 12F, 2.5F, 20); // 128
            Core.NETHERRACK = EnumHelper.addToolMaterial("NETHERRACK", 2, 482, 5F, 1.5F, 7); // 150
            Core.OBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 3, 2732, 7.1F, 2.7F, 8); // 850
            Core.REDSTONE = EnumHelper.addToolMaterial("REDSTONE", 2, 968, 10F, 2.4F, 16); // 300
            Core.STEEL = EnumHelper.addToolMaterial("STEEL", 2, 1607, 7.2F, 3.6F, 12); // 500
        } else { // x2,5
            // Vanilla
            Core.WOOD = EnumHelper.addToolMaterial("WOOD", 0, 148, 2F, 0, 15); // 59
            Core.STONE = EnumHelper.addToolMaterial("STONE", 1, 330, 4F, 1, 5); // 131
            Core.IRON = EnumHelper.addToolMaterial("IRON", 2, 625, 6F, 2, 14); // 250
            Core.DIAMOND = EnumHelper.addToolMaterial("DIAMOND", 3, 3903, 8F, 3, 10); // 1561
            Core.GOLD = EnumHelper.addToolMaterial("GOLD", 0, 80, 12F, 0, 22); // 32
            // New
            Core.EMERALD = EnumHelper.addToolMaterial("EMERALD", 3, 320, 12F, 2.5F, 20); // 128
            Core.NETHERRACK = EnumHelper.addToolMaterial("NETHERRACK", 2, 375, 5F, 1.5F, 7); // 150
            Core.OBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 3, 2125, 7.1F, 2.7F, 8); // 850
            Core.REDSTONE = EnumHelper.addToolMaterial("REDSTONE", 2, 750, 10F, 2.4F, 16); // 300
            Core.STEEL = EnumHelper.addToolMaterial("STEEL", 2, 1250, 7.2F, 3.6F, 12); // 500
        }
    }

    // Load the config-values
    public static void loadConfig(FMLPreInitializationEvent event) {
        String categoryEnable, categoryTorch, categoryGeneralCore;

        Configuration coreConfig = new Configuration(new File(event.getSuggestedConfigurationFile().getPath().replace(Core.modid, "FlannCore")));
        coreConfig.load();
        Core.commonConfig = coreConfig.get("core", "combineConfigs", false).getBoolean(false);
        if (Core.commonConfig) {
            categoryEnable = modid + "_Enable";
            categoryTorch = modid+"_Torch_settings";
            categoryGeneralCore = "core_General";
        } else {
            categoryEnable = "enable";
            categoryTorch = "Torch_settings";
            categoryGeneralCore = "general";
        }
        if (Core.enableCoreSteel) {
            Core.set(coreConfig, categoryGeneralCore, "enableSteel", true);
        }
        if (Core.enableCoreStickSteel) {
            Core.set(coreConfig, categoryGeneralCore, "enableStickSteel", true);
        }
        if (Core.enableCoreIngotRedstone) {
            Core.set(coreConfig, categoryGeneralCore, "enableIngotRedstone", true);
        }
        coreConfig.save();

        Configuration config;
        if (Core.commonConfig == false) {
            config = new Configuration(event.getSuggestedConfigurationFile());
        } else {
            config = coreConfig;
        }

        config.load();
        Core.advancedRecipes = config.get(categoryEnable, "advancedRecipes", false, "Enable advanced recipes").getBoolean(false);
        Core.placeTorch = config.get(categoryEnable, "placeTorch", false, "Enable if you can place torches on right click (Consumes torches!)").getBoolean(false);
        
        Core.blockToPlace = config.get(categoryTorch, "blockToPlace", "planks", "Here you can customize which block to place on rightclick").getString();
        Core.itemToConsume = config.get(categoryTorch, "itemToConsume", "torch", "Here you can customize which item to consume on placing a block").getString();
        Core.amountToConsume = config.get(categoryTorch, "amountToConsume", 1, "Here you can customize the amount of items to be consumed on use").getInt();
        Core.checkForInfinity = config.get(categoryTorch, "checkForInfinity", true, "Do not consume items if you have infinity-enchantment").getBoolean(true);
        Core.checkForCreative = config.get(categoryTorch, "checkForCreative", true, "Do not consume items if you are in Creative Mode").getBoolean(true);

        Core.enableVanilla = config.get(categoryEnable, "enableVanilla", true).getBoolean(true);
        Core.enableEmerald = config.get(categoryEnable, "enableEmerald", true).getBoolean(true);
        Core.enableNetherrack = config.get(categoryEnable, "enableNetherrack", true).getBoolean(true);
        Core.enableObsidian = config.get(categoryEnable, "enableObsidian", true).getBoolean(true);
        Core.enableRedstone = config.get(categoryEnable, "enableRedstone", true).getBoolean(true);
        Core.enableSteel = config.get(categoryEnable, "enableSteel", true).getBoolean(true);
        config.save();
    }

    // Initialize items
    public static void init_items() {
        boolean[] canPlaceTorch = {placeTorch, checkForInfinity, checkForCreative};
        
        if (Core.advancedRecipes) {
            Core.stickCrafter = new ItemStickCrafter("Stick Crafter", Core.textureLocation + "stickCrafter", Core.tabPaxel).setUnlocalizedName("stickCrafter");
        }
        if (Core.enableVanilla) {
            if (Core.advancedRecipes) {
                Core.stickVanilla = new ItemStickPaxel(Core.textureLocation, "stickPaxel", Core.tabPaxel);
                Core.bodyVanilla = new ItemBodyPaxel(Core.textureLocation, "bodyPaxel", Core.tabPaxel);
                Core.headVanilla = new ItemHeadPaxel(Core.textureLocation, "headPaxel", Core.tabPaxel);

                Core.paxelWood = new ItemPaxel(Core.WOOD, "Wooden Paxel", Core.textureLocation + "paxelW_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelStone = new ItemPaxel(Core.STONE, "Stone Paxel", Core.textureLocation + "paxelS_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelIron = new ItemPaxel(Core.IRON, "Iron Paxel", Core.textureLocation + "paxelI_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelDiamond = new ItemPaxel(Core.DIAMOND, "Diamond Paxel", Core.textureLocation + "paxelD_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelGold = new ItemPaxel(Core.GOLD, "Golden Paxel", Core.textureLocation + "paxelG_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            } else {
                Core.paxelWood = new ItemPaxel(Core.WOOD, "Wooden Paxel", Core.textureLocation + "paxelW", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelStone = new ItemPaxel(Core.STONE, "Stone Paxel", Core.textureLocation + "paxelS", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelIron = new ItemPaxel(Core.IRON, "Iron Paxel", Core.textureLocation + "paxelI", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelDiamond = new ItemPaxel(Core.DIAMOND, "Diamond Paxel", Core.textureLocation + "paxelD", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
                Core.paxelGold = new ItemPaxel(Core.GOLD, "Golden Paxel", Core.textureLocation + "paxelG", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            }
        }

        boolean[] enablePaxel = { Core.enableEmerald, Core.enableNetherrack, Core.enableObsidian, Core.enableRedstone, Core.enableSteel };
        Item.ToolMaterial[] xPaxelMaterial = { Core.EMERALD, Core.NETHERRACK, Core.OBSIDIAN, Core.REDSTONE, Core.STEEL };

        int paxelNum = 0;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                Core.catalystEmerald = new ItemCatalystPaxel(Core.textureLocation, Core.materialName[paxelNum], Core.materialLetter[paxelNum], Core.tabPaxel);
                Core.paxelEmerald = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel" + Core.materialLetter[paxelNum]
                        + "_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            } else {
                Core.paxelEmerald = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel" + Core.materialLetter[paxelNum],
                        Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                Core.catalystNetherrack = new ItemCatalystPaxel(Core.textureLocation, Core.materialName[paxelNum], Core.materialLetter[paxelNum], Core.tabPaxel);
                Core.paxelNetherrack = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel"
                        + Core.materialLetter[paxelNum] + "_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            } else {
                Core.paxelNetherrack = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel"
                        + Core.materialLetter[paxelNum], Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                Core.catalystObsidian = new ItemCatalystPaxel(Core.textureLocation, Core.materialName[paxelNum], Core.materialLetter[paxelNum], Core.tabPaxel);
                Core.paxelObsidian = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel" + Core.materialLetter[paxelNum]
                        + "_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            } else {
                Core.paxelObsidian = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel",
                        Core.textureLocation + "paxel" + Core.materialLetter[paxelNum], Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                Core.catalystRedstone = new ItemCatalystPaxel(Core.textureLocation, Core.materialName[paxelNum], Core.materialLetter[paxelNum], Core.tabPaxel);
                Core.paxelRedstone = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel" + Core.materialLetter[paxelNum]
                        + "_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            } else {
                Core.paxelRedstone = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel",
                        Core.textureLocation + "paxel" + Core.materialLetter[paxelNum], Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                Core.catalystSteel = new ItemCatalystPaxel(Core.textureLocation, Core.materialName[paxelNum], Core.materialLetter[paxelNum], Core.tabPaxel);
                Core.paxelSteel = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel" + Core.materialLetter[paxelNum]
                        + "_a", Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            } else {
                Core.paxelSteel = new ItemPaxel(xPaxelMaterial[paxelNum], Core.materialName[paxelNum] + " Paxel", Core.textureLocation + "paxel" + Core.materialLetter[paxelNum],
                        Core.tabPaxel, blockToPlace, itemToConsume, amountToConsume, canPlaceTorch);
            }
        }
    }

    // Add to oreDictionary
    public static void registerOreDictionary() {
        if (Core.advancedRecipes) {
            OreDictionary.registerOre("stickCrafter", Core.stickCrafter);
        }
        if (Core.enableVanilla) {
            OreDictionary.registerOre("paxelWood", Core.paxelWood);
            OreDictionary.registerOre("paxelStone", Core.paxelStone);
            OreDictionary.registerOre("paxelIron", Core.paxelIron);
            OreDictionary.registerOre("paxelDiamond", Core.paxelDiamond);
            OreDictionary.registerOre("paxelGold", Core.paxelGold);
            if (Core.advancedRecipes) {
                OreDictionary.registerOre("stickPaxel_W", new ItemStack(Core.stickVanilla, 1, 0));
                OreDictionary.registerOre("stickPaxel_S", new ItemStack(Core.stickVanilla, 1, 1));
                OreDictionary.registerOre("stickPaxel_I", new ItemStack(Core.stickVanilla, 1, 2));
                OreDictionary.registerOre("stickPaxel_D", new ItemStack(Core.stickVanilla, 1, 3));
                OreDictionary.registerOre("stickPaxel_G", new ItemStack(Core.stickVanilla, 1, 4));

                OreDictionary.registerOre("bodyPaxel_W", new ItemStack(Core.bodyVanilla, 1, 0));
                OreDictionary.registerOre("bodyPaxel_S", new ItemStack(Core.bodyVanilla, 1, 1));
                OreDictionary.registerOre("bodyPaxel_I", new ItemStack(Core.bodyVanilla, 1, 2));
                OreDictionary.registerOre("bodyPaxel_D", new ItemStack(Core.bodyVanilla, 1, 3));
                OreDictionary.registerOre("bodyPaxel_G", new ItemStack(Core.bodyVanilla, 1, 4));

                OreDictionary.registerOre("headPaxel_W", new ItemStack(Core.headVanilla, 1, 0));
                OreDictionary.registerOre("headPaxel_S", new ItemStack(Core.headVanilla, 1, 1));
                OreDictionary.registerOre("headPaxel_I", new ItemStack(Core.headVanilla, 1, 2));
                OreDictionary.registerOre("headPaxel_D", new ItemStack(Core.headVanilla, 1, 3));
                OreDictionary.registerOre("headPaxel_G", new ItemStack(Core.headVanilla, 1, 4));
            }
        }

        boolean[] enablePaxel = { Core.enableEmerald, Core.enableNetherrack, Core.enableObsidian, Core.enableRedstone, Core.enableSteel };
        int paxelNum = 0;
        if (enablePaxel[paxelNum]) {
            OreDictionary.registerOre("paxel" + Core.materialName[paxelNum], Core.paxelEmerald);
            OreDictionary.registerOre("stickPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystEmerald, 1, 0));
            OreDictionary.registerOre("bodyPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystEmerald, 1, 1));
            OreDictionary.registerOre("headPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystEmerald, 1, 2));
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            OreDictionary.registerOre("paxel" + Core.materialName[paxelNum], Core.paxelNetherrack);
            OreDictionary.registerOre("stickPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystNetherrack, 1, 0));
            OreDictionary.registerOre("bodyPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystNetherrack, 1, 1));
            OreDictionary.registerOre("headPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystNetherrack, 1, 2));
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            OreDictionary.registerOre("paxel" + Core.materialName[paxelNum], Core.paxelObsidian);
            OreDictionary.registerOre("stickPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystObsidian, 1, 0));
            OreDictionary.registerOre("bodyPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystObsidian, 1, 1));
            OreDictionary.registerOre("headPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystObsidian, 1, 2));
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            OreDictionary.registerOre("paxel" + Core.materialName[paxelNum], Core.paxelRedstone);
            OreDictionary.registerOre("stickPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystRedstone, 1, 0));
            OreDictionary.registerOre("bodyPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystRedstone, 1, 1));
            OreDictionary.registerOre("headPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystRedstone, 1, 2));
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            OreDictionary.registerOre("paxel" + Core.materialName[paxelNum], Core.paxelSteel);
            OreDictionary.registerOre("stickPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystSteel, 1, 0));
            OreDictionary.registerOre("bodyPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystSteel, 1, 1));
            OreDictionary.registerOre("headPaxel_" + Core.materialName[paxelNum], new ItemStack(Core.catalystSteel, 1, 2));
        }
    }

    // Initialize Crafting
    public static void init_crafting() {
        if (Core.advancedRecipes) {
            GameRegistry.addShapelessRecipe(new ItemStack(Core.stickCrafter), Items.flint, Blocks.cobblestone);
        }
        if (Core.enableVanilla) {
            if (Core.advancedRecipes) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.stickVanilla, 1, 0), Core.stickCrafter, "plankWood", "plankWood"));
                GameRegistry.addShapelessRecipe(new ItemStack(Core.stickVanilla, 1, 1), Core.stickCrafter, Blocks.cobblestone, Blocks.cobblestone);
                GameRegistry.addShapelessRecipe(new ItemStack(Core.stickVanilla, 1, 2), Core.stickCrafter, Items.iron_ingot, Items.iron_ingot);
                GameRegistry.addShapelessRecipe(new ItemStack(Core.stickVanilla, 1, 3), Core.stickCrafter, Items.diamond, Items.diamond);
                GameRegistry.addShapelessRecipe(new ItemStack(Core.stickVanilla, 1, 4), Core.stickCrafter, Items.gold_ingot, Items.gold_ingot);

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.bodyVanilla, 1, 0), "I  ", " S ", "  I", 'S', "stickPaxel_W", 'I', "plankWood"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.bodyVanilla, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_S", 'I', Blocks.cobblestone));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.bodyVanilla, 1, 2), "I  ", " S ", "  I", 'S', "stickPaxel_I", 'I', Items.iron_ingot));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.bodyVanilla, 1, 3), "I  ", " S ", "  I", 'S', "stickPaxel_D", 'I', Items.diamond));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.bodyVanilla, 1, 4), "I  ", " S ", "  I", 'S', "stickPaxel_G", 'I', Items.gold_ingot));

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.headVanilla, 1, 0), " I", "S ", 'S', "stickPaxel_W", 'I', "plankWood"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.headVanilla, 1, 1), " I", "S ", 'S', "stickPaxel_S", 'I', Blocks.cobblestone));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.headVanilla, 1, 2), " I", "S ", 'S', "stickPaxel_I", 'I', Items.iron_ingot));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.headVanilla, 1, 3), " I", "S ", 'S', "stickPaxel_D", 'I', Items.diamond));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.headVanilla, 1, 4), " I", "S ", 'S', "stickPaxel_G", 'I', Items.gold_ingot));

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelWood, 1), "  h", " b ", "s  ", 'h', "headPaxel_W", 'b', "bodyPaxel_W", 's', "stickPaxel_W"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelStone, 1), "  h", " b ", "s  ", 'h', "headPaxel_S", 'b', "bodyPaxel_S", 's', "stickPaxel_S"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelIron, 1), "  h", " b ", "s  ", 'h', "headPaxel_I", 'b', "bodyPaxel_I", 's', "stickPaxel_I"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelDiamond, 1), "  h", " b ", "s  ", 'h', "headPaxel_D", 'b', "bodyPaxel_D", 's', "stickPaxel_D"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelGold, 1), "  h", " b ", "s  ", 'h', "headPaxel_G", 'b', "bodyPaxel_G", 's', "stickPaxel_G"));
            } else {
                GameRegistry.addRecipe(new ItemStack(Core.paxelWood, 1), "PAS", " | ", " | ", Character.valueOf('P'), Items.wooden_pickaxe, Character.valueOf('A'),
                        Items.wooden_axe, Character.valueOf('S'), Items.wooden_shovel, Character.valueOf('|'), Items.stick);
                GameRegistry.addRecipe(new ItemStack(Core.paxelStone, 1), "PAS", " | ", " | ", Character.valueOf('P'), Items.stone_pickaxe, Character.valueOf('A'),
                        Items.stone_axe, Character.valueOf('S'), Items.stone_pickaxe, Character.valueOf('|'), Items.stick);
                GameRegistry.addRecipe(new ItemStack(Core.paxelIron, 1), "PAS", " | ", " | ", Character.valueOf('P'), Items.iron_pickaxe, Character.valueOf('A'), Items.iron_axe,
                        Character.valueOf('S'), Items.iron_shovel, Character.valueOf('|'), Items.stick);
                GameRegistry.addRecipe(new ItemStack(Core.paxelDiamond, 1), "PAS", " | ", " | ", Character.valueOf('P'), Items.diamond_pickaxe, Character.valueOf('A'),
                        Items.diamond_axe, Character.valueOf('S'), Items.diamond_shovel, Character.valueOf('|'), Items.stick);
                GameRegistry.addRecipe(new ItemStack(Core.paxelGold, 1), "PAS", " | ", " | ", Character.valueOf('P'), Items.golden_pickaxe, Character.valueOf('A'),
                        Items.golden_axe, Character.valueOf('S'), Items.golden_shovel, Character.valueOf('|'), Items.stick);
            }
        }
        boolean[] enablePaxel = { Core.enableEmerald, Core.enableNetherrack, Core.enableObsidian, Core.enableRedstone, Core.enableSteel };
        int paxelNum = 0;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                if (Core.useOreDictionary[paxelNum]) {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystEmerald, 1, 0), "stickCrafter", Core.ingot1[paxelNum], Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystEmerald, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystEmerald, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.ingot1[paxelNum]));
                    if (!Core.ingot2.equals("") && !Core.ingot2.equals(Core.ingot1[paxelNum])) {
                        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystEmerald, 1, 0), "stickCrafter", Core.ingot2[paxelNum], Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystEmerald, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_"
                                + Core.materialLetter[paxelNum], 'I', Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystEmerald, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                                Core.ingot2[paxelNum]));
                    }
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelEmerald, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                } else {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystEmerald, 1, 0), "stickCrafter", Core.customCraftingMaterial[paxelNum],
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystEmerald, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystEmerald, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelEmerald, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                }

            } else {
                String pick = "pickaxe" + Core.materialName[paxelNum];
                String axe = "axe" + Core.materialName[paxelNum];
                String spade = "shovel" + Core.materialName[paxelNum];

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelEmerald, 1), "PAS", " | ", " | ", Character.valueOf('P'), pick, Character.valueOf('A'), axe,
                        Character.valueOf('S'), spade, Character.valueOf('|'), Items.stick));
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                if (Core.useOreDictionary[paxelNum]) {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 0), "stickCrafter", Core.ingot1[paxelNum], Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_"
                            + Core.materialLetter[paxelNum], 'I', Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.ingot1[paxelNum]));
                    if (!Core.ingot2.equals("") && !Core.ingot2.equals(Core.ingot1[paxelNum])) {
                        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 0), "stickCrafter", Core.ingot2[paxelNum], Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_"
                                + Core.materialLetter[paxelNum], 'I', Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                                'I', Core.ingot2[paxelNum]));
                    }
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelNetherrack, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                } else {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 0), "stickCrafter", Core.customCraftingMaterial[paxelNum],
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_"
                            + Core.materialLetter[paxelNum], 'I', Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystNetherrack, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelNetherrack, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                }

            } else {
                String pick = "pickaxe" + Core.materialName[paxelNum];
                String axe = "axe" + Core.materialName[paxelNum];
                String spade = "shovel" + Core.materialName[paxelNum];

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelNetherrack, 1), "PAS", " | ", " | ", Character.valueOf('P'), pick, Character.valueOf('A'), axe,
                        Character.valueOf('S'), spade, Character.valueOf('|'), Items.stick));
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                if (Core.useOreDictionary[paxelNum]) {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystObsidian, 1, 0), "stickCrafter", Core.ingot1[paxelNum], Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystObsidian, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystObsidian, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.ingot1[paxelNum]));
                    if (!Core.ingot2.equals("") && !Core.ingot2.equals(Core.ingot1[paxelNum])) {
                        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystObsidian, 1, 0), "stickCrafter", Core.ingot2[paxelNum], Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystObsidian, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_"
                                + Core.materialLetter[paxelNum], 'I', Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystObsidian, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                                Core.ingot2[paxelNum]));
                    }
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelObsidian, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                } else {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystObsidian, 1, 0), "stickCrafter", Core.customCraftingMaterial[paxelNum],
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystObsidian, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystObsidian, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelObsidian, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                }

            } else {
                String pick = "pickaxe" + Core.materialName[paxelNum];
                String axe = "axe" + Core.materialName[paxelNum];
                String spade = "shovel" + Core.materialName[paxelNum];

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelObsidian, 1), "PAS", " | ", " | ", Character.valueOf('P'), pick, Character.valueOf('A'), axe,
                        Character.valueOf('S'), spade, Character.valueOf('|'), Items.stick));
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                if (Core.useOreDictionary[paxelNum]) {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystRedstone, 1, 0), "stickCrafter", Core.ingot1[paxelNum], Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystRedstone, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystRedstone, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.ingot1[paxelNum]));
                    if (!Core.ingot2.equals("") && !Core.ingot2.equals(Core.ingot1[paxelNum])) {
                        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystRedstone, 1, 0), "stickCrafter", Core.ingot2[paxelNum], Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystRedstone, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_"
                                + Core.materialLetter[paxelNum], 'I', Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystRedstone, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                                Core.ingot2[paxelNum]));
                    }
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelRedstone, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                } else {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystRedstone, 1, 0), "stickCrafter", Core.customCraftingMaterial[paxelNum],
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystRedstone, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystRedstone, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelRedstone, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                }

            } else {
                String pick = "pickaxe" + Core.materialName[paxelNum];
                String axe = "axe" + Core.materialName[paxelNum];
                String spade = "shovel" + Core.materialName[paxelNum];

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelRedstone, 1), "PAS", " | ", " | ", Character.valueOf('P'), pick, Character.valueOf('A'), axe,
                        Character.valueOf('S'), spade, Character.valueOf('|'), Items.stick));
            }
        }
        paxelNum++;
        if (enablePaxel[paxelNum]) {
            if (Core.advancedRecipes) {
                if (Core.useOreDictionary[paxelNum]) {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystSteel, 1, 0), "stickCrafter", Core.ingot1[paxelNum], Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystSteel, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.ingot1[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystSteel, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.ingot1[paxelNum]));
                    if (!Core.ingot2.equals("") && !Core.ingot2.equals(Core.ingot1[paxelNum])) {
                        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystSteel, 1, 0), "stickCrafter", Core.ingot2[paxelNum], Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystSteel, 1, 1), "I  ", " S ", "  I", 'S',
                                "stickPaxel_" + Core.materialLetter[paxelNum], 'I', Core.ingot2[paxelNum]));
                        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystSteel, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                                Core.ingot2[paxelNum]));
                    }
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelSteel, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                } else {
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Core.catalystSteel, 1, 0), "stickCrafter", Core.customCraftingMaterial[paxelNum],
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystSteel, 1, 1), "I  ", " S ", "  I", 'S', "stickPaxel_" + Core.materialLetter[paxelNum],
                            'I', Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.catalystSteel, 1, 2), " I", "S ", 'S', "stickPaxel_" + Core.materialLetter[paxelNum], 'I',
                            Core.customCraftingMaterial[paxelNum]));
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelSteel, 1), "  h", " b ", "s  ", 'h', "headPaxel_" + Core.materialLetter[paxelNum], 'b',
                            "bodyPaxel_" + Core.materialLetter[paxelNum], 's', "stickPaxel_" + Core.materialLetter[paxelNum]));
                }

            } else {
                String pick = "pickaxe" + Core.materialName[paxelNum];
                String axe = "axe" + Core.materialName[paxelNum];
                String spade = "shovel" + Core.materialName[paxelNum];

                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Core.paxelSteel, 1), "PAS", " | ", " | ", Character.valueOf('P'), pick, Character.valueOf('A'), axe,
                        Character.valueOf('S'), spade, Character.valueOf('|'), Items.stick));
            }
        }
    }

    public static Property set(Configuration config, String category, String key, boolean defaultValue) {
        return Core.set(config, category, key, defaultValue, null);
    }

    public static Property set(Configuration config, String category, String key, boolean defaultValue, String comment) {
        Property prop = config.get(category, key, Boolean.toString(defaultValue), comment, BOOLEAN);
        prop.set(defaultValue);
        return prop;
    }
}
