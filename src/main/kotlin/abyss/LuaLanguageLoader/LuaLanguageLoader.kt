package abyss.LuaLanguageLoader

import net.neoforged.fml.ModContainer
import net.neoforged.neoforgespi.language.IModInfo
import net.neoforged.neoforgespi.language.IModLanguageLoader
import net.neoforged.neoforgespi.language.ModFileScanData

class LuaLanguageLoader : IModLanguageLoader {

    init {
        System.out.println("[luafml] Lua mods loader instantiated.")
    }

    override fun name(): String {
        return "luafml"
    }

    override fun version(): String {
        return "0.1"
    }

    override fun loadMod(
        info: IModInfo,
        modFileScanResults: ModFileScanData?,
        layer: ModuleLayer?):
            ModContainer {
        return LuaModContainer(info)
    }
}