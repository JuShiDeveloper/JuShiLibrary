package com.jushi.library.compression.utils;

import java.io.File;

public class FILE {
    /**
     * 创建目录，整个路径上的目录都会创建
     *
     * @param path
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
