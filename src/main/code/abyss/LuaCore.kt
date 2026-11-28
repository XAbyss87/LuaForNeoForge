package abyss

import abyss.eventHandlers.GlobalEvents
import abyss.registers.RegistrationHandler
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.concurrent.CopyOnWriteArrayList

// Acts as a shared endpoint for mods to communicate with
// Handles mod startup after mod creation
object LuaCore {
    const val ID = "luaforneoforge"
    val DEBUG = true

    val LOGGER: Logger = LogManager.getLogger(ID)
    // Simple but helpful
    fun luaDebug(text: String){
        if (DEBUG) println("[luafml - DEBUG]$text")
    }

    // Each RegistrationHandler is responsible for registering its respective mod elements and passing its instance
    // to the registries list so that it can be registered at the appropriate time.
    val registries = CopyOnWriteArrayList<RegistrationHandler>()

    // Called once all Lua mods load
    // Registers objects and hands off control to the Events system
    fun initialize() {
        luaDebug("LuaCore initialized")
        luaDebug("All Lua mods loaded")
        GlobalEvents.onRegister()

        registries.forEach { it.registerAll() }
        registries.firstOrNull()?.modBus?.addListener(
            ::onCommonSetup
        )
    }

    fun onCommonSetup(event: FMLCommonSetupEvent) {
        event.enqueueWork {
            GlobalEvents.onStart()
        }
    }
}