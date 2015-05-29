/*
 * Fergoman123's Tools
 * Copyright (c) 2014 fergoman123.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl-3.0.html
 */

package io.github.fergoman123.fergotools.util.tool;


import io.github.fergoman123.fergotools.FergoTools;
import io.github.fergoman123.fergoutil.info.ToolInfo;
import io.github.fergoman123.fergoutil.item.ItemShearsFergo;
import net.minecraft.item.Item;

public class ItemShearsFT extends ItemShearsFergo {
    public ItemShearsFT(ToolMaterial material, Item repairItem, ToolInfo info) {
        super(0, FergoTools.tabFergoTools, info);
    }
}
