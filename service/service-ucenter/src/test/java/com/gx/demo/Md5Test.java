package com.gx.demo;

import com.gx.serviceucenter.utils.MD5;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 20:42
 */
public class Md5Test {
    public static void main(String[] args) {
        String qwert = MD5.encrypt("qwert");
        System.out.println(qwert);
    }
}
