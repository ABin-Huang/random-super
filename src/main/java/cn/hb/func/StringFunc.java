package cn.hb.func;

import cn.hb.core.BaseFuncParam;
import cn.hb.core.BaseFunction;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.Objects;

/**
 * @Author: abin
 * @Date: 2024/9/17 19:57
 * @Description: 生成随机字符
 */

public class StringFunc implements BaseFunction<String> {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateResult(BaseFuncParam param) {
        int length = Objects.nonNull(param.getResLength()) ? param.getResLength() : 6;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return "\"" + sb + "\"";
    }

    @Override
    public String getResult(BaseFuncParam param) {
        if (Objects.isNull(param.getResNum())) {
            return generateResult(param);
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
