package com.ets.utils;

import java.util.UUID;

/**
 * @author 吴浩
 * @create 2019-01-08 11:38
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
