package fergoman123.mods.fergotools.item.bow;

import cpw.mods.fml.relauncher.SideOnly;
import fergoman123.mods.fergotools.lib.Reference;
import fergoman123.mods.fergotools.lib.strings.BowStrings;
import fergoman123.mods.fergotools.lib.textures.BowTextures;
import fergoman123.mods.fergotools.util.base.ItemBowFT;
import fergoman123.mods.fergotools.util.item.UtilToolArmor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import static cpw.mods.fml.relauncher.Side.CLIENT;

public class ItemBronzeBow extends ItemBowFT {

    public static ItemBronzeBow instance = new ItemBronzeBow();

    public static final String[] pullArray = new String[]{"_1", "_2", "_3"};

    @SideOnly(CLIENT)
    private IIcon[] texture;
    public ItemBronzeBow()
    {
        super();
        this.setUnlocalizedName(BowStrings.bowBronze);
        this.setMaxDamage(UtilToolArmor.getBronzeMaxUses());
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4)
    {
        int j = this.getMaxItemUseDuration(stack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow))
        {
            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(world, player, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ArrowNockEvent event = new ArrowNockEvent(player, stack);
        MinecraftForge.EVENT_BUS.post(event);
        if(!event.isCancelable())
        {
            return event.result;
        }

        if(player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow))
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }
        return stack;
    }

    public int getEnchantability()
    {
        return 1;
    }

    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(BowTextures.bronzeBowStandy);
        this.texture = new IIcon[pullArray.length];

        for (int i = 0; i < pullArray.length; i++)
        {
            this.texture[i] = register.registerIcon(Reference.textureLoc + BowStrings.bowBronze + pullArray[i]);
        }
    }

    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if(player.getItemInUse() == null) return this.itemIcon;
        int pulling = stack.getMaxItemUseDuration() - useRemaining;
        if(pulling >= 18)
        {
            return texture[2];
        }
        else if(pulling > 13)
        {
            return texture[1];
        }
        else if(pulling > 0)
        {
            return texture[0];
        }
        return this.itemIcon;
    }

}
