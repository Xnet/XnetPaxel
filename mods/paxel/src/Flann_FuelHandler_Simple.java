// Coded by Flann

package mods.paxel.src;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class Flann_FuelHandler_Simple implements IFuelHandler{
	
	@Override
	public int getBurnTime(ItemStack fuel) { //This method sets burn time
		if(fuel.itemID == CorePaxel.paxelW.itemID){
			return 300;
		}
		return 0;
	}
}