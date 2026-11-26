package abyss.eventHandlers

import party.iroiro.luajava.value.LuaValue
import abyss.Luaforneoforge

open class EventRegistry {
    private val listeners = mutableMapOf<String, MutableList<LuaValue>>()

    fun callEvent(eventName: String, vararg args: Any) {
        listeners[eventName]?.forEach {
            if (Luaforneoforge.DEBUG)
                Luaforneoforge.luaDebug("___Event '$eventName' Called For '$it'___")

            it.call(*args)
        }
    }

    fun registerEvent(eventName: String, luaFunc: LuaValue) {
        if (Luaforneoforge.DEBUG)
            Luaforneoforge.luaDebug("___Registered Event Listener For '$eventName' By '$luaFunc'___")

        listeners.getOrPut(eventName) { mutableListOf() }.add(luaFunc)
    }

    fun clearEvents() {
        listeners.clear()
    }
}