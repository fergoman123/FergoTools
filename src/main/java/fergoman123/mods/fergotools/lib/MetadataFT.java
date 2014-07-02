package fergoman123.mods.fergotools.lib;

import cpw.mods.fml.common.ModMetadata;

public class MetadataFT
{

    public static void writeMetadata(ModMetadata metadata)
    {
        metadata.autogenerated = false;
        metadata.modId = ModInfo.modid;
        metadata.name = ModInfo.name;
        metadata.version = ModInfo.versionMain;
        metadata.description = ModInfo.description;
        metadata.authorList = ModInfo.authorList;
        metadata.logoFile = ModInfo.logoFile;
        metadata.url = ModInfo.url;
        metadata.updateUrl = ModInfo.updateUrl;
    }
}
