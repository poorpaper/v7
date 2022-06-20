package com.poorpaper.commons.util;

import java.util.Random;

public class CodeUtils {

    public static String generateCode(int len) {
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, len);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(CodeUtils.generateCode(6));
        }
    }
}
