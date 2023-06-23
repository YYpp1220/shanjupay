--限流KEY
local key = KEYS[1]
--限流大小
local limit = tonumber(ARGV[1])
--时间周期
local period = tonumber(ARGV[2])

local current = tonumber(redis.call('get', key) or "0")
--如果超出限流大小
if current + 1 > limit then
    return 0
else
    redis.call("incr", key)
    if current == 1 then
        redis.call("expire", key,period)
    end
    return 1
end