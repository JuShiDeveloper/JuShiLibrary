package com.jushi.library.zxing;

import android.os.Bundle;


/**
 * 二维码结果监听返回
 *
 */
public interface ScanListener {
	/**
	 * 返回扫描结果
	 * @param rawResult  结果对象
	 * @param bundle  存放了截图，或者是空的
	 */
	 void scanResult(String rawResult, Bundle bundle);
	/**
	 * 扫描抛出的异常
	 * @param e
	 */
	 void scanError(Exception e);
	
}
