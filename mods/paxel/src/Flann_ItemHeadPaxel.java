// Coded by Flann

package mods.paxel.src;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class Flann_ItemHeadPaxel extends Item {
	
	public Icon tw;//Wood
	public Icon ts;//Stone
	public Icon ti;//Iron
	public Icon td;//Diamond
	public Icon tg;//Gold
	public String tex = "headPaxel";
	public String texLoc;
	
	public Flann_ItemHeadPaxel(int par1, String texLocation) {
		super(par1);
		setHasSubtypes(true);
		setMaxDamage(0);
		texLoc = texLocation;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(texLoc + tex + "_1");
		this.tw = IR.registerIcon(texLoc + tex + "_1");
		this.ts = IR.registerIcon(texLoc + tex + "_2");
		this.ti = IR.registerIcon(texLoc + tex + "_3");
		this.td = IR.registerIcon(texLoc + tex + "_4");
		this.tg = IR.registerIcon(texLoc + tex + "_5");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i){
		switch(i){
		case 0:return tw;
		case 1:return ts;
		case 2:return ti;
		case 3:return td;
		case 4:return tg;
		default:return itemIcon;
		}
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		switch(is.getItemDamage()){
		case 0:return "Wooden Paxelhead";
		case 1:return "Stone Paxelhead";
		case 2:return "Iron Paxelhead";
		case 3:return "Diamond Paxelhead";
		case 4:return "Golden Paxelhead";
		default:return "itemUnknown";
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tab,List itemList){
		for(int i=0;i<5;i++){
			itemList.add(new ItemStack(itemID,1,i));
		}
	}
	
}
