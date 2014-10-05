 /*
  * Fergoman123's Tools
  * Copyright (c) 2014 fergoman123.
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the GNU Lesser Public License v2.1
  * which accompanies this distribution, and is available at
  * http://www.gnu.org/licenses/gpl-3.0.html
  */

package fergoman123.mods.fergotools.util.item;

import fergoman123.mods.fergotools.config.ConfigHandler;
import fergoman123.mods.fergotools.reference.Ints;
import fergoman123.mods.fergotools.reference.Strings;
import fergoman123.mods.fergoutil.helper.ToolHelper;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class Materials {

    public static void init() {
        Tools.initMaterials();
        Armor.initMaterials();
    }

    public static final class Tools
    {
        public static ToolMaterial quartz;
        public static ToolMaterial obsidian;
        public static ToolMaterial emerald;
        public static ToolMaterial lapis;
        public static ToolMaterial bronze;
        public static ToolMaterial coal;
        public static ToolMaterial glowstone;
        public static ToolMaterial adamantium;
        public static ToolMaterial donator;
        public static ToolMaterial silk;
        public static ToolMaterial redstone;

        public static final ToolMaterial[] materials = new ToolMaterial[]{quartz, obsidian, emerald, lapis, bronze, coal, glowstone, adamantium, donator, silk, redstone};

        public static void initMaterials()
        {
            quartz = EnumHelper.addToolMaterial(Strings.Material.quartzToolMat, Ints.Tools.harvestLvl, Ints.Tools.quartzMaxDamage, Ints.Tools.quartzEff, Ints.Tools.quartzDamage, ConfigHandler.enchantability);
            obsidian = EnumHelper.addToolMaterial(Strings.Material.obsidianToolMat, Ints.Tools.harvestLvl, Ints.Tools.obsidianMaxDamage, Ints.Tools.obsidianEff, Ints.Tools.obsidianDamage, ConfigHandler.enchantability);
            emerald = EnumHelper.addToolMaterial(Strings.Material.emeraldToolMat, Ints.Tools.harvestLvl, Ints.Tools.emeraldMaxDamage, Ints.Tools.emeraldEff, Ints.Tools.emeraldDamage, ConfigHandler.enchantability);
            lapis = EnumHelper.addToolMaterial(Strings.Material.lapisToolMat, Ints.Tools.harvestLvl, Ints.Tools.lapisMaxDamage, Ints.Tools.lapisEff, Ints.Tools.lapisDamage, ConfigHandler.enchantability);
            bronze = EnumHelper.addToolMaterial(Strings.Material.bronzeToolMat, Ints.Tools.harvestLvl, Ints.Tools.bronzeMaxDamage, Ints.Tools.bronzeEff, Ints.Tools.bronzeDamage, ConfigHandler.enchantability);
            coal = EnumHelper.addToolMaterial(Strings.Material.coalToolMat, Ints.Tools.harvestLvl, Ints.Tools.coalMaxDamage, Ints.Tools.coalEff, Ints.Tools.coalDamage, ConfigHandler.enchantability);
            glowstone = EnumHelper.addToolMaterial(Strings.Material.glowstoneToolMat, Ints.Tools.harvestLvl, Ints.Tools.glowstoneMaxDamage, Ints.Tools.glowstoneEff, Ints.Tools.glowstoneDamage, ConfigHandler.enchantability);
            adamantium = EnumHelper.addToolMaterial(Strings.Material.adamantiumToolMat, Ints.Tools.harvestLvl, Ints.Tools.adamantiumMaxDamage, Ints.Tools.adamantiumEff, Ints.Tools.adamantiumDamage, ConfigHandler.enchantability);
            donator = EnumHelper.addToolMaterial(Strings.Material.donatorToolMat, Ints.Tools.harvestLvl, Ints.Tools.donatorMaxDamage, Ints.Tools.donatorEff, Ints.Tools.donatorDamage, ConfigHandler.enchantability);
            silk = EnumHelper.addToolMaterial(Strings.Material.silkToolMat, Ints.Tools.harvestLvl, Ints.Tools.silkMaxDamage, Ints.Tools.silkEff, Ints.Tools.silkDamage, ConfigHandler.enchantability);
            redstone = EnumHelper.addToolMaterial(Strings.Material.redstoneToolMat, Ints.Tools.harvestLvl, Ints.Tools.redstoneMaxDamage, Ints.Tools.redstoneEff, Ints.Tools.redstoneDamage, ConfigHandler.enchantability);
        }
    }

    public static final class Armor
    {
        public static ArmorMaterial quartzArmor;
        public static ArmorMaterial obsidianArmor;
        public static ArmorMaterial emeraldArmor;
        public static ArmorMaterial lapisArmor;
        public static ArmorMaterial bronzeArmor;
        public static ArmorMaterial coalArmor;
        public static ArmorMaterial glowstoneArmor;
        public static ArmorMaterial adamantiumArmor;
        public static ArmorMaterial redstoneArmor;

        public static void initMaterials()
        {
            quartzArmor = EnumHelper.addArmorMaterial(Strings.Material.quartzArmor, Ints.Armor.quartzArmorMaxDamage, Ints.Armor.quartzReduct, ConfigHandler.enchantability);
            obsidianArmor = EnumHelper.addArmorMaterial(Strings.Material.obsidianArmor, Ints.Armor.obsidianArmorMaxDamage, Ints.Armor.obsidianReduct, ConfigHandler.enchantability);
            emeraldArmor = EnumHelper.addArmorMaterial(Strings.Material.emeraldArmor, Ints.Armor.emeraldArmorMaxDamage, Ints.Armor.emeraldReduct, ConfigHandler.enchantability);
            lapisArmor = EnumHelper.addArmorMaterial(Strings.Material.lapisArmor, Ints.Armor.lapisArmorMaxDamage, Ints.Armor.lapisReduct, ConfigHandler.enchantability);
            bronzeArmor = EnumHelper.addArmorMaterial(Strings.Material.bronzeArmor, Ints.Armor.bronzeArmorMaxDamage, Ints.Armor.bronzeReduct, ConfigHandler.enchantability);
            coalArmor = EnumHelper.addArmorMaterial(Strings.Material.coalArmor, Ints.Armor.coalArmorMaxDamage, Ints.Armor.coalReduct, ConfigHandler.enchantability);
            glowstoneArmor = EnumHelper.addArmorMaterial(Strings.Material.glowstoneArmor, Ints.Armor.glowstoneArmorMaxDamage, Ints.Armor.glowstoneReduct, ConfigHandler.enchantability);
            adamantiumArmor = EnumHelper.addArmorMaterial(Strings.Material.adamantiumArmor, Ints.Armor.adamantiumArmorMaxDamage, Ints.Armor.adamantiumReduct, ConfigHandler.enchantability);
            redstoneArmor = EnumHelper.addArmorMaterial(Strings.Material.redstoneArmor, Ints.Armor.redstoneArmorMaxDamage, Ints.Armor.redstoneReduct, ConfigHandler.enchantability);

        }
    }
}