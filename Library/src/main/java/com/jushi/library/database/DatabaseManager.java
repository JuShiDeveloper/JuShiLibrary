package com.jushi.library.database;

import android.database.sqlite.SQLiteDatabase;

import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * 数据库管理
 */
public class DatabaseManager extends BaseManager {
    /**
     * 数据库操作线程
     */
    private final Executor executor = Executors.newCachedThreadPool();
    private DoctorSQLiteOpenHelper greatHealthDBHelper;

    @Override
    public void onManagerCreate(BaseApplication application) {
        initDataBase();
    }

    private void initDataBase() {
        Runnable runnable = () -> {
            if (greatHealthDBHelper != null) {
                greatHealthDBHelper.close();
            }
            greatHealthDBHelper = new DoctorSQLiteOpenHelper();
            greatHealthDBHelper.getWritableDatabase();
        };
        executor.execute(runnable);
    }

    /**
     * 提交任务到数据库执行
     *
     * @param dbTask 数据库任务
     * @param <Data> 数据类型
     */
    public <Data> void submitDBTask(final DBTask<Data> dbTask) {
        Runnable runnable = () -> {
            SQLiteDatabase writableDatabase = greatHealthDBHelper.getWritableDatabase();
            final Data data = dbTask.runOnDBThread(writableDatabase);
            getHandler().post(() -> dbTask.runOnUIThread(data));
        };
        executor.execute(runnable);
    }


    /**
     * 数据库任务
     *
     * @param <Data> 数据库返回结果
     */
    public interface DBTask<Data> {
        /**
         * 在数据库线程执行的任务
         *
         * @param sqLiteDatabase 数据库
         * @return 数据库执行结果 Data
         */
        Data runOnDBThread(SQLiteDatabase sqLiteDatabase);

        /**
         * 数据库执行完成后,  提交到UI线程执行的任务
         *
         * @param data 数据执行结果, runOnDBThread返回
         */
        void runOnUIThread(Data data);
    }
}
