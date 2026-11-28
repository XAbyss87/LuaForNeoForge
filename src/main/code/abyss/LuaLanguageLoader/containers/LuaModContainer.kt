package abyss.LuaLanguageLoader.containers

import abyss.LuaCore
import abyss.LuaLanguageLoader.LuaLanguageLoader
import abyss.LuaLanguageLoader.LuaModsLoader.injectLuaMod
import abyss.luaObjects.LuaAPI
import net.neoforged.bus.api.BusBuilder
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent
import net.neoforged.neoforgespi.language.IModInfo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.file.Files
import kotlin.io.path.extension
import kotlin.io.path.isRegularFile
import kotlin.io.path.readText
import kotlin.streams.asSequence

// Each mods ModContainer
// Handles setting up the mods Eventbus, Logger, ect
// Passes mod data to LuaInterpreter.kt for injection
class LuaModContainer(info: IModInfo) : ModContainer(info) {

    val logger: Logger = LogManager.getLogger()

    private val eventBus: IEventBus = BusBuilder.builder()
        .allowPerPhasePost()
        .build()

    private val API = LuaAPI(modId, eventBus)

    init {
        logger.info("[luafml] loading mod: $modId")
    }

    override fun getEventBus(): IEventBus {
        return eventBus
    }

    private fun HookNeoForgeEventBus(){

        // Subscribe to NeoForges event bus to act on its 'FMLConstructModEvent'
        val neoForgeBus = ModList.get()
            .getModContainerById("neoforge")
            .orElse(null)
            ?.eventBus

        if (neoForgeBus != null &&
            LuaLanguageLoader.SubscribedToNeoForgeBus.compareAndSet(false, true)
        ) {
            LuaCore.luaDebug("Hooking into FMLConstructModEvent")
            neoForgeBus.addListener(
                FMLConstructModEvent::class.java
            ) { event ->
                event.enqueueWork {
                    LuaCore.initialize()
                }
            }
        }
    }

    // Read all Lua files, grant each mod a local environment inside the Lua interpreter, and inject the mod.
    override fun constructMod() {
        try {
            val pathName = "Lua"

            val modFile = modInfo.owningFile.file
            val scriptsDir = modFile.findResource(pathName)

            if (Files.notExists(scriptsDir)) {
                logger.warn("Lua mod '{}' has no $pathName/ directory.", modId)
                return
            }

            // Get all Lua files
            val luaFiles = Files.walk(scriptsDir).use { stream ->
                stream.asSequence()
                    .filter { it.isRegularFile() && it.extension.equals("lua", ignoreCase = false) }
                    .sortedBy { scriptsDir.relativize(it).toString() }
                    .toList()
            }

            // Finally load the files
            luaFiles.forEach { injectLuaMod(modId, eventBus, it.readText(), API) }

            HookNeoForgeEventBus()

        } catch (error: Error) {
            logger.error("Error loading Lua mod '{}': {}", modId, error.message)
        }
    }
}