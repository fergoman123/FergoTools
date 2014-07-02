package fergoman123.mods.fergotools.util.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fergoman123.mods.fergotools.lib.Reference;
import fergoman123.mods.fergotools.tabs.Tabs;
import fergoman123.mods.fergoutil.helper.NameHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public abstract class BlockFT extends Block{

    public BlockFT()
    {
        super(Material.rock);
        this.setCreativeTab(Tabs.tabFergoBlocks);
        this.setHardness(2.5f);
        this.setResistance(50f);
    }

    public BlockFT(Material material)
    {
        super(material);
        this.setCreativeTab(Tabs.tabFergoBlocks);
        this.setHardness(2.5f);
        this.setResistance(50f);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.textureLoc, NameHelper.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(String.format("%s", NameHelper.getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }
}