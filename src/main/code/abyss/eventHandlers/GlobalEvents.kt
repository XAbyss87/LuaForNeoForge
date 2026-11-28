package abyss.eventHandlers

import abyss.LuaCore.ID
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.player.PlayerEvent

// Shared events for all mods
// Custom event implementation for mods will eventually go here - maybe
@EventBusSubscriber(modid = ID)
object GlobalEvents : EventRegistry() {
    // Custom events
    fun onStart(){
        callEvent("onStart")
    }

    // NeoForge events
    @SubscribeEvent
    fun onPlayerLoggedIn(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity
        callEvent("onPlayerJoin", player)
    }
}