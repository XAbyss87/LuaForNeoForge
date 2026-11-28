package abyss.luaObjects

import abyss.LuaCore
import abyss.registers.RegistrationHandler
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

    fun item(name: String): DeferredItem<LuaItem>?{
        return registrationHandler.registerItem(name)
    }

    fun block(name: String, blockItem: Boolean = false): DeferredBlock<LuaBlock>? {
        return registrationHandler.registerBlock(name, blockItem)
    }
}