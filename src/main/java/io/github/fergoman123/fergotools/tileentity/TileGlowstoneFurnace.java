package io.github.fergoman123.fergotools.tileentity;

import io.github.fergoman123.fergotools.block.BlockGlowstoneFurnace;
import io.github.fergoman123.fergotools.reference.Assets.Locale;
import io.github.fergoman123.fergotools.reference.ints.FurnaceInts;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class TileGlowstoneFurnace extends TileEntityFurnaceFT
{
    @Override
    public String getCommandSenderName() {
        return Locale.containerGlowstoneFurnace;
    }
    
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	NBTTagList list = compound.getTagList("Items", 10);
    	this.slots = new ItemStack[this.getSizeInventory()];
    	
    	for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = list.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length)
            {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.burnTime = compound.getShort("BurnTime");
        this.cookTime = compound.getShort("CookTime");
        this.totalCookTime = compound.getShort("CookTimeTotal");
        this.currentItemBurnTime = getBurnTime(this.slots[1]);

        if (compound.hasKey("CustomName", 8))
        {
            this.inventoryName = compound.getString("CustomName");
        }
    }

    @Override
    public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.burnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (!this.isBurning() && (this.slots[1] == null || this.slots[0] == null))
            {
                if (!this.isBurning() && this.cookTime > 0)
                {
                    this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
                }
            }
            else
            {
                if (!this.isBurning() && this.canSmelt())
                {
                    this.currentItemBurnTime = this.burnTime = getBurnTime(this.slots[1]);

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

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.slots[0]);
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }

            if (flag != this.isBurning())
            {
                flag1 = true;
                BlockGlowstoneFurnace.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    @Override
    public int getCookTime(ItemStack stack) {
        return FurnaceInts.glowstoneFurnaceSpeed;
    }

    @Override
    public boolean canSmelt() {
        if (this.slots[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(this.slots[0]);
            if (stack == null)return false;
            if (this.slots[2] == null)return true;
            if (!this.slots[2].isItemEqual(stack))return false;
            int result = slots[2].stackSize + stack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.slots[2].getMaxStackSize();
        }
    }

    @Override
    public void smeltItem() {
        if (this.canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.slots[0]);

            if (this.slots[2] == null)
            {
                this.slots[2] = itemstack.copy();
            }
            else if (this.slots[2].getItem() == itemstack.getItem())
            {
                this.slots[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            if (this.slots[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.slots[0].getMetadata() == 1 && this.slots[1] != null && this.slots[1].getItem() == Items.bucket)
            {
                this.slots[1] = new ItemStack(Items.water_bucket);
            }

            --this.slots[0].stackSize;

            if (this.slots[0].stackSize <= 0)
            {
                this.slots[0] = null;
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index != 2 && (index != 1 || isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack));
    }

    @Override
    public String getGuiID() {
        return "fergotools:glowstoneFurnace";
    }
    
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.water_bucket && item != Items.bucket)
            {
                return false;
            }
        }

        return true;
    }
    
    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
    	return this.isItemValidForSlot(index, stack);
    }

    @Override
    public Container createContainer(InventoryPlayer invPlayer, EntityPlayer player) {
//        return new ContainerGlowstoneFurnace(invPlayer, this);
    	return null; //TODO: fix this
    }
}