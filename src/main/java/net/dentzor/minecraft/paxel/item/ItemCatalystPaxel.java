package net.dentzor.minecraft.paxel.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCatalystPaxel extends Item {

    public IIcon i1, i2, i3;
    public String materialName, materialLetter, texLoc;

    public ItemCatalystPaxel(String textureLocation, String materialFullName, String materialFirstLetter, CreativeTabs creativetab) {
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(creativetab);
        materialName = materialFullName;
        materialLetter = materialFirstLetter;
        texLoc = textureLocation;
    }

    @Override
    public String getItemStackDisplayName(ItemStack is) {
        switch (is.getItemDamage()) {
            case 0:
                return materialName + " Paxelstick";
            case 1:
                return materialName + " Paxelbody";
            case 2:
                return materialName + " Paxelhead";
            default:
                return "itemUnknown";
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    // Makes sure that only the client side can call this method
    public void registerIcons(IIconRegister IR) {
        this.itemIcon = IR.registerIcon(texLoc+"stickPaxel"+materialLetter);
        this.i1 = IR.registerIcon(texLoc+"stickPaxel"+materialLetter);
        this.i2 = IR.registerIcon(texLoc+"bodyPaxel"+materialLetter);
        this.i3 = IR.registerIcon(texLoc+"headPaxel"+materialLetter);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        switch (i) {
            case 0:return i1;
            case 1:return i2;
            case 2:return i3;
            default:return itemIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
        for (int i = 0; i < 3; i++) {
            itemList.add(new ItemStack(item, 1, i));
        }
    }
}
