package abyss.luaObjects

import abyss.eventHandlers.EventRegistry
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

// Wrapper for custom Item class
class LuaItem(properties: Properties) : Item(properties) {

    @JvmField
    val events = EventRegistry()

    override fun use(
        level: Level,
        player: Player,
        hand: InteractionHand
    ): InteractionResultHolder<ItemStack> {

        val stack = player.getItemInHand(hand)

        if (!level.isClientSide) {
            events.callEvent("onUse", level, player, hand)
        }

        return InteractionResultHolder.sidedSuccess(
            stack,
            true
        )
    }
}