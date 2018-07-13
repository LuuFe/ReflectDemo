package com.lufeng.utils;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lufo on 2018/7/13.
 */
public class Testmain {


    @Test
    public void test() throws Exception{
        Map<String,String[]> map = new HashMap<>();
        map.put("name",new String[]{"戴维斯","詹姆斯"});
        map.put("password",new String[]{"tihu","huren"});
        map.put("age",new String[]{"25"});
        map.put("hobby",new String[]{"哪个锋中锋还是大前锋还是C","ALL the map"});
        map.put("birthday",new String[]{"2018-7-13"});
        ReflectUtil.registConvert(Date.class,new MyDataConvert());
        User user = ReflectUtil.convertData(map,User.class);
        System.out.println(user);
    }
}
