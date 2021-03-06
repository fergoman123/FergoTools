 /*
   * Fergoman123's Tools
   * Copyright (c) 2014 fergoman123.
   * All rights reserved. This program and the accompanying materials
   * are made available under the terms of the GNU Lesser Public License v2.1
   * which accompanies this distribution, and is available at
   * http://www.gnu.org/licenses/gpl-3.0.html
   */

package io.github.fergoman123.fergotools.crafting;

 import net.minecraft.block.Block;
 import net.minecraft.init.Blocks;
 import net.minecraft.init.Items;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;

 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;

 public class MaceratorRecipes
{
    private static final MaceratorRecipes instance = new MaceratorRecipes();
    private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();

    public static MaceratorRecipes macerating()
    {
        return instance;
    }

    public void addSmeltingRecipeForBlock(Block input, ItemStack output, float experience)
    {
        this.addSmelting(Item.getItemFromBlock(input), output, experience);
    }

    public void addSmelting(Item input, ItemStack output, float experience)
    {
        this.addSmeltingRecipe(new ItemStack(input, 1, 32767), output, experience);
    }

    public void addSmeltingRecipe(ItemStack input, ItemStack output, float experience)
    {
        this.smeltingList.put(input, output);
        this.experienceList.put(output, Float.valueOf(experience));
    }

    public ItemStack getSmeltingResult(ItemStack stack)
    {
        Iterator iterator = this.smeltingList.entrySet().iterator();
        Map.Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entry = (Map.Entry)iterator.next();
        }
        while (!this.compareItemStacks(stack, (ItemStack)entry.getKey()));

        return (ItemStack)entry.getValue();
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }

    public Map getSmeltingList()
    {
        return this.smeltingList;
    }

    public float func_151398_b(ItemStack stack)
    {
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1)return ret;

        Iterator iterator = this.experienceList.entrySet().iterator();
        Map.Entry entry;

        do {
            if (!iterator.hasNext())
            {
                return 0.0F;
            }

            entry = (Map.Entry)iterator.next();
        }
        while (!this.compareItemStacks(stack, (ItemStack) entry.getKey()));

        return ((Float)entry.getValue()).floatValue();
    }
}
