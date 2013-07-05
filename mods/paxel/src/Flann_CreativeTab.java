// Coded by Flann

package mods.paxel.src;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Flann_CreativeTab extends CreativeTabs{

	public ItemStack img;
	
	public Flann_CreativeTab(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
	    return new ItemStack(CorePaxel.paxelW);
	}
}
