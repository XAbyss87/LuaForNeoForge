package abyss.luaObjects

import abyss.eventHandlers.EventRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

// Wrapper for custom Block class
class LuaBlock(properties: Properties) : Block(properties) {
    private val eventRegistry = EventRegistry()

    override fun onPlace(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        oldState: BlockState,
        movedByPiston: Boolean ) {
        eventRegistry.callEvent("onPlace", state, level, pos, oldState, movedByPiston)
    }

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        oldState: BlockState,
        movedByPiston: Boolean
    ) {
        eventRegistry.callEvent("onBroken", state, level, pos, oldState, movedByPiston)
    }

    fun detonate(level: Level, pos: BlockPos, explosionRadius: Float = 4.0f) {
        if (!level.isClientSide) {
            level.removeBlock(pos, false)
            level.explode(
                null,
                pos.x + 0.5,
                pos.y + 0.5,
                pos.z + 0.5,
                explosionRadius,
                Level.ExplosionInteraction.BLOCK
            )
        }
    }
}