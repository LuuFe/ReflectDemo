package com.lufeng.utils;

/**
 * Created by Lufo on 2018/7/13.
 */


public interface MyConvert<S,T> {

    T convert(S source) throws Exception;

}
