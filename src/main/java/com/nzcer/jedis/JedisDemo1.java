package com.nzcer.jedis;

import redis.clients.jedis.Jedis;


public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("139.196.204.173", 6379);
        System.out.println("连接Redis成功！"+jedis.ping());
        jedis.close();
    }
}
