package com.zhang.cloud.util;

import java.math.BigDecimal;

/**
 * @author 98549
 * @date 2021/12/27 18:07
 */
public class DataUtil {
    public  static Double add(Double var1,Double var2){
        return BigDecimal.valueOf(var1).add(BigDecimal.valueOf(var2)).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(add(0.1,0.2));
    }
}
