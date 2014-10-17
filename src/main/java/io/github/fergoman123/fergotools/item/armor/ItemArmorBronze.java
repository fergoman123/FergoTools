package io.github.fergoman123.fergotools.item.armor;

import io.github.fergoman123.fergotools.init.ModItems;
import io.github.fergoman123.fergotools.reference.Textures;
import io.github.fergoman123.fergotools.reference.names.ArmorTooltipLocale;
import io.github.fergoman123.fergotools.reference.names.Locale;
import io.github.fergoman123.fergotools.util.base.ItemArmorFT;
import io.github.fergoman123.fergotools.util.item.Materials;
import io.github.fergoman123.fergoutil.helper.NameHelper;
import io.github.fergoman123.fergoutil.item.ArmorType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemArmorBronze extends ItemArmorFT {

    public ItemArmorBronze(ArmorType type)
    {
        super(ArmorNames.bronze, Materials.Armor.bronzeArmor, type);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if(stack.getItem() == ModItems.bronzeHelmet || stack.getItem() == ModItems.bronzeChestplate || stack.getItem() == ModItems.bronzeBoots)
        {
            return Textures.bronzeArmorLayer1;
        }

        if(stack.getItem() == ModItems.bronzeLeggings)
        {
            return Textures.bronzeArmorLayer2;
        }
        else
        {
            return null;
        }
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean extraInfo)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        {
            list.add(NameHelper.translateToLocal(ArmorTooltipLocale.reductAmount));
            for (int i = 0; i < ArmorTooltipLocale.bronzeArmor.length; i++)
            {
                list.add(NameHelper.translateToLocal(ArmorTooltipLocale.bronzeArmor[i]));
            }
        }
        else
        {
            list.add(NameHelper.translateToLocal(Locale.holdShiftMessage));
        }
    }

}
