// Coded by Flann

package mods.paxel.src;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class Flann_FuelHandler implements IFuelHandler{
	
	@Override
	public int getBurnTime(ItemStack fuel) { //This method sets burn time
		if(fuel.itemID == CorePaxel.paxelW.itemID){
			return 300;
		}
		if(fuel.itemID == CorePaxel.stickV.itemID && fuel.getItemDamage() == 0){
			return 300;
		}
		if(fuel.itemID == CorePaxel.bodyV.itemID && fuel.getItemDamage() == 0){
			return 300;
		}
		if(fuel.itemID == CorePaxel.headV.itemID && fuel.getItemDamage() == 0){
			return 300;
		}
		return 0;
	}
}