package fergoman123.mods.fergotools;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fergoman123.mods.fergotools.config.ConfigHandler;
import fergoman123.mods.fergotools.handler.RegHandler;
import fergoman123.mods.fergotools.helper.LogHelper;
import fergoman123.mods.fergotools.init.ModBlocks;
import fergoman123.mods.fergotools.init.ModItems;
import fergoman123.mods.fergotools.init.ModTiles;
import fergoman123.mods.fergotools.init.Recipes;
import fergoman123.mods.fergotools.lib.MetadataFT;
import fergoman123.mods.fergotools.lib.ModInfo;
import fergoman123.mods.fergotools.lib.Reference;
import fergoman123.mods.fergotools.packet.PacketPipeline;
import fergoman123.mods.fergotools.tabs.Tabs;
import fergoman123.mods.fergotools.util.item.UtilToolArmor;
import fergoman123.mods.fergoutil.proxy.IProxy;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.versionMain, dependencies = ModInfo.dep, guiFactory = Reference.guiFactoryClass)
public class FergoTools{

    public static final PacketPipeline packPipe = new PacketPipeline();
	
    @Instance(ModInfo.modid)
    public static FergoTools instance;
    
    @SidedProxy(clientSide = Reference.clientProxyClass, serverSide = Reference.serverProxyClass)
    public static IProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {
        LogHelper.info("Pre Initialising Mod");
        MetadataFT.writeMetadata(evt.getModMetadata());
        ConfigHandler.init(evt.getSuggestedConfigurationFile());
        Tabs.init();
        UtilToolArmor.init();
        ModItems.init();
        ModBlocks.init();
    }

	@EventHandler
    public void load(FMLInitializationEvent evt)
    {
        LogHelper.info("Initialising Registration and Packet Handler");
        ModTiles.init();
        RegHandler.init();
        Recipes.init();
        packPipe.initialise();
    }
	
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt)
    {
        LogHelper.info("Mod Loaded");
        packPipe.postInitialise();
    }
}
