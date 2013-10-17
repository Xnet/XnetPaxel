// Coded by Flann

package mods.paxel.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public class Flann_ItemPaxel extends ItemPickaxe {
    
	public String tex, name;
	public ItemStack ccm;
	public boolean hasDam;
	public int torchID = Block.torchWood.blockID;
	public boolean config_canPlaceTorch;
	
	public Flann_ItemPaxel(int i, boolean canPlaceTorch, String displayName, String texture, ItemStack customCraftingMaterial, boolean hasDamage, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
		tex = texture;
		ccm = customCraftingMaterial;
		hasDam = hasDamage;
		name = displayName;
		config_canPlaceTorch = canPlaceTorch;
	}
	
	public Flann_ItemPaxel(int i, boolean canPlaceTorch, String displayName, String t, Item ccm, EnumToolMaterial enumtoolmaterial) {
		this(i, canPlaceTorch, displayName, t, new ItemStack(ccm), false, enumtoolmaterial);
	}
	
	public Flann_ItemPaxel(int i, boolean canPlaceTorch, String displayName, String t, Block ccm, EnumToolMaterial enumtoolmaterial) {
		this(i, canPlaceTorch, displayName, t, new ItemStack(ccm), false, enumtoolmaterial);
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		return name;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(tex);
	}
	
	public boolean canHarvestBlock(Block par1Block)
    {
		if (par1Block == Block.snow || par1Block == Block.blockSnow)
			return par1Block == Block.snow ? true : par1Block == Block.blockSnow;
		else
			return super.canHarvestBlock(par1Block);
    }
	
	@Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
    	if(par2Block != null && (par2Block.blockMaterial == Material.wood || par2Block.blockMaterial == Material.vine || par2Block.blockMaterial == Material.plants))
    		return this.efficiencyOnProperMaterial;
    	else if(par2Block != null && (par2Block == Block.grass || par2Block == Block.dirt || par2Block == Block.sand || par2Block == Block.gravel || par2Block == Block.snow || par2Block == Block.blockSnow || par2Block == Block.blockClay || par2Block == Block.tilledField || par2Block == Block.slowSand || par2Block == Block.mycelium))
    		return this.efficiencyOnProperMaterial;
    	else if(par2Block != null && (par2Block == Block.planks || par2Block == Block.bookShelf || par2Block == Block.wood || par2Block == Block.chest || par2Block == Block.stoneDoubleSlab || par2Block == Block.stoneSingleSlab || par2Block == Block.pumpkin || par2Block == Block.pumpkinLantern))
    		return this.efficiencyOnProperMaterial;
    	else
    		return super.getStrVsBlock(par1ItemStack, par2Block);
    }
	
	@Override
	public boolean getIsRepairable(ItemStack i, ItemStack j){
		if(hasDam)
			return (i.itemID == itemID && j.itemID == ccm.itemID && j.getItemDamage() == ccm.getItemDamage());
		return (i.itemID == itemID && j.itemID == ccm.itemID);
	}
	
	/**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	int ItemToConsume = Block.torchWood.blockID;
    	boolean flag = par2EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
    	boolean hasTorchInInventory = flag || par2EntityPlayer.inventory.hasItem(ItemToConsume);
    	
    	if(config_canPlaceTorch && hasTorchInInventory)
    	{
	        int i1 = par3World.getBlockId(par4, par5, par6);
	
	        if (i1 == Block.snow.blockID && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)
	        {
	            par7 = 1;
	        }
	        else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID
	                && (Block.blocksList[i1] == null || !Block.blocksList[i1].isBlockReplaceable(par3World, par4, par5, par6)))
	        {
	            if (par7 == 0)
	            {
	                --par5;
	            }
	
	            if (par7 == 1)
	            {
	                ++par5;
	            }
	
	            if (par7 == 2)
	            {
	                --par6;
	            }
	
	            if (par7 == 3)
	            {
	                ++par6;
	            }
	
	            if (par7 == 4)
	            {
	                --par4;
	            }
	
	            if (par7 == 5)
	            {
	                ++par4;
	            }
	        }
	
	        if (par1ItemStack.stackSize == 0)
	        {
	            return false;
	        }
	        else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
	        {
	            return false;
	        }
	        else if (par5 == 255 && Block.blocksList[this.torchID].blockMaterial.isSolid())
	        {
	            return false;
	        }
	        else if (par3World.canPlaceEntityOnSide(this.torchID, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack))
	        {
	            Block block = Block.blocksList[this.torchID];
	            int j1 = this.getMetadata(par1ItemStack.getItemDamage());
	            int k1 = Block.blocksList[this.torchID].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, j1);
	
	            if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, k1))
	            {
	                par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
	                if (!flag)
	                {
	                    par2EntityPlayer.inventory.consumeInventoryItem(ItemToConsume);
	                }
	            }
	
	            return true;
	        }
	        else
	        {
	            return false;
	        }
    	}else{
    		return false;
    	}
    }
    
    /**
     * Called to actually place the block, after the location is determined
     * and all permission checks have been made.
     *
     * @param stack The item stack that was used to place the block. This can be changed inside the method.
     * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
     * @param side The side the player (or machine) right-clicked on.
     */
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
       if (!world.setBlock(x, y, z, this.torchID, metadata, 3))
       {
           return false;
       }

       if (world.getBlockId(x, y, z) == this.torchID)
       {
           Block.blocksList[this.torchID].onBlockPlacedBy(world, x, y, z, player, stack);
           Block.blocksList[this.torchID].onPostBlockPlaced(world, x, y, z, metadata);
       }

       return true;
    }
	
}
