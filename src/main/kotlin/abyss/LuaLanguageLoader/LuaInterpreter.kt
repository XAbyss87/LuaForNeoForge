package abyss.LuaLanguageLoader

import abyss.LuaAPI
import abyss.Luaforneoforge
import abyss.eventHandlers.GlobalEvents
import com.ibm.icu.impl.ValidIdentifiers
import net.neoforged.fml.ISystemReportExtender
import party.iroiro.luajava.lua55.Lua55
import java.nio.file.Files
import java.nio.file.Path
import kotlin.jvm.java

internal object LuaModsLoader {

    internal val lua = Lua55()

    init {
        lua.openLibraries()
        lua.set("API", LuaAPI)
        lua.set("Events", GlobalEvents)
        loadLuaEnvHelper()
    }

    internal fun loadLuaEnvHelper() {
        val script = Luaforneoforge::class.java
            .getResourceAsStream("/abyss/LuaFmlHelpers/EnvironmentManager.lua")
            ?.bufferedReader()
            ?.use { it.readText() }
            ?: error("Failed to load Lua Environment Manager script")

        lua.run(script)
    }

    internal fun injectLuaMod(modID: String, source: String) {
        // Register a Lua mod source with its own environment
        System.out.println("[luafml] injecting $modID.")
        lua.get("LoadMod").call(modID, source)
        System.out.println("[luafml] mod $modID injected successfully.")
    }
}

internal object LuaHelperSource {
    private const val RESOURCE_PATH = "abyss/LuaFmlHelpers/EnvironmentManager.lua"

    @JvmStatic
    fun read(developmentHelpers: Path?): String = if (developmentHelpers != null) {
        Files.readString(developmentHelpers.resolve("EnvironmentManager.lua"))
    } else {
        LuaHelperSource::class.java.getResourceAsStream("/$RESOURCE_PATH")
            ?.bufferedReader()
            ?.use { it.readText() }
            ?: error("Lua environment helper resource not found: $RESOURCE_PATH")
    }
}