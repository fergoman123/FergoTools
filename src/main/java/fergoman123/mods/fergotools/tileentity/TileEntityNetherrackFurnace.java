package fergoman123.mods.fergotools.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fergoman123.mods.fergotools.block.furnace.BlockNetherrackFurnace;
import fergoman123.mods.fergotools.reference.Locale;
import fergoman123.mods.fergotools.reference.ints.FurnaceInts;
import fergoman123.mods.fergotools.util.base.TileEntityFurnaceFT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityNetherrackFurnace extends TileEntityFurnaceFT
{

    @Override
    public int getSizeInventory(){return this.slots.length;}

    public ItemStack getStackInSlot(int slot){return this.slots[slot];}

    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.slots[par1] != null)
        {
            ItemStack stack;

            if (this.slots[par1].stackSize <= par2)
            {
                stack = this.slots[par1];
                this.slots[par1] = null;
                return stack;
            }
            else
            {
                stack = this.slots[par1].splitStack(par2);

                if (this.slots[par1].stackSize == 0)
                {
                    this.slots[par1] = null;
                }

                return stack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.slots[slot] != null)
        {
            ItemStack stack = this.slots[slot];
            this.slots[slot] = null;
            return stack;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.slots[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.localizedName : Locale.containerNetherrackFurnace;
    }

    public boolean hasCustomInventoryName()
    {
        return this.localizedName != null && this.localizedName.length() > 0;
    }

    public void setGuiDisplayName(String displayName)
    {
        this.localizedName = displayName;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList list = compound.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound compound1 = list.getCompoundTagAt(i);
            byte b0 = compound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length)
            {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(compound1);
            }
        }

        this.burnTime = compound.getShort("BurnTime");
        this.cookTime = compound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.slots[1]);

        if (compound.hasKey("CustomName", 8))
        {
            this.localizedName = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setShort("BurnTime", (short)this.burnTime);
        compound.setShort("CookTime", (short)this.cookTime);
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < this.slots.length; ++i)
        {
            if (this.slots[i] != null)
            {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Slot", (byte)i);
                this.slots[i].writeToNBT(compound1);
                list.appendTag(compound1);
            }
        }

        compound.setTag("Items", list);

        if (this.hasCustomInventoryName())
        {
            compound.setString("CustomName", this.localizedName);
        }
    }

    public int getInventoryStackLimit()
    {
        return inventoryStackLimit;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int speed)
    {
        return this.cookTime * speed / FurnaceInts.netherrackFurnaceSpeed;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int speed)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = FurnaceInts.netherrackFurnaceSpeed;
        }

        return this.burnTime * speed / this.currentItemBurnTime;
    }

    public boolean isBurning()
    {
            return this.worldObj.getBlock(this.xCoord, this.xCoord - 1, this.zCoord) == Blocks.fire || this.burnTime > 0;
    }

    public void updateEntity()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.burnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.burnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[1]);

                if (this.isBurning())
                {
                    flag1 = true;

                    if (this.slots[1] != null)
                    {
                        --this.slots[1].stackSize;

                        if (this.slots[1].stackSize == 0)
                        {
                            this.slots[1] = slots[1].getItem().getContainerItem(slots[1]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt())
            {
                ++this.cookTime;

                if (this.cookTime == FurnaceInts.netherrackFurnaceSpeed)
                {
                    this.cookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                this.cookTime = 0;
            }

            if (flag != this.isBurning())
            {
                flag1 = true;
                BlockNetherrackFurnace.updateBlockState(this.isBurning(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    public boolean canSmelt()
    {
        if (this.slots[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
            if (stack == null)return false;
            if (this.slots[2] == null)return true;
            if (!this.slots[2].isItemEqual(stack))return false;
            int result = slots[2].stackSize + stack.stackSize;
            return (result <= getInventoryStackLimit() && result <= this.slots[2].getMaxStackSize());
        }
    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);

            if (this.slots[2] == null)
            {
                this.slots[2] = stack.copy();
            }
            else if (this.slots[2].getItem() == stack.getItem())
            {
                this.slots[2].stackSize += stack.stackSize;
            }

            --this.slots[0].stackSize;

            if (this.slots[0].stackSize <= 0)
            {
                this.slots[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack stack)
    {
        if (stack == null)
        {
            return 0;
        }
        else
        {
            Item item = stack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != null)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab)
                {
                    return 150;
                }

                if (block.getMaterial() == Material.wood)
                {
                    return 300;
                }

                if (block == Blocks.coal_block)
                {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD"))return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD"))return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD"))return 200;
            if (item == Items.stick)return 100;
            if (item == Items.coal)return 1600;
            if (item == Items.lava_bucket)return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling))return 100;
            if (item == Items.blaze_rod)return 2400;
            return GameRegistry.getFuelValue(stack);
        }
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory(){}

    public void closeInventory(){}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 2 ? false : (slot == 1 ? isItemFuel(stack) : true);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {
        return slot == 0 ? slotsBottom : (slot == 1 ? slotsTop : slotsSides);
    }

    @Override
    public boolean canInsertItem(int par1, ItemStack stack, int par3) {
        return this.isItemValidForSlot(par1, stack);
    }

    @Override
    public boolean canExtractItem(int par1, ItemStack stack, int par3) {
        return par3 != 0 || par1 != 1 || stack.getItem() == Items.bucket;
    }
}