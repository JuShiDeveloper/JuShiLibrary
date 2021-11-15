package com.jushi.library.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jushi.library.base.BaseApplication;

/**
 * 数据库,该类负责数据库升级, 建表的工作
 */
public class DoctorSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final int version = 1;

    DoctorSQLiteOpenHelper() {
        super(BaseApplication.getInstance(), "db_yongxing", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        createDeviceParamsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //update data base
        if (oldVersion < version) {
            //TO DO

        }

    }

    /**
     * 创建设备配置 设备参数缓存表，保存配置的设备参数（params 以json字符串格式保存）
     *
     * @param db
     */
    private void createDeviceParamsTable(SQLiteDatabase db) {
        db.execSQL("create table device_params(" +
                "id integer primary key autoincrement," +
                "device_code text,"+ //设备编号
                "params text," +   //参数json字符串
                "state integer default 0," +  //上传状态 0-未上传  1-已上传
                "type integer)"); //参数类型 0-LORA参数  1-网络参数
    }

}
