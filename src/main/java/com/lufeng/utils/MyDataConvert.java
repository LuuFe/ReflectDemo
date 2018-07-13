package com.lufeng.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lufo on 2018/7/13.
 */
public class MyDataConvert implements MyConvert<String,Date> {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public Date convert(String source) throws Exception {
        return source== null?null:simpleDateFormat.parse(source);
    }
}
