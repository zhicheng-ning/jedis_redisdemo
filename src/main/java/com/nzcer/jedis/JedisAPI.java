package com.nzcer.jedis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.args.FlushMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 测试Redis相关的API命令
 */
public class JedisAPI {
    private Jedis jedis;
    @Before
    public void init() {
        jedis = new Jedis("139.196.204.173", 6379);
    }

    @After
    public void close() {
        jedis.close();
    }

    @Test
    /**
     * 测试String相关的API
     */
    public  void testString() {
        jedis.flushDB();
        jedis.set("k1", "100");
        jedis.set("k2", "200");
        System.out.println(jedis.keys("*").size());
        jedis.keys("*").stream().forEach(System.out::println);
        System.out.println(jedis.get("k1"));
        jedis.append("k1", "abc");
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.strlen("k1"));
        System.out.println(jedis.setnx("k1", "888"));
        System.out.println(jedis.setnx("k3", "300"));
        System.out.println(jedis.get("k3"));
        System.out.println(jedis.get("k2"));
        System.out.println(jedis.incr("k2"));
        System.out.println(jedis.decr("k2"));
        System.out.println(jedis.incrBy("k2", 10));
        System.out.println(jedis.decrBy("k2", 5));
        System.out.println(jedis.mset("k4", "400", "k5", "500"));
        jedis.mget("k4", "k5").stream().forEach(System.out::println);
        jedis.set("name", "zhicheng");
        System.out.println(jedis.getrange("name", 0, 2));
        System.out.println(jedis.setrange("name", 0, "nzc"));
        System.out.println(jedis.get("name"));
        System.out.println(jedis.setex("sex", 10L, "male"));
        System.out.println(jedis.ttl("sex"));
        System.out.println(jedis.ttl("sex"));
        System.out.println(jedis.ttl("sex"));
        System.out.println(jedis.getSet("name", "ning"));
        System.out.println(jedis.get("name"));
    }

    @Test
    /**
     * 测试List相关的API
     */
    public void testList() {
        jedis.flushDB(FlushMode.ASYNC);
        System.out.println(jedis.lpush("k1", "v1", "v2", "v3"));
        jedis.lrange("k1", 0, -1).stream().forEach(System.out::println);
        System.out.println(jedis.rpush("k2", "v1", "v2", "v3"));
        jedis.lrange("k2", 0, -1).stream().forEach(System.out::println);
        System.out.println(jedis.lpop("k1"));
        System.out.println(jedis.rpop("k2"));
        jedis.lrange("k1", 0, -1).stream().forEach(System.out::println);
        System.out.println(jedis.rpoplpush("k2", "k1"));
        System.out.println(jedis.llen("k1"));
        jedis.lrange("k1", 0, -1).stream().forEach(System.out::println);
        System.out.println(jedis.lindex("k1", 2));
        System.out.println(jedis.lset("k1", 0, "v3"));
        jedis.lrange("k1", 0, -1).stream().forEach(System.out::println);
        System.out.println(jedis.linsert("k1", ListPosition.BEFORE, "v3", "v4"));
        System.out.println(jedis.linsert("k1", ListPosition.AFTER, "v1", "v0"));
        jedis.lrange("k1", 0, -1).stream().forEach(System.out::println);
        System.out.println(jedis.lrem("k1", 1, "v3"));
        jedis.lrange("k1", 0, -1).stream().forEach(System.out::println);
    }

    @Test
    /**
     * 测试Set相关的API
     */
    public void testSet() {
        jedis.flushDB(FlushMode.ASYNC);
        System.out.println(jedis.sadd("k1", "m1", "m2", "m3"));
        jedis.smembers("k1").stream().forEach(System.out::println);
        System.out.println(jedis.sismember("k1", "m4"));
        System.out.println(jedis.scard("k1"));
        System.out.println(jedis.spop("k1"));
        System.out.println(jedis.srem("k1", "m1"));
        jedis.smembers("k1").stream().forEach(System.out::println);
        System.out.println(jedis.sadd("k1", "m1", "m2", "m3"));
        jedis.srandmember("k1", 2).stream().forEach(System.out::println);
        jedis.smembers("k1").stream().forEach(System.out::println);
        System.out.println(jedis.sadd("k2", "m1", "m4", "m5"));
        System.out.println(jedis.smove("k1", "k2", "m3"));
        jedis.smembers("k2").stream().forEach(System.out::println);
        jedis.sinter("k1", "k2").stream().forEach(System.out::println);
        jedis.sunion("k1", "k2").stream().forEach(System.out::println);
    }

    @Test
    /**
     * 测试Hash相关API
     */
    public void testHash() {
        jedis.flushDB(FlushMode.ASYNC);
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "zhicheng");
        map.put("age", "22");
        System.out.println(jedis.hset("user", map));
        System.out.println(jedis.hget("user", "name"));
        System.out.println(jedis.hexists("user", "sex"));
        jedis.hkeys("user").stream().forEach(System.out::println);
        jedis.hvals("user").stream().forEach(System.out::println);
        System.out.println(jedis.hincrBy("user", "age", 5));
        System.out.println(jedis.hget("user", "age"));
        System.out.println(jedis.hsetnx("user", "age", "30"));
        System.out.println(jedis.hsetnx("user", "sex", "male"));
        jedis.hkeys("user").stream().forEach(System.out::println);
        jedis.hvals("user").stream().forEach(System.out::println);
    }


    @Test
    /**
     * 测试Zset相关API
     */
    public void testZset() {
        jedis.flushDB(FlushMode.ASYNC);
        Map<String, Double> map = new HashMap<>();
        map.put("python", 100.0);
        map.put("java", 200.0);
        map.put("c++", 300.0);
        jedis.zadd("topn", map);
        jedis.zrange("topn", 0, -1).stream().forEach(System.out::println);
        Set<Tuple> topn = jedis.zrangeWithScores("topn", 0, -1);
        for (Tuple tuple : topn) {
            System.out.printf("%s %f\n", tuple.getElement(), tuple.getScore());
        }
        jedis.zrangeByScore("topn", 200, 500).stream().forEach(System.out::println);
        Set<Tuple> topn1 = jedis.zrangeByScoreWithScores("topn", 200, 500);
        for (Tuple tuple : topn1) {
            System.out.printf("%s %f\n", tuple.getElement(), tuple.getScore());
        }
        Set<Tuple> topn2 = jedis.zrevrangeByScoreWithScores("topn", 200, 0);
        for (Tuple tuple : topn2) {
            System.out.printf("%s %f\n", tuple.getElement(), tuple.getScore());
        }
        System.out.println(jedis.zincrby("topn", 50, "java"));
        jedis.zrevrange("topn", 0, -1).stream().forEach(System.out::println);
        System.out.println(jedis.zrank("topn", "java"));
        System.out.println(jedis.zrank("topn", "c++"));
        System.out.println(jedis.zrank("topn", "python"));
        Set<Tuple> topn3 = jedis.zrangeWithScores("topn", 0, -1);
        for (Tuple tuple : topn3) {
            System.out.printf("%s %f\n", tuple.getElement(), tuple.getScore());
        }
        System.out.println(jedis.zcount("topn", 100, 200));
        System.out.println(jedis.zrem("topn", "c++"));
        Set<Tuple> topn4 = jedis.zrangeWithScores("topn", 0, -1);
        for (Tuple tuple : topn4) {
            System.out.printf("%s %f\n", tuple.getElement(), tuple.getScore());
        }
    }
}
