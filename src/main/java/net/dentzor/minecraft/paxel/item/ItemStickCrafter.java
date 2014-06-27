package net.dentzor.minecraft.paxel.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStickCrafter extends Item {

    String local_name;

    public ItemStickCrafter(String displayName, String texture, CreativeTabs creativetab) {
        setCreativeTab(creativetab);
        setTextureName(texture);
        setMaxStackSize(1);
        setContainerItem(this);
        local_name = displayName;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack) {
        return false;
    }

    @Override
    public String getItemStackDisplayName(ItemStack is) {
        return local_name;
    }
}
