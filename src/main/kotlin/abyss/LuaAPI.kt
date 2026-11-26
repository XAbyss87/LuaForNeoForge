package abyss

import abyss.luaObjects.LuaBlock
import abyss.luaObjects.LuaItem
import abyss.registers.RegistrationHandler
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem

object LuaAPI {
    fun log(text: String){
        Luaforneoforge.LOGGER.info(text)
    }
    fun warning(text: String){
        Luaforneoforge.LOGGER.warn(text)
    }

    fun item(name: String): DeferredItem<LuaItem>?{
        return RegistrationHandler.registerItem(name)
    }

    fun block(name: String, blockItem: Boolean = false): DeferredBlock<LuaBlock>? {
        return RegistrationHandler.registerBlock(name, blockItem)
    }

    fun sendToPlayer(player: Player, text: String) {
        (player as? ServerPlayer)?.sendSystemMessage(Component.literal(text))
    }
}