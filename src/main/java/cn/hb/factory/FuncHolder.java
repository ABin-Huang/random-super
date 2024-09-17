package cn.hb.factory;

import cn.hb.core.BaseFunction;
import cn.hb.enums.TypeEnum;
import cn.hb.func.IntegerFunc;
import cn.hb.func.StringFunc;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: abin
 * @Date: 2024/9/17 20:26
 * @Description:
 */

public class FuncHolder {
    private static Map<String, BaseFunction> funcMap = new HashMap<>(2);

    static {
        funcMap.put(TypeEnum.STRING.getName(), new StringFunc());
        funcMap.put(TypeEnum.INTEGER.getName(), new IntegerFunc());
    }

    public static BaseFunction getByType(TypeEnum type) {
        return funcMap.get(type.getName());
    }
}
