package com.jushi.library.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.jushi.library.BuildConfig;
import com.jushi.library.crash.ExceptionCaughtHandler;
import com.jushi.library.database.DatabaseManager;
import com.jushi.library.bluetooth.BluetoothFuncManager;
import com.jushi.library.manager.MClipboardManager;
import com.jushi.library.manager.SdManager;
import com.jushi.library.manager.UserManager;
import com.jushi.library.manager.NetworkManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseApplication extends Application {
    private HashMap<String, BaseManager> managers = new HashMap<>();
    private static BaseApplication application;
    private Handler handler;


    public static BaseApplication getInstance() {
        return application;
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        handler = new Handler();
        // 初始化管理器
        initManager();
//        initCrashLog();
    }


    private void initManager() {
        List<BaseManager> managerList = new ArrayList<BaseManager>();
        registerManager(managerList);
        for (BaseManager baseManager : managerList) {
            injectManager(baseManager);
            baseManager.onManagerCreate(this);
            managers.put(baseManager.getClass().getName(), baseManager);
        }

        for (Map.Entry<String, BaseManager> entry : managers.entrySet()) {
            entry.getValue().onAllManagerCreated();
        }
    }

    public <V extends BaseManager> V getManager(Class<V> cls) {
        return (V) managers.get(cls.getName());
    }

    public void injectManager(Object object) {
        Class<?> aClass = object.getClass();
        while (aClass != BaseManager.class && aClass != Object.class) {
            Field[] declaredFields = aClass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    int modifiers = field.getModifiers();
                    if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                        // 忽略掉static 和 final 修饰的变量
                        continue;
                    }
                    if (!field.isAnnotationPresent(Manager.class)) {
                        continue;
                    }

                    Class<?> type = field.getType();
                    if (!BaseManager.class.isAssignableFrom(type)) {
                        throw new RuntimeException("@Manager 注解只能应用到BaseManager的子类");
                    }
                    BaseManager baseManager = getManager((Class<? extends BaseManager>) type);
                    if (baseManager == null) {
                        throw new RuntimeException(type.getSimpleName() + " 管理类还未初始化！");
                    }
                    if (!field.isAccessible())
                        field.setAccessible(true);
                    try {
                        field.set(object, baseManager);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            aClass = aClass.getSuperclass();
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Manager {

    }

    /**
     * 注册管理类
     *
     * @param lists
     */
    protected void registerManager(List<BaseManager> lists) {
        lists.add(new NetworkManager());            // 网络管理器
        lists.add(new DatabaseManager());            // 数据库管理类
        lists.add(new UserManager());
        lists.add(new SdManager());
        lists.add(new BluetoothFuncManager());
        lists.add(new MClipboardManager());
    }

    private void initCrashLog(){
    if (BuildConfig.DEBUG){
        // 测试版本   开启崩溃显示功能
        Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        ExceptionCaughtHandler exceptionCaughtHandler = new ExceptionCaughtHandler(handler);
        Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtHandler);
    }else {
    }
}

    /**
     * 获得进程名字
     */
    protected String getUIPName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager.getRunningAppProcesses() == null || mActivityManager.getRunningAppProcesses().size() == 0)
            return "";
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}
