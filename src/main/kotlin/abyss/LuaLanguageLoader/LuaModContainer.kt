package abyss.LuaLanguageLoader

import abyss.LuaAPI
import abyss.LuaLanguageLoader.LuaModsLoader.injectLuaMod
import abyss.LuaLanguageLoader.LuaModsLoader.loadLuaEnvHelper
import abyss.LuaLanguageLoader.LuaModsLoader.lua
import abyss.eventHandlers.GlobalEvents
import net.neoforged.bus.api.BusBuilder
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.neoforgespi.language.IModInfo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.file.Files
import kotlin.io.path.extension
import kotlin.io.path.isRegularFile
import kotlin.io.path.readText
import kotlin.streams.asSequence

class LuaModContainer(info: IModInfo, ) : ModContainer(info) {

    val logger: Logger = LogManager.getLogger()

    init {
        logger.info("[luafml] loading mod: $modId")
    }

    private val eventBus: IEventBus = BusBuilder.builder()
        .allowPerPhasePost()
        .build()

    override fun getEventBus(): IEventBus {
        return eventBus
    }

    // Read all Lua files, grant each mod a local environment inside the Lua interpreter, and inject the mod.
    override fun constructMod() {
        try {
            val pathName = "Lua"

            val modFile = modInfo.owningFile.file
            val scriptsDir = modFile.findResource(pathName)

            if (Files.notExists(scriptsDir)){
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
            luaFiles.forEach { injectLuaMod(modId, it.readText()) }

        }
        catch (error: Error) {
            TODO("Implement Error Handling")
        }
    }
}