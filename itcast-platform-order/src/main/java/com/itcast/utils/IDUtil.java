package com.itcast.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IDUtil {
    private static  IDUtil idUtil = null;

    public static IDUtil getInstance(){
        if (idUtil ==null){
            idUtil = new IDUtil();
        }
        return idUtil;
    }

    private IDUtil() {}

    /***
     * 生成id yyyyMMdd+uuid(32)
     * @return
     */
    public String createID(){
        String uuid = UUID.randomUUID().toString();	//获取UUID并转化为String对象
        uuid = uuid.replace("-", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date())+uuid;
    }
}
