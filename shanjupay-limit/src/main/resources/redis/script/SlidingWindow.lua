local function addToQueue(x,time)
    local count=0
    for i=1,x,1 do
        redis.call('lpush',KEYS[1],time)
        count=count+1
    end
    return count
end
--返回
local result=0
--限流KEY
local key = KEYS[1]
--申请数
local applyCount = tonumber(ARGV[1])
--阀值数量
local limit = tonumber(ARGV[2])
--阀值时间
local period = tonumber(ARGV[3])
redis.replicate_commands()
local now = redis.call('time')[1]
redis.call('SET','now',now)
--当前时间
local current_time = now

local timeBase = redis.call('lindex',key, limit - applyCount)
if (timeBase == false) or (tonumber(current_time) - tonumber(timeBase) > period) then
    result = result + addToQueue(applyCount,tonumber(current_time))
end
if (timeBase ~= false) then
    redis.call('ltrim',key,0,limit)
end
return result