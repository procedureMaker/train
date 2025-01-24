package com.example.train.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * 封装hutool雪花算法
 * */
public class SnowUtil {
    private static long dataCenterId = 1;   //数据中心
    private static long workId = 1;         //机器Id

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflakeNextId();
    }

    public static String getSnowflakeNextStr() {
        return IdUtil.getSnowflakeNextIdStr();
    }
}