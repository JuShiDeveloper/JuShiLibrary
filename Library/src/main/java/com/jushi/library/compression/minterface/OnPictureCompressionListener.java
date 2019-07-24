package com.jushi.library.compression.minterface;

import java.io.File;

/**
 * 图片压缩回掉接口 （压缩单张图片）
 */
public interface OnPictureCompressionListener {

    /**
     * 压缩后的图片文件回掉方法
     * @param file 压缩成功后的图片文件
     */
    void onCompressSuccess(File file);

    void onCompressStart();

    void onCompressError(String msg);

}
