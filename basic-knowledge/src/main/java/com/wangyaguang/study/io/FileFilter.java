package com.wangyaguang.study.io;

import java.io.File;

/**
 * @Description: 查询指定目录下.java文件中包含关键字的行，并打印
 * @Author: WANG Y.G.
 * @Time: 2020/01/13 14:45
 * @version: V1.0
 */
public class FileFilter {

    private static void checkFile(File file) {
        // 文件夹
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                checkFile(f);
            }
        }
        // 文件
        if (file.isFile() && file.getName().endsWith(".txt")) {
            String KEY_WORD = "hello";
            new Thread(new TaskRunnable(file, KEY_WORD), "T1").start();
        }
    }

    public static void main(String[] args) {
        String FILE_PATH = "D:\\image";
        File file = new File(FILE_PATH);
        checkFile(file);
    }
}
