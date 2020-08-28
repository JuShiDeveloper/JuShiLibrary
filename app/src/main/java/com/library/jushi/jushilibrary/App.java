package com.library.jushi.jushilibrary;

import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseManager;

import java.util.List;

public class App extends BaseApplication {
    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    /**
     * 注册管理类
     *
     * @param lists
     */
    @Override
    protected void registerManager(List<BaseManager> lists) {
        super.registerManager(lists);

    }
}
