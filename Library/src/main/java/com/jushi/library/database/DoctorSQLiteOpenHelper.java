package com.jushi.library.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jushi.library.base.BaseApplication;

/**
 * 数据库,一个用户一个, 该类负责数据库升级, 建表的工作
 */
public class DoctorSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final int version = 1;

    DoctorSQLiteOpenHelper() {
        super(BaseApplication.getInstance(), "database_name", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //update data base
        if (oldVersion < 2) {
            //TO DO

        }

    }

}
