package abyss.LuaLanguageLoader

import abyss.LuaCore
import abyss.eventHandlers.GlobalEvents
import abyss.luaObjects.LuaAPI
import net.neoforged.bus.api.IEventBus
import party.iroiro.luajava.lua55.Lua55

// Handles the Lua interpreter and helper scripts
internal object LuaModsLoader {

    internal val lua = Lua55()

    init {
        lua.openLibraries()
        lua.set("Events", GlobalEvents)
        loadLuaEnvHelper()
    }

    internal fun loadLuaEnvHelper() {
        val script = LuaCore::class.java
            .getResourceAsStream("/abyss/LuaFmlHelpers/EnvironmentManager.lua")
            ?.bufferedReader()
            ?.use { it.readText() }
            ?: error("Failed to load Lua Environment Manager script")

        lua.run(script)
    }

    internal fun injectLuaMod(modID: String, modBus: IEventBus, source: String) {
        // Register a Lua mod source with its own environment
        System.out.println("[luafml] injecting $modID.")

        lua.get("LoadMod").call(modID, modBus, source, LuaAPI(modID, modBus))

        System.out.println("[luafml] mod $modID injected successfully.")
    }
}