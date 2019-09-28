package com.imooc.Utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        return System.currentTimeMillis()+String.valueOf(random.nextInt(900000)+100000);
    }
}
