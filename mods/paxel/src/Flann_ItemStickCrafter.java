// Coded by Flann

package mods.paxel.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Flann_ItemStickCrafter extends Item{
	
	private String tex, name;
	
	public Flann_ItemStickCrafter(int par1, String displayName, String texture) {
		super(par1);
		setMaxStackSize(1);
		setContainerItem(this);
		tex=texture;
		name = displayName;
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		return name;
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(tex);
	}
}
