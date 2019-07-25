package com.jushi.library.compression.minterface;

import java.io.File;
import java.util.List;

/**
 * 图片压缩回掉接口 （压缩多张图片）
 */
public interface OnCompressionPicturesListener {
    /**
     * 回掉方法，将压缩后的图片集合返回
     *
     * @param pictures 装有压缩成功后的图片文件的集合
     */
    void onPictureFiles(List<File> pictures);
    void onError(String msg);
}
