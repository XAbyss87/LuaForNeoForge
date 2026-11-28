package abyss.luaObjects

import abyss.LuaCore
import abyss.registers.RegistrationHandler
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem

// Acts as a middle-man for connecting Lua mods to the NeoForge API
class LuaAPI(modID: String, modBus: IEventBus) {

    val registrationHandler = RegistrationHandler(modID, modBus)

    fun log(text: String){
        LuaCore.LOGGER.info(text)
    }
    fun warning(text: String){
        LuaCore.LOGGER.warn(text)
    }

    fun item(name: String): DeferredItem<LuaItem>? {
        return registrationHandler.registerItem(name)
    }

    fun block(name: String, blockItem: Boolean = false): DeferredBlock<LuaBlock>? {
        return registrationHandler.registerBlock(name, blockItem)
    }

    fun ClientMessage(text: String, player: Player){
        player.displayClientMessage(
            Component.literal(text),
            false
        )
    }

    fun getItem(item: DeferredItem<LuaItem>): LuaItem {
        return item.get()
    }

    fun getBlock(block: DeferredBlock<LuaBlock>): LuaBlock {
        return block.get()
    }
}