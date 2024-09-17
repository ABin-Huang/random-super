package cn.hb.core;

/**
 * @Author: abin
 * @Date: 2024/9/17 19:55
 * @Description: 功能接口
 */

public interface BaseFunction<T> {
    T generateResult(BaseFuncParam param);
    String getResult(BaseFuncParam param);
}
