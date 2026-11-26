package abyss.registers

import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import abyss.luaObjects.LuaBlock
import abyss.luaObjects.LuaItem
import net.minecraft.world.level.block.state.BlockBehaviour

object RegistrationHandler {
    private val registeredItems = mutableMapOf<String, DeferredItem<LuaItem>>()
    private val registeredBlocks = mutableMapOf<String, DeferredBlock<LuaBlock>>()

    fun registerItem(name: String): DeferredItem<LuaItem>? {
        registeredItems[name] = ModItems.ITEMS.registerItem(
            name,
            ::LuaItem,
            Item.Properties()
            )

        return registeredItems[name]
    }

    fun registerBlock(name: String, blockitem: Boolean = false): DeferredBlock<LuaBlock>? {
        val block = ModBlocks.BLOCKS.registerBlock(
            name,
            ::LuaBlock,
            BlockBehaviour.Properties.of()
            )

        registeredBlocks[name] = block

        if (blockitem){
            ModItems.ITEMS.registerSimpleBlockItem(block)
        }

        return registeredBlocks[name]
    }
}