package com.mall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;

/**
 * @author JngKang
 * @date 2022-06-21 18:40
 */
@SpringBootTest
public class RedisTest {
    
    /**
     * 此处没有注入xxxOperations类型，但是却能够获取到其实例？
     * 原因：Spring提供了对应的xxxOperationsEdite实现类，在通过redisTemplate进行注入时，
     * 如果发现类型与准备注入的类型不一致，则会直接通过反射，获取被注入的类型，并在其后面加上Edite后缀，
     * 拼接成xxxOperationsEdite，然后再根据反射获取到xxxOperationsEdite的实现类并进行注入。
     * <p>
     * 注：此处如果使用自带的序列化工具，那么Redis可视化数据库中将显示乱码。
     */
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Test
    void string() {
        valueOperations.set("string", "wwk");
        System.out.println(valueOperations.get("string"));
    }

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    @Test
    void list() {
        listOperations.leftPush("list", "wwk1");
        listOperations.leftPush("list", "wwk2");
        listOperations.leftPush("list", "wwk3");
        System.out.println(listOperations.leftPop("list"));
    }

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    @Test
    void hash() {
        hashOperations.put("hash", "id", "01");
        hashOperations.put("hash", "name", "wwk");
        hashOperations.put("hash", "age", "18");

        System.out.println(hashOperations.get("hash", "id"));
        System.out.println(hashOperations.values("hash"));
    }

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Test
    void set() {
        setOperations.add("set", "wwk1");
        setOperations.add("set", "wwk2");
        setOperations.add("set", "wwk3");
        System.out.println(setOperations.members("set"));
    }

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zSetOperations;

    @Test
    void zset() {
        zSetOperations.add("zset", "wwk1", 20);
        zSetOperations.add("zset", "wwk2", 50);
        zSetOperations.add("zset", "wwk3", 80);
        zSetOperations.add("zset", "wwk4", 30);
        zSetOperations.add("zset", "wwk5", 100);

        ZSetOperations.TypedTuple<String> zset = zSetOperations.popMax("zset");
        System.out.println(zset);
        ZSetOperations.TypedTuple<String> zset1 = zSetOperations.popMin("zset");
        System.out.println(zset1);
    }

}