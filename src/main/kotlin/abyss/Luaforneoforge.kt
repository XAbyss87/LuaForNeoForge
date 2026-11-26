package abyss

import abyss.eventHandlers.GlobalEvents
import abyss.registers.ModBlocks
import abyss.registers.ModItems
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLLoader
import net.neoforged.fml.loading.FMLPaths
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.nio.file.Path

@Mod(Luaforneoforge.ID)
object Luaforneoforge {
    const val ID = "luaforneoforge"
    const val DEBUG = true

    val LOGGER: Logger = LogManager.getLogger(ID)

    // Simple but helpful
    fun luaDebug(text: String){
        if (DEBUG) LOGGER.debug(text)
    }

    init {
        ModItems.ITEMS.register(MOD_BUS)
        ModBlocks.BLOCKS.register(MOD_BUS)
    }
}