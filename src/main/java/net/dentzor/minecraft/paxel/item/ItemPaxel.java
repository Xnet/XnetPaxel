package net.dentzor.minecraft.paxel.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

import com.google.common.collect.Sets;

public class ItemPaxel extends ItemTool {

    String                          local_name;

    Block                           cfg_blockToPlace;
    Item                            cfg_itemToConsume;
    int                             cfg_amountToConsume;
    boolean                         cfg_canPlaceBlock;
    boolean                         cfg_checkForInfinity;
    boolean                         cfg_checkForCreative;

    /*
     * Following: Block to place Metadata Item to remove Metadata Amount to
     * remove Flags to check for Infinity Creative
     */

    private static final Set<Block> field_150916_c = Sets.newHashSet(new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow,
            Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium, Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone,
            Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block,
            Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail,
            Blocks.golden_rail, Blocks.activator_rail, Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin });

    public ItemPaxel(ToolMaterial material, String displayName, String texture, CreativeTabs creativetab, String blockToPlace, String itemToConsume, int amountToConsume,
            boolean[] canPlaceBlock) {
        this(material, displayName, texture, creativetab, (Block.getBlockFromName(blockToPlace) == null ? Blocks.emerald_block : Block.getBlockFromName(blockToPlace)),
                (getItemFromName(itemToConsume) == null ? Item.getItemFromBlock(Blocks.cobblestone) : getItemFromName(itemToConsume)), amountToConsume, canPlaceBlock);
    }

    public ItemPaxel(ToolMaterial material, String displayName, String texture, CreativeTabs creativetab, Block blockToPlace, Item itemToConsume, int amountToConsume,
            boolean[] canPlaceBlock) {
        super(1.0F, material, ItemPaxel.field_150916_c);
        setTextureName(texture);
        setCreativeTab(creativetab);

        local_name = displayName;

        cfg_blockToPlace = blockToPlace;
        cfg_itemToConsume = itemToConsume;
        cfg_amountToConsume = amountToConsume;
        cfg_canPlaceBlock = canPlaceBlock[0];
        cfg_checkForInfinity = canPlaceBlock[1];
        cfg_checkForCreative = canPlaceBlock[2];
    }

    @Override
    public boolean func_150897_b(Block p_150897_1_) {
        if (p_150897_1_ == Blocks.snow_layer)
            return true;
        else if (p_150897_1_ == Blocks.snow)
            return true;
        else
            return p_150897_1_ == Blocks.obsidian ? toolMaterial.getHarvestLevel() == 3
                    : ((p_150897_1_ != Blocks.diamond_block) && (p_150897_1_ != Blocks.diamond_ore) ? ((p_150897_1_ != Blocks.emerald_ore) && (p_150897_1_ != Blocks.emerald_block) ? ((p_150897_1_ != Blocks.gold_block)
                            && (p_150897_1_ != Blocks.gold_ore) ? ((p_150897_1_ != Blocks.iron_block) && (p_150897_1_ != Blocks.iron_ore) ? ((p_150897_1_ != Blocks.lapis_block)
                            && (p_150897_1_ != Blocks.lapis_ore) ? ((p_150897_1_ != Blocks.redstone_ore) && (p_150897_1_ != Blocks.lit_redstone_ore) ? (p_150897_1_.getMaterial() == Material.rock ? true
                            : (p_150897_1_.getMaterial() == Material.iron ? true : p_150897_1_.getMaterial() == Material.anvil))
                            : toolMaterial.getHarvestLevel() >= 2)
                            : toolMaterial.getHarvestLevel() >= 1)
                            : toolMaterial.getHarvestLevel() >= 1)
                            : toolMaterial.getHarvestLevel() >= 2)
                            : toolMaterial.getHarvestLevel() >= 2)
                            : toolMaterial.getHarvestLevel() >= 2);
    }

    @Override
    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_) {
        return (p_150893_2_.getMaterial() != Material.iron) && (p_150893_2_.getMaterial() != Material.anvil) && (p_150893_2_.getMaterial() != Material.rock)
                && (p_150893_2_.getMaterial() != Material.wood) && (p_150893_2_.getMaterial() != Material.plants) && (p_150893_2_.getMaterial() != Material.vine) ? func_150893_abc(
                p_150893_1_, p_150893_2_) : efficiencyOnProperMaterial;
    }

    public float func_150893_abc(ItemStack p_150893_1_, Block p_150893_2_) {
        return ItemPaxel.field_150916_c.contains(p_150893_2_) ? efficiencyOnProperMaterial : super.func_150893_a(p_150893_1_, p_150893_2_);
    }

    // Display Name
    @Override
    public String getItemStackDisplayName(ItemStack is) {
        return local_name;
    }

    // Place block
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {

        Item ItemToConsume = cfg_itemToConsume;
        boolean flag = (par2EntityPlayer.capabilities.isCreativeMode && cfg_checkForCreative)
                || ((EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0) && cfg_checkForInfinity);
        boolean canPlace = flag || par2EntityPlayer.inventory.hasItem(ItemToConsume) || cfg_amountToConsume == 0;

        int itemsRemoved = 0;
        boolean pass = false;
        for (int i = 0; i < cfg_amountToConsume; i++) {
            if (par2EntityPlayer.inventory.consumeInventoryItem(ItemToConsume)) {
                itemsRemoved++;
            } else {
                break;
            }
        }
        if (itemsRemoved == cfg_amountToConsume) {
            pass = true;
        }
        par2EntityPlayer.inventory.addItemStackToInventory(new ItemStack(cfg_itemToConsume, itemsRemoved));

        if (cfg_canPlaceBlock && canPlace && pass) {
            Block block = par3World.getBlock(par4, par5, par6);

            if ((block == Blocks.snow_layer) && ((par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)) {
                par7 = 1;
            } else if ((block != Blocks.vine) && (block != Blocks.tallgrass) && (block != Blocks.deadbush) && !block.isReplaceable(par3World, par4, par5, par6)) {
                if (par7 == 0) {
                    --par5;
                }

                if (par7 == 1) {
                    ++par5;
                }

                if (par7 == 2) {
                    --par6;
                }

                if (par7 == 3) {
                    ++par6;
                }

                if (par7 == 4) {
                    --par4;
                }

                if (par7 == 5) {
                    ++par4;
                }
            }

            if (par1ItemStack.stackSize == 0)
                return false;
            else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
                return false;
            else if ((par5 == 255) && cfg_blockToPlace.getMaterial().isSolid())
                return false;
            else if (par3World.canPlaceEntityOnSide(cfg_blockToPlace, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {
                int i1 = getMetadata(par1ItemStack.getItemDamage());
                int j1 = cfg_blockToPlace.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, i1);

                if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, j1)) {
                    par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, cfg_blockToPlace.stepSound.func_150496_b(),
                            (cfg_blockToPlace.stepSound.getVolume() + 1.0F) / 2.0F, cfg_blockToPlace.stepSound.getPitch() * 0.8F);
                    if (!flag) {
                        for (int i = 0; i < cfg_amountToConsume; i++) {
                            par2EntityPlayer.inventory.consumeInventoryItem(ItemToConsume);
                        }
                    }
                }

                return true;
            } else
                return false;
        } else
            return false;
    }

    // Which side to place block at
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {

        if (!world.setBlock(x, y, z, cfg_blockToPlace, metadata, 3))
            return false;

        if (world.getBlock(x, y, z) == cfg_blockToPlace) {
            cfg_blockToPlace.onBlockPlacedBy(world, x, y, z, player, stack);
            cfg_blockToPlace.onPostBlockPlaced(world, x, y, z, metadata);
        }

        return true;
    }

    public static Item getItemFromName(String itemName) {
        if (Item.itemRegistry.containsKey(itemName)) {
            return (Item) itemRegistry.getObject(itemName);
        } else {
            try {
                return (Item) itemRegistry.getObjectById(Integer.parseInt(itemName));
            } catch (NumberFormatException numberformatexception) {
                return null;
            }
        }
    }

}
