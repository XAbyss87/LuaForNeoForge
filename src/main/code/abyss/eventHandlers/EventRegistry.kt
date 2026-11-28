package abyss.eventHandlers

import abyss.LuaCore
import party.iroiro.luajava.value.LuaValue

// Framework for custom events system
open class EventRegistry {
    private val listeners = mutableMapOf<String, MutableList<LuaValue>>()

    fun callEvent(eventName: String, vararg args: Any) {

        LuaCore.luaDebug("[luafml] ___Event '$eventName' Called___")

        listeners[eventName]?.forEach {
            LuaCore.luaDebug("[luafml] ___Event '$eventName' Called For '$it'___")

            it.call(*args)
        }
    }

    fun registerEvent(eventName: String, luaFunc: LuaValue) {
        LuaCore.luaDebug("[luafml] ___Registered Event Listener For '$eventName' By '$luaFunc'___")

        listeners.getOrPut(eventName) { mutableListOf() }.add(luaFunc)
    }

    fun clearEvents() {
        listeners.clear()
    }
}