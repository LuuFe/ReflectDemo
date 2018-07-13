package com.lufeng.utils;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Lufo on 2018/7/13.
 */
public class ReflectUtil {

    private static Map<Class,MyConvert> classMyConvertMap = new HashMap<>();

    public  static  void registConvert(Class clazz,MyConvert convert){
            classMyConvertMap.put(clazz,convert);
    }
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    //当前方法将request中的参数封装到对象当中
    public static <T> T convertData(Map<String,String[]> map,Class<T> clazz) throws Exception{

        T newInstance = clazz.newInstance();

        //解析参数
        //获取每个参数名字并封装到对象上面
        //form表单参数吆喝对象上的属性一致
        Set<Map.Entry<String, String[]>> entries = map.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            System.out.println("当前正在封装："+key);

            //通过key找到封装的与之对应的属性名
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(key, clazz);
            if (propertyDescriptor!=null){
                Method writeMethod = propertyDescriptor.getWriteMethod();

                //获取到set方法然偶将其set
                String[] value = entry.getValue();


                //保证参数类型和长度是与之相匹配的，要把form表单穿过来的数据转换成setter相对应的参数类型

                Class<?>[] parameterTypes = writeMethod.getParameterTypes();//获取set方法参数type

                //转换  转就完事了
                if(parameterTypes.length>=1){
                    Class<?> parameterType = parameterTypes[0];//获取到参数类型是一个class


                    //int类型
                    if (parameterType==int.class||parameterType==Integer.class){
                        if(value==null||value.length!=1){
                            throw  new RuntimeException("参数"+key+"的长度必须为1");
                        }else {
                            int parseInt = Integer.parseInt(value[0]);
                            writeMethod.invoke(newInstance,parseInt);
                        }
                    }

                    //String类型
                    else if (parameterType==String.class){
                        if(value!=null){
                            writeMethod.invoke(newInstance, Arrays.toString(value));
                        }
                    }
                    //String[]类型
                    else if (parameterType==String[].class){
                        //java反射规范中，数组参数的传递需要进行转换，并将之转换成Object[]
                        //数组类型会抛出长度execption
//                        String[] strings = new String[value.length];
//                        for (int i= 0 ;i<value.length;i++){
//                            strings[i] = value[i];
//                        }
//                        writeMethod.invoke(newInstance,strings);
                        writeMethod.invoke(newInstance,new Object[]{value});
                    }

                    //int[]类型
                    else if (parameterType == int[].class||parameterType==Integer[].class){
                        //writeMethod.invoke(newInstance,new Object[]{value});
                        int[] ints = new int[value.length];
                        for (int i = 0; i < ints.length; i++) {
                            ints[i]= Integer.parseInt(value[i]);

                        }
                        //if this is 手动new的对象，不用再转换成Object
                        writeMethod.invoke(newInstance,ints);
                    }else if (parameterType==Date.class||parameterType== java.sql.Date.class){
                        if (value==null||value.length!=1){
                            throw new RuntimeException("参数"+key+"的长度必须为1");
                        }else {
                            MyConvert myConvert = classMyConvertMap.get(parameterType);
                            if (myConvert!=null){
                                Object convert = myConvert.convert(value[0]);
                                writeMethod.invoke(newInstance,convert);
                            }else {
                                Date date = simpleDateFormat.parse(value[0]);
                                writeMethod.invoke(newInstance,date);
                            }
                        }
                    }else{//不知道的类型，，自己提供转换器转换
                        MyConvert myConvert = classMyConvertMap.get(parameterType);
                        if (myConvert!=null){
                            Object convert = myConvert.convert(value[0]);
                            writeMethod.invoke(newInstance,convert);
                        }
                    }

                    }

            }
        }


        //返回带有数据的对象，，也94创建的对象
        return newInstance;
    }
}
