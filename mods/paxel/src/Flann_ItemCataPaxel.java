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

public class Flann_ItemCataPaxel extends Item {
	
	public Icon i1, i2, i3;
	public String t1, t2, t3,
		mat;
	
	public Flann_ItemCataPaxel(int id, String materialName, String tex1, String tex2, String tex3) {
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
		t1 = tex1;
		t2 = tex2;
		t3 = tex3;
		mat = materialName;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(t1);
		this.i1 = IR.registerIcon(t1);
		this.i2 = IR.registerIcon(t2);
		this.i3 = IR.registerIcon(t3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i){
		switch(i){
			case 0:return i1;
			case 1:return i2;
			case 2:return i3;
			default:return itemIcon;
		}
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		switch(is.getItemDamage()){
			case 0:return mat+" Paxelstick";
			case 1:return mat+" Paxelbody";
			case 2:return mat+" Paxelhead";
			default:return "itemUnknown";
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tab,List itemList){
		for(int i=0;i<3;i++){
			itemList.add(new ItemStack(itemID,1,i));
		}
	}
}
