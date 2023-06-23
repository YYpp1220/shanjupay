--利用redis的hash结构，存储key所对应令牌桶的上次获取时间和上次获取后桶中令牌数量
local ratelimit_info = redis.pcall('HMGET',KEYS[1],'last_time','current_token_num')
local last_time = ratelimit_info[1]
local current_token_num = tonumber(ratelimit_info[2])

redis.replicate_commands()
local now = redis.call('time')[1]
redis.call('SET','now',now);



--tonumber是将value转换为数字，此步是取出桶中最大令牌数、生成令牌的速率(每秒生成多少个)、当前时间
local max_token_num = tonumber(ARGV[1])
local token_rate = tonumber(ARGV[2])
--local current_time = tonumber(ARGV[3])
local current_time = now
--reverse_time 即多少毫秒生成一个令牌
local reverse_time = 1000/token_rate

--如果current_token_num不存在则说明令牌桶首次获取或已过期，即说明它是满的
if current_token_num == nil then
  current_token_num = max_token_num
  last_time = current_time
else
  --计算出距上次获取已过去多长时间
  local past_time = current_time-last_time
  --在这一段时间内可产生多少令牌
  local reverse_token_num = math.floor(past_time/reverse_time)
  current_token_num = current_token_num +reverse_token_num
  last_time = reverse_time * reverse_token_num + last_time
  if current_token_num > max_token_num then
    current_token_num = max_token_num
  end
end

local result = 0
if(current_token_num > 0) then
  result = 1
  current_token_num = current_token_num - 1
end

--将最新得出的令牌获取时间和当前令牌数量进行存储,并设置过期时间
redis.call('HMSET',KEYS[1],'last_time',last_time,'current_token_num',current_token_num)
redis.call('pexpire',KEYS[1],math.ceil(reverse_time*(max_token_num - current_token_num)+(current_time-last_time)))

return result