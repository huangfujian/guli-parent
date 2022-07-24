package com.gx;

import java.math.BigDecimal;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/25 18:30
 */
public class MyTest {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(0.01);
        System.out.println(bigDecimal.multiply(new BigDecimal("100")).longValue());
    }
}
