local environments = {}

function LoadMod(modId, source)
    local env = environments[modId]

    if not env then
        env = {}

        env._G = env

        setmetatable(env, {
            __index = _G
        })

        environments[modId] = env
    end

    local chunk = assert(load(source, modId, "t", env))

    chunk()
end

function CallMod(modId, functionName, ...)
    local env = assert(environments[modId], "Mod not found")

    local fn = rawget(env, functionName)
    assert(type(fn) == "function", "Function not found")

    return fn(...)
end