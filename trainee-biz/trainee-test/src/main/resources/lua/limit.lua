local key = KEYS[1]
local limitCount = ARGV[1]
local expire = ARGV[2]
local c
c = redis.call('get', key)
-- 若当前接口访问计数已超过限制，则直接返回当前访问总数
if c and tonumber(c) > tonumber(limitCount) then
return c;
end

-- 访问计数自增
c = redis.call('incr', key)
-- 设置过期时间
if tonumber(c) == 1 then
redis.call('expire', key, expire)
end

return c