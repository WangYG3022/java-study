package com.wangyaguang.study.io;

import java.io.*;

/**
 * @Description:
 * @Author: WANG Y.G.
 * @Time: 2020/01/13 14:42
 * @version: V1.0
 */
public class TaskRunnable implements Runnable{

    private File file;

    private String key;

    public TaskRunnable(File file, String key) {
        this.file = file;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                 if (line.contains(key)) {
                     System.out.println(Thread.currentThread().getName() + "线程找到关键字" + key + "\n文件名:"
                             + file.getAbsolutePath() + "\n内容:" + line);
                 }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
