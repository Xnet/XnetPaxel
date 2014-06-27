package net.dentzor.minecraft.paxel.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStickPaxel extends Item {

    public IIcon tw; // Wood
    public IIcon ts; // Stone
    public IIcon ti; // Iron
    public IIcon td; // Diamond
    public IIcon tg; // Gold
    public String texLoc, tex;

    public ItemStickPaxel(String textureLocation, String textureName, CreativeTabs creativetab) {
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(creativetab);
        texLoc = textureLocation;
        tex = textureName;
    }

    @Override
    public String getItemStackDisplayName(ItemStack is) {
        switch (is.getItemDamage()) {
            case 0:
                return "Wooden Paxelstick";
            case 1:
                return "Stone Paxelstick";
            case 2:
                return "Iron Paxelstick";
            case 3:
                return "Diamond Paxelstick";
            case 4:
                return "Gold PaxelStick";
            default:
                return "itemUnknown";
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    // Makes sure that only the client side can call this method
    public void registerIcons(IIconRegister IR) {
        itemIcon = IR.registerIcon(texLoc + tex + "_1");
        tw = IR.registerIcon(texLoc + tex + "_1");
        ts = IR.registerIcon(texLoc + tex + "_2");
        ti = IR.registerIcon(texLoc + tex + "_3");
        td = IR.registerIcon(texLoc + tex + "_4");
        tg = IR.registerIcon(texLoc + tex + "_5");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        switch (i) {
            case 0:
                return tw;
            case 1:
                return ts;
            case 2:
                return ti;
            case 3:
                return td;
            case 4:
                return tg;
            default:
                return itemIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
        for (int i = 0; i < 5; i++) {
            itemList.add(new ItemStack(item, 1, i));
        }
    }
}
