redis.call('ZADD', KEYS[1], tonumber(ARGV[1]), ARGV[1])
local c
c = redis.call('ZCARD', KEYS[1])
redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, tonumber(ARGV[2]))
redis.call('EXPIRE', KEYS[1], tonumber(ARGV[3]))
return c;

--KEYS[1]:该次限流对应的key
--ARGV[1]:一分钟之前的时间戳
--ARGV[2]:此时此刻的时间戳
--ARGV[3]:允许通过的最大数量
--ARGV[4]:member名称（随机生成）
--[[
redis.call('zremrangeByScore', KEYS[1], 0, ARGV[1])
local res = redis.call('zcard', KEYS[1])
if (res == nil) or (res < tonumber(ARGV[3])) then
    redis.call('zadd', KEYS[1], ARGV[2], ARGV[4])
    return 0
else return 1 end]]

--ARGV[1]: 一分钟之前的时间戳
--ARGV[2]: 此时此刻的时间戳
--ARGV[3]: 假装一个随机数,各位看官老爷请自己实现. (主要是防止极端情况下时间戳也会重复的问题)
--ARGV[4]: 最大次数
--ARGV[5]: 过期时间

--[[redis.call('zremrangeByScore', KEYS[1], 0, ARGV[1])
local res = redis.call('zcard', KEYS[1])
if res and (tonumber(res) < tonumber(ARGV[4])) then
    redis.call('zadd', KEYS[1], ARGV[2], ARGV[3])
    redis.call('expire', KEYS[1], ARGV[5])
    return 1
else
    return 0
end]]
