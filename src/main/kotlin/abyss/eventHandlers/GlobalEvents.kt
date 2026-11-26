package abyss.eventHandlers

import abyss.LuaAPI
import net.neoforged.fml.common.EventBusSubscriber
import abyss.Luaforneoforge.ID
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent

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