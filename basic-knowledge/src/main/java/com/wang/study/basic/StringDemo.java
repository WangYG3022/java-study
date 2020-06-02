package com.wang.study.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/03/26 18:13
 * @version: V1.0
 */
public class StringDemo {

    private static Pattern pattern = Pattern.compile("JSESSIONID=(\\w+);");

    public static void main(String[] args) {
        String src = "uqwhekh9812736238JSESSIONID=DB1E3D9AA313B5C1964225318EED4284;kjahsdkhbdsfjksdkf";
        Matcher matcher = pattern.matcher(src);
        if (matcher.find()) {
            String JSESSIONID = matcher.group();
            System.out.println(JSESSIONID.substring(0, JSESSIONID.length() - 1));
        } else {
            System.out.println("没有找到指定内容");
        }
    }
}
