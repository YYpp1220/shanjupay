package com.djh.shanjupay.common.interfaces.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public interface Cache {

    /**
     * 列出所有的key
     *
     * @return {@link Set<String>}
     */
    Set<String> getKeys();


    /**
     * 列出所有的key
     *
     * @param pattern 模式
     * @return {@link Set<String>}
     */
    Set<String> getKeys(String pattern);

    /**
     * 检查给定key是否存在
     *
     * @param key 关键
     * @return {@link Boolean}
     */
    Boolean exists(String key);


    /**
     * 移除给定的一个或多个key。如果key不存在，则忽略该命令。
     *
     * @param key 关键
     */
    void del(String key);

    /**
     * 删除多个key，返回删除key的个数
     *
     * @param delKeys keys
     * @return {@link Long}
     */
    Long delMulti(String... delKeys);


    /**
     * 简单的字符串设置
     *
     * @param key   关键
     * @param value 价值
     */
    void set(String key, String value);

    /**
     * 存入redis
     *
     * @param key        关键
     * @param value      价值
     * @param expiration 过期
     */
    void set(String key, String value, Integer expiration);

    /**
     * 批量设置redis键值，key值存在会覆盖原值
     *
     * @param map keysValues
     * @return {@link String}
     */
    void mset(Map<String, String> map);

    /**
     * 批量插入，如果里面的所有key都不存在，则全部插入，返回true，如果其中一个在redis中已存在，全不插入，返回false
     *
     * @param map 地图
     * @return {@link Boolean}
     */
    Boolean mIsSet(Map<String, String> map);

    /**
     * 返回key所关联的字符串值
     *
     * @param key 关键
     * @return {@link String}
     */
    String get(String key);

    /**
     * 批量获取redis键值
     *
     * @param keys 键
     * @return {@link List<String>}
     */
    List<String> mget(String... keys);

    /**
     * 获取过期剩余时间
     *
     * @param key 关键
     * @return {@link Long}
     */
    Long ttl(String key);

    /**
     * key seconds 为给定key设置生存时间。当key过期时，它会被自动删除。
     *
     * @param key    关键
     * @param expire 到期
     */
    void expire(String key, int expire);


    /**
     * 如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
     *
     * @param key   关键
     * @param value 价值
     */
    void append(String key, String value);


    /**
     * 获取旧值返回新值，不存在返回nil
     *
     * @param key      关键
     * @param newValue 新值
     * @return 旧值
     */
    String getSet(String key, String newValue);

    /**
     * 分布锁
     *
     * @param key   关键
     * @param value 价值
     * @return boolean
     */
    boolean setNx(String key, String value);


    /**
     * 计数器
     *
     * @param key   关键
     * @param delta δ
     * @return {@link Long}
     */
    Long incrBy(String key, Long delta);

    /**
     * 键值自减
     *
     * @param key 关键
     * @return {@link Long}
     */
    Long decr(String key);

    /**
     * 脚本执行
     *
     * @param script 脚本
     * @param keys   键
     * @param args   arg游戏
     * @return {@link Object}
     */
    Object eval(String script, List<String> keys, List<String> args);
}
