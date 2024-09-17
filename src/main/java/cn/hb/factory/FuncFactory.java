package cn.hb.factory;

import cn.hb.core.BaseFuncParam;
import cn.hb.core.BaseFunction;
import cn.hb.enums.TypeEnum;

/**
 * @Author: abin
 * @Date: 2024/9/17 20:21
 * @Description: 模块功能工厂
 */

public class FuncFactory {
    public static BaseFunction get(TypeEnum type) {
        return FuncHolder.getByType(type);
    }

    public static void main(String[] args) {
        System.out.println(FuncFactory.get(TypeEnum.STRING).generateResult(new BaseFuncParam()));
    }
}
