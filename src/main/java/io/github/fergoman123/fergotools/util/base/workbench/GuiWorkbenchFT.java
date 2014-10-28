package io.github.fergoman123.fergotools.util.base.workbench;

import io.github.fergoman123.fergotools.reference.names.Locale;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiWorkbenchFT extends GuiContainer
{

    public String localeName = Locale.containerInventory;
    public String localeInventory;

    public Object[] obj = new Object[0];

    public GuiWorkbenchFT(Container p_i1072_1_) {
        super(p_i1072_1_);
    }

    public abstract void drawGuiContainerBackgroundLayer(float par1, int par2, int par3);

    public abstract void drawGuiContainerForegroundLayer(int par1, int par2);
}