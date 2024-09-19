package cn.hb.func;

import cn.hb.core.BaseFuncParam;
import cn.hb.core.BaseFunction;

import java.util.Objects;
import java.util.UUID;

/**
 * @Author: abin
 * @Date: 2024/9/19 19:10
 * @Description:
 */

public class IdFunc implements BaseFunction<String> {
    @Override
    public String generateResult(BaseFuncParam param) {
        return "\"" + UUID.randomUUID().toString().replace("-", "")+ "\"";
    }

    @Override
    public String getResult(BaseFuncParam param) {
        if (Objects.isNull(param.getResNum())) {
            return String.valueOf(generateResult(param));
        }
        return getList(param);
    }

    @Override
    public String getList(BaseFuncParam param) {
        Integer resNum = param.getResNum();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resNum; i++) {
            sb.append(generateResult(param)).append(",");
        }
        return sb.substring(0, sb.length()-1);
    }
}
