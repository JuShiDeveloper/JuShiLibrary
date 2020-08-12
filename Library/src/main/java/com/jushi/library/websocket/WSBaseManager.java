package com.jushi.library.websocket;

import android.os.Handler;


import org.json.JSONObject;


/**
 * java-websokect基础管理类模块
 * <p>
 * 使用：
 * 1、自定义类继承 {@link WSBaseManager}。
 * 2、重写 {@link WSBaseManager#onMessage(String)}、{@link WSBaseManager#onError(String)}、{@link WSBaseManager#onHeartbeatTime()}方法。
 * 3、初始化，创建自定义类实例对象调用 {@link WSBaseManager#initWs(String)}方法。
 * <p>
 * created by wyf on 2020/06/23
 */
public abstract class WSBaseManager implements WSMamagerCallBack {
    //心跳包默认时间 单位：ms
    private final long DEFAULT_HEARTBEAT_TIME = 60 * 1000;
    private final Handler handler = new Handler();
    private WSMamager wsMamager = new WSMamager();

    /**
     * 初始化webSocket
     *
     * @param wsUrl 连接地址
     */
    public void initWs(String wsUrl) {
        wsMamager.setHeartBeat(onHearbeatContent().toString());
        wsMamager.setOnWSConnectlistener(this);
        wsMamager.connect(wsUrl);
        onConnectUrl(wsUrl);
    }

    /**
     * 关闭webSocket
     */
    protected void closeWS() {
        wsMamager.closeWs();
    }

    @Override
    public long heartbeatTime() { //心跳时间
        return onHeartbeatTime() <= 0 ? DEFAULT_HEARTBEAT_TIME : onHeartbeatTime();
    }

    @Override
    public void onMessages(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onMessage(message);
            }
        });
    }


    @Override
    public void onErrors(final String error) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onError(error);
            }
        });
    }

    /**
     * 连接ws的URL
     *
     * @param wsUrl
     */
    protected void onConnectUrl(String wsUrl) {

    }

    /**
     * 重连
     */
    protected void reconnection() {
        wsMamager.reconnection();
    }

    /**
     * 发送消息
     *
     * @param message 字符串类型消息内容
     */
    protected void sendStringMessage(String message) {
        wsMamager.SendStringMessage(message);
    }

    /**
     * 获取心跳状态
     *
     * @return ture:心跳已经停止，表示连接已经断开  false:心跳仍然在活动，表示服务器连接正常
     */
    protected boolean getHeartbeatState() {
        return wsMamager.getHeartBeatStata();
    }


    /**
     * 收到消息回调
     *
     * @param message 收到的消息内容
     */
    protected abstract void onMessage(String message);

    /**
     * 出错或关闭时调用
     *
     * @param error 出错或关闭时的信息
     */
    protected abstract void onError(String error);

    /**
     * 发送心跳包时间 (默认60000ms)
     *
     * @return （单位：ms 例：1000）
     */
    protected abstract long onHeartbeatTime();

    /**
     * 心跳包内容
     *
     * @return key:value的json对象
     */
    protected abstract JSONObject onHearbeatContent();

}
