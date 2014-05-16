package fergoman123.mods.fergotools.block.furnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fergoman123.mods.fergotools.FergoTools;
import fergoman123.mods.fergotools.init.ModBlocks;
import fergoman123.mods.fergotools.lib.Reference;
import fergoman123.mods.fergotools.lib.Textures.BlockTextures;
import fergoman123.mods.fergotools.lib.Textures.FurnaceTextures;
import fergoman123.mods.fergotools.tileentity.TileEntityRedstoneFurnace;
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

/**
 * Created by Fergoman123 on 22/04/2014.
 */
public class BlockRedstoneFurnace extends BlockContainer{

    private final Random rand = new Random();
    private final boolean isActive;
    private static boolean keepInventory;

    private TileEntityRedstoneFurnace furnace = new TileEntityRedstoneFurnace();

    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    public BlockRedstoneFurnace(boolean isActive)
    {
        super(Material.iron);
        this.isActive = isActive;
    }

    public Item getItemDropped(int par1, Random rand, int par3)
    {
        return UtilBlockItem.itemRedstoneFurnace;
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        setDefaultDirection(world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        if (par2 == 0)
            par2 = 3;
        return par1 == 1 ? this.iconTop : (par1 == 0 ? this.iconTop :(par1 != par2 ? this.blockIcon : this.iconFront));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = registerIcon(BlockTextures.blockRedstone);
        this.iconFront = registerIcon(Reference.textureLoc + (this.isActive ? FurnaceTextures.redstoneFurnaceActive : FurnaceTextures.redstoneFurnaceIdle));
        this.iconTop = registerIcon(BlockTextures.blockRedstone);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
       if (isRemote())
       {
           return true;
       }
       else if (playerIsNotSneaking())
       {
           TileEntityRedstoneFurnace furnaceTile = (TileEntityRedstoneFurnace)getTileEntity(x, y, z);
           if (furnaceTile != null)
           {
               openGui(FergoTools.instance, 9, world, x, y, z);
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
        int l = world.getBlockMetadata(x, y, z);
        TileEntity entity = world.getTileEntity(x, y, z);
        keepInventory = true;

        if (active)
        {
            setBlock(x, y, z, ModBlocks.redstoneFurnaceActive);
        }
        else
        {
            setBlock(x, y, z, ModBlocks.redstoneFurnaceIdle);
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
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        if (this.isActive)
        {
            int l = getBlockMetadata(x, y, z);
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;

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
        return this.furnace;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack stack)
    {
        int l = floorDouble((double)(elb.rotationYaw * 4.0f / 360.0f) + 0.5D) & 3;

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

        if (stack.hasDisplayName())
        {
            ((TileEntityRedstoneFurnace)getTileEntity(x, y, z)).setGuiDisplayName(stack.getDisplayName());
        }
    }

    public void breakBlock(World par1World, int x, int y, int z, Block block, int oldBlock)
    {
        if (!keepInventory)
        {
            TileEntityRedstoneFurnace tileentityfurnace = (TileEntityRedstoneFurnace)getTileEntity(x, y, z);

            if (tileentityfurnace != null)
            {
                for (int j1 = 0; j1 < tileentityfurnace.getSizeInventory(); ++j1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(j1);

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
                            EntityItem entityitem = new EntityItem(par1World, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));

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

                par1World.func_147453_f(x, y, z, block);
            }
        }

        super.breakBlock(par1World, x, y, z, block, oldBlock);
    }

    public boolean hasComparatorInputOverride(){return true;}

    public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
    {
        return calcRedstoneFromInventory((IInventory)getTileEntity(x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return UtilBlockItem.itemRedstoneFurnace;
    }
}
