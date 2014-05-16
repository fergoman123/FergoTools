package fergoman123.mods.fergotools.block.furnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fergoman123.mods.fergotools.FergoTools;
import fergoman123.mods.fergotools.init.ModBlocks;
import fergoman123.mods.fergotools.lib.Reference;
import fergoman123.mods.fergotools.lib.Textures.BlockTextures;
import fergoman123.mods.fergotools.lib.Textures.FurnaceTextures;
import fergoman123.mods.fergotools.tileentity.TileEntityAdamantiumFurnace;
import fergoman123.mods.fergotools.util.UtilBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

import static fergoman123.mods.fergoutil.helper.FurnaceHelper.*;

public class BlockAdamantiumFurnace extends BlockContainer
{
    private final Random rand = new Random();

    private final boolean isActive;

    private static boolean keepInventory;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    public  BlockAdamantiumFurnace(boolean isActive)
    {
        super(Material.rock);
        this.isActive = isActive;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return UtilBlockItem.itemAdamantiumFurnace;
    }

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        setDefaultDirection(par1World, par2, par3, par4);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
            if(meta == 0)
                    meta = 3;
            
        return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : (side != meta ? this.blockIcon : this.iconFront));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon(BlockTextures.blockAdamantium);
        this.iconFront = register.registerIcon(Reference.textureLoc + (this.isActive ? FurnaceTextures.adamantiumFurnaceActive : FurnaceTextures.adamantiumFurnaceIdle));
        this.iconTop = register.registerIcon(BlockTextures.blockAdamantium);
    }
   
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
        {
        	return true;
        }
        else if(playerIsNotSneaking())
        {
        	TileEntityAdamantiumFurnace var10 = (TileEntityAdamantiumFurnace) getTileEntity(x, y, z);
        	if(var10 != null)
        	{
                openGui(FergoTools.instance, 7, world, x, y, z);
        	}
        	return true;
        }
        else
        {
        	return false;
        }
    }

    public static void updateFurnaceBlockState(boolean active, World world, int x, int y, int z)
    {
        int l = getBlockMetadata(x, y, z);
        TileEntity entity = getTileEntity(x, y, z);
        keepInventory = true;

        if (active)
        {
            setBlock(x, y, z, ModBlocks.adamantiumFurnaceActive);
        }
        else
        {
            setBlock(x, y, z, ModBlocks.adamantiumFurnaceIdle);
        }

        keepInventory = false;
        setBlockMetadataWithNotify(x, y, z, l, 2);

        if (entity != null)
        {
            validateTileEntity();
            setTileEntity(x, y, z, entity);
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if (this.isActive)
        {
            int l = getBlockMetadata(x, y, z);
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;

            if (l == 4)
            {
                spawnParticle("smoke", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                spawnParticle("flame", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 5)
            {
                spawnParticle("smoke", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                spawnParticle("flame", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 2)
            {
                spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
                spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 3)
            {
                spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
                spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public TileEntity createNewTileEntity(World world, int var)
    {
        return new TileEntityAdamantiumFurnace();
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack is)
    {
        int l = floorDouble((double) (elb.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1)
        {
            setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2)
        {
            setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3)
        {
            setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if (hasDisplayName())
        {
            ((TileEntityAdamantiumFurnace)getTileEntity(x, y, z)).setGuiDisplayName(is.getDisplayName());
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int oldBlock)
    {
        if (!keepInventory)
        {
            TileEntityAdamantiumFurnace furnace = (TileEntityAdamantiumFurnace)getTileEntity(x, y, z);

            if (furnace != null)
            {
                for (int j1 = 0; j1 < furnace.getSizeInventory(); ++j1)
                {
                    ItemStack itemstack = furnace.getStackInSlot(j1);

                    if (itemstack != null)
                    {
                        float f = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int k1 = this.rand.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize)
                            {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                            spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_147453_f(x, y, z, block);
            }
        }

        super.breakBlock(world, x, y, z, block, oldBlock);
    }
    
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
    {
        return calcRedstoneFromInventory((IInventory) getTileEntity(x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return UtilBlockItem.itemAdamantiumFurnace;
    }
}