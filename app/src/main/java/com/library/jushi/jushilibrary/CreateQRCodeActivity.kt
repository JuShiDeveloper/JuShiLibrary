package com.library.jushi.jushilibrary

import android.os.Bundle
import android.widget.Toast
import com.jushi.library.base.BaseFragmentActivity
import com.jushi.library.zxing.encode.EncodingHandler
import kotlinx.android.synthetic.main.activity_create_qr_code_layout.*

/**
 * 生成二维码
 */
class CreateQRCodeActivity : BaseFragmentActivity() {


    override fun getLayoutResId(): Int = R.layout.activity_create_qr_code_layout

    override fun initView() {
    }

    override fun initData() {
    }

    override fun setListener() {
        //生成二维码
        create_qrcode.setOnClickListener {
            tv_qrcode.setImageBitmap(EncodingHandler.create2Code(EditText.text.toString(), 400))
        }

        //生成条形码
        create_barcode.setOnClickListener {
            try {
                bar_code.setImageBitmap(EncodingHandler.createBarCode(EditText.text.toString(), 400, 200))
            } catch (e: Exception) {
                Toast.makeText(this, "输入的内容条形码不支持！", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}