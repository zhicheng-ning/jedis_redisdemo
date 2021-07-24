package com.nzcer.jedis;

import redis.clients.jedis.Jedis;


public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("xxx.xxx.xxx.xxx", 6379);
        System.out.println("连接Redis成功！"+jedis.ping());
        jedis.close();
    }
}
