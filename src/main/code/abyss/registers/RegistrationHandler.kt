package abyss.registers

import abyss.LuaCore
import abyss.luaObjects.LuaBlock
import abyss.luaObjects.LuaItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

// Wrapper around DeferredRegisters
// Connects to LuaCore allowing mods to register items on the same event call 'onStart'
class RegistrationHandler(modID: String, val modBus: IEventBus) {

    init {
        LuaCore.registries.add(this)
    }

    private val items: DeferredRegister.Items = DeferredRegister.createItems(modID)
    private val blocks: DeferredRegister.Blocks = DeferredRegister.createBlocks(modID)

    private val registeredItems = mutableMapOf<String, DeferredItem<LuaItem>>()
    private val registeredBlocks = mutableMapOf<String, DeferredBlock<LuaBlock>>()

    fun registerAll(){
         items.register(modBus)
         blocks.register(modBus)
    }

    fun registerItem(name: String): DeferredItem<LuaItem>? {
        registeredItems[name] = items.registerItem(
            name,
            ::LuaItem,
            Item.Properties()
            )

        return registeredItems[name]
    }
    fun registerBlock(name: String, blockitem: Boolean = false): DeferredBlock<LuaBlock>? {
        val block = blocks.registerBlock(
            name,
            ::LuaBlock,
            BlockBehaviour.Properties.of()
            )

        registeredBlocks[name] = block

        if (blockitem)
            items.registerSimpleBlockItem(block)

        return registeredBlocks[name]
    }
}