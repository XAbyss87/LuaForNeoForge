package abyss.LuaLanguageLoader

import abyss.LuaLanguageLoader.containers.LuaModContainer
import net.neoforged.fml.ModContainer
import net.neoforged.neoforgespi.language.IModInfo
import net.neoforged.neoforgespi.language.IModLanguageLoader
import net.neoforged.neoforgespi.language.ModFileScanData
import java.util.concurrent.atomic.AtomicBoolean

// Starting point for Lua mods using NeoForges IModLanguageLoader interface
class LuaLanguageLoader : IModLanguageLoader {

    companion object {
        val SubscribedToNeoForgeBus = AtomicBoolean(false)
    }

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
        layer: ModuleLayer?
    ): ModContainer {

        val ownModule = LuaLanguageLoader::class.java.module

        val gameLayer = requireNotNull(layer)

        val neoforgeModule = gameLayer
            .findModule("neoforge")
            .orElseThrow()

        val minecraftModule = gameLayer
            .findModule("minecraft")
            .orElseThrow()

        // Establish runtime module readability between the PLUGIN and GAME layers.
        // Required for LuaForNeoForge to access Minecraft and NeoForge classes.
        ownModule.addReads(neoforgeModule)
        ownModule.addReads(minecraftModule)

        println("[luafml] NeoForge readable: ${ownModule.canRead(neoforgeModule)}")
        println("[luafml] Minecraft readable: ${ownModule.canRead(minecraftModule)}")

        return LuaModContainer(info)
    }
}