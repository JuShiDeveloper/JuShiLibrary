package com.library.jushi.jushilibrary

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.jushi.library.base.BaseFragmentActivity
import com.jushi.library.takingPhoto.PictureHelper
import com.jushi.library.utils.Logger
import com.jushi.library.utils.PermissionUtil
import com.jushi.library.zxing.ScanListener
import com.jushi.library.zxing.ScanManager
import com.jushi.library.zxing.decode.Utils
import kotlinx.android.synthetic.main.activity_scan_code_layout.*
import java.lang.Exception

/**
 * 二维码扫描示例界面
 */
class ScanCodeActivity : BaseFragmentActivity(), ScanListener {
    private val REQUEST_CODE_PHOTO = 0x101

    private lateinit var scanManager: ScanManager
    private lateinit var photo_path: String

    override fun getLayoutResId(): Int = R.layout.activity_scan_code_layout

    override fun initView(savedInstanceState: Bundle) {
        PermissionUtil.request(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
    }

    override fun initData() {
        scanManager = ScanManager(this, SurfaceView, capture_container, capture_crop_view, capture_scan_line, ScanManager.ALL_MODE, this)
    }

    override fun setListener() {
        qrcode_g_gallery.setOnClickListener {
            PictureHelper.gotoPhotoAlbum(this, REQUEST_CODE_PHOTO)
        }
        iv_light.setOnClickListener {
            scanManager.switchLight()
        }
        service_register_rescan.setOnClickListener {
            if (service_register_rescan.visibility == View.VISIBLE) {
                service_register_rescan.visibility = View.INVISIBLE
                scan_image.visibility = View.GONE
                scanManager.reScan()
            }
        }
        qrcode_ic_back.setOnClickListener { finish() }

    }

    override fun onResume() {
        super.onResume()
        scanManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        scanManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        scanManager.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            REQUEST_CODE_PHOTO -> {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = this.contentResolver.query(data!!.data, proj, null, null, null)
                if (cursor!!.moveToFirst()) {
                    val colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    photo_path = cursor.getString(colum_index)
                    photo_path?.let {
                        photo_path = Utils.getPath(applicationContext, data!!.data)
                    }
                    scanManager.scanningImage(photo_path)
                }
            }
        }
    }

    /**
     * 扫描成功结果
     */
    override fun scanResult(result: String?, bundle: Bundle?) {
        Logger.v("ScanCodeActivity", result)
    }

    /**
     * 扫描失败
     */
    override fun scanError(e: Exception?) {
        Logger.v("ScanCodeActivity", e!!.message)
    }
}