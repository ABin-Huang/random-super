package cn.hb.func;

import cn.hb.core.BaseFuncParam;
import cn.hb.core.BaseFunction;

import java.util.Objects;
import java.util.Random;

/**
 * @Author: abin
 * @Date: 2024/9/17 20:07
 * @Description:
 */

public class IntegerFunc implements BaseFunction<Long> {
    @Override
    public Long generateResult(BaseFuncParam param) {
        int length = Objects.nonNull(param.getResLength()) ? param.getResLength() : 6;
        return generateRandomNumber(length);
    }

    private static long generateRandomNumber(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be at least 1");
        }
        if (length > 19) {
            throw new IllegalArgumentException("Out of range");
        }

        Random random = new Random();
        // 计算最小值和最大值 // 对于7位数来说，最小值是1_000_000
        long minValue = (long) Math.pow(10, length - 1);

        // 对于7位数来说，最大值是9_999_999
        long maxValue = (long) Math.pow(10, length) - 1;

        // 生成一个[minValue, maxValue]之间的随机数
        return random.nextLong(minValue, maxValue + 1);
    }

    @Override
    public String getResult(BaseFuncParam param) {
        return "" + generateResult(param);
    }
}
