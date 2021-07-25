package com.nzcer.jedis;

import redis.clients.jedis.Jedis;

import java.util.Objects;
import java.util.Random;

/**
 * 模拟手机验证码案例
 * 1、输入手机号，点击发送后随机生成6位数字验证码，2分钟有效
 * 2、输入验证码，点击验证，返回成功或失败
 * 3、每个手机号24小时之内只能输入三次
 */
public class VerifyPhoneCode {
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            codeToRedis("18573938527");
            verifyCode("18573938527","666666");
            //正确的验证码：378526
            //输入的验证码：666666
            //验证失败！
            //正确的验证码：347970
            //输入的验证码：666666
            //验证失败！
            //正确的验证码：375082
            //输入的验证码：666666
            //验证失败！
            //验证码二十四小时之内只能输入三次
            //正确的验证码：375082
            //输入的验证码：666666
            //验证失败！
        }
    }

    /**
     * 生成随机6位数字验证码
     * @return 返回6位数字验证码
     */
    public static String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int value = random.nextInt(10);
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 验证码存入到Redis中，同时设置有效时间以及设置输入次数
     * @param phone
     */
    public static void codeToRedis(String phone) {
        Jedis jedis = new Jedis("xxx.xxx.xxx.xxx", 6379);
        String phoneKey = phone + "Key";
        String phoneCount = phone + "Count";
        String nowCount = jedis.get(phoneCount);
        if (nowCount == null) {
            //首次发送验证码
            jedis.setex(phoneCount, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(nowCount)<=2) {
            jedis.incr(phoneCount);
        } else {
            System.out.println("验证码二十四小时之内只能输入三次");
            jedis.close();
            return;
        }
        //将验证码放到redis
        String phoneCode = generateCode();
        jedis.setex(phoneKey, 2 * 60, phoneCode);
        jedis.close();
    }

    /**
     * 判断验证码
     * @param phone
     * @param inputCode
     */
    public static void verifyCode(String phone, String inputCode) {
        Jedis jedis = new Jedis("xxx.xxx.xxx.xxx", 6379);
        String phoneKey = phone + "Key";
        String phoneCode = jedis.get(phoneKey);
        System.out.println("正确的验证码：" + phoneCode);
        System.out.println("输入的验证码：" + inputCode);
        if (Objects.equals(phoneCode, inputCode)) {
            System.out.println("验证成功！");
        } else {
            System.out.println("验证失败！");
        }
        jedis.close();
    }
    
}
