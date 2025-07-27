package com.zjb.zjbpicturebackend.utils;

/**
 *颜色转换工具类
 */
public class ColorTransformUtils {

    public static String transformColor(String color) {
        if(color.length() == 8){
            return color;
        }
        //1.去掉0x前缀
        String hexColor = color.substring(2);
        //将剩余部分数字解析为成16进制数，再转换成6位十六进制字符串
        return "0x" + String.format("%06x", Integer.parseInt(hexColor, 16));
    }

    public static void main(String[] args) {
        System.out.println(transformColor("0x080e0"));
    }
}
