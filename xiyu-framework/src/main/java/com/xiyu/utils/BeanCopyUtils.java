package com.xiyu.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * beank 拷贝工具封装类
 */
public class BeanCopyUtils {

    /**
     * 序列化单个bean
     * @param resource 需要转化的对象
     * @param clazz 要转换的结果
     * @return
     * @param <V>
     */
    public static <V> V copyBean(Object resource, Class<V> clazz){
        //需要转换成的目标对象
        V result = null;
        try {
            //构建目标对象的构造函数
            result = clazz.newInstance();
            BeanUtils.copyProperties(resource, result);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 序列化List
     * @param clazz
     * @return
     * @param <O>
     * @param <V>
     */
    public static <O,V>List<V> copyList(List<O> list, Class<V> clazz){

        return list.stream().map(o -> copyBean(o,clazz)).collect(Collectors.toList());

    }
}
