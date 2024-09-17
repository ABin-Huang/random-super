package cn.hb.func;

import cn.hb.core.BaseFuncParam;
import cn.hb.core.BaseFunction;

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
        return sb.toString();
    }

    @Override
    public String getResult(BaseFuncParam param) {
        return generateResult(param);
    }

    public static void main(String[] args) {

    }
}
