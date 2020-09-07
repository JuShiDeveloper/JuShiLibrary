package com.jushi.library.websocket;


import com.jushi.library.utils.Logger;

import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.extensions.IExtension;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.protocols.IProtocol;
import org.java_websocket.protocols.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * WebSocket管理类
 */
class WSMamager implements WSClientCallBack {
    private final String TAG = WSMamager.class.getSimpleName();
    private final int CLOSE_CODE = 120;
    private WSMamagerCallBack onWSConnectlistener;
    private String wsUrl;
    private WSClient wsClient;
    // 心跳定时器
    private Timer mHeartTimer = null;
    private String mheartbeatMsg;
    private final Executor executor = Executors.newCachedThreadPool();//发送线程池对象
    private final Executor notifier = Executors.newCachedThreadPool();//接收线程池对象

    private boolean mStopSendHeartBeat;  //停止发送心跳

    public WSMamager() {
        //默认心跳停止
        mStopSendHeartBeat = true;
    }

    /**
     * 连接WebSocket
     *
     * @param url WebSocket域名
     */
    public void connect(String url) {
        Logger.i(TAG, "connectWs: url = " + url);
        this.wsUrl = url;
        if (mHeartTimer == null) {
            mHeartTimer = new Timer();
        }
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                if (CheckUseSSL(wsUrl)) {
                    wSSConnect(wsUrl);
                } else {
                    wSConnect(wsUrl);
                }
            }
        });
    }

    /**
     * 设置心跳消息内容
     *
     * @param heartBeatMsg 心跳消息内容
     */
    public void setHeartBeat(String heartBeatMsg) {
        mheartbeatMsg = heartBeatMsg;
    }

    public void setOnWSConnectlistener(WSMamagerCallBack onWSConnectlistener) {
        this.onWSConnectlistener = onWSConnectlistener;
    }

    public void SendStringMessage(final String message) {
        if (wsClient == null) {
            Logger.i(TAG, "wsclient is null:");
            return;
        }
        try {
            executor.execute(() -> {
                Logger.i(TAG, "send:" + message);
                if (wsClient != null && wsClient.isOpen()) {
                    wsClient.send(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭WebSocket连接
     */
    public void closeWs() {
        if (wsClient == null || wsClient.isClosing() || wsClient.isClosed()) {
            return;
        }
        wsClient.close(CLOSE_CODE);
    }

    /*
     * @function: 与服务器异常断开连接后，调此接口进行重连
     * @return : void
     */
    public void reconnection() {
        if (null != wsClient) {
            wsClient = null;
        }
        if (null != mHeartTimer) {
            mHeartTimer.cancel();
            mHeartTimer = null;
        }
        connect(wsUrl);
    }

    /**
     * 获得心跳状态
     * ture:心跳已经停止，表示连接已经断开
     * false:心跳仍然在活动，表示服务器连接正常
     */
    public boolean getHeartBeatStata() {
        return mStopSendHeartBeat;
    }

    /////////////////////////////////////////// WSClientCallBack回调接口 //////////////////////////////////////
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Logger.i(TAG, "connect ws onOpen");
        mStopSendHeartBeat = false;
        sendHeartBeat();
    }

    @Override
    public void onMessage(final String message) {
        notifier.execute(new Runnable() {
            @Override
            public void run() {
                Logger.i(TAG, "onMessage:" + message);
                onWSConnectlistener.onMessages(message);
            }
        });
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Logger.i(TAG, "onClose: code = " + code + "  reason = " + reason + "  remote = " + remote);
        mStopSendHeartBeat = true;
        onWSConnectlistener.onErrors(reason);
    }

    @Override
    public void onError(Exception ex) {
        Logger.i(TAG, "onError: " + ex.getMessage());
        mStopSendHeartBeat = true;
        onWSConnectlistener.onErrors(ex.getMessage());
    }

    /**
     * 发送心跳包
     */
    private void sendHeartBeat() {
        mHeartTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mStopSendHeartBeat) {
                    Logger.i(TAG, "mStopSendHeartBeat");
                    mHeartTimer.cancel();
                    mHeartTimer = null;
                } else {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Logger.i(TAG, "send:" + mheartbeatMsg);
                            SendStringMessage(mheartbeatMsg);
                        }
                    });
                }
            }
        }, 0, onWSConnectlistener.heartbeatTime());
    }


    /////////////////////////////////////////// 内部函数调用 //////////////////////////////////////

    /**
     * Method which returns a SSLContext from a keystore on Android or IllegalArgumentException on error
     *
     * @return a valid SSLContext
     * @throws IllegalArgumentException when some exception occurred
     */
    private SSLContext getSSLConextFromAndroidKeystore() {
        SSLContext sslContext = null;
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (IllegalArgumentException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private void wSConnect(String _serverUrl) {
        Logger.d(TAG, "wSConnect: url = " + _serverUrl);
        if (_serverUrl == null || _serverUrl.equals("")) return;
        try {
            URI url = new URI(_serverUrl);
            Draft_6455 draft = new Draft_6455(Collections.<IExtension>emptyList(), Collections.<IProtocol>singletonList(new Protocol("text")));
            wsClient = new WSClient(url, draft);
            wsClient.setCallBack(this);
            wsClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void wSSConnect(String wsUrl) {
        Logger.d(TAG, "wSSConnect: url = " + wsUrl);
        if (wsUrl == null || wsUrl.equals("")) return;
        try {
            URI url = new URI(wsUrl);
            wsClient = new WSClient(url);
            wsClient.setCallBack(this);
            SSLContext sslContext = getSSLConextFromAndroidKeystore();
            SSLSocketFactory factory = sslContext.getSocketFactory();// (SSLSocketFactory) SSLSocketFactory.getDefault();
            wsClient.setSocketFactory(factory);
            wsClient.connectBlocking();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return true--表示使用ssl，false--表示不使用ssl
     * @func 检查_serverUrl是否使用ssl
     */
    private boolean CheckUseSSL(String _serverUrl) {
        Logger.d(TAG, "CheckUseSSL url: " + _serverUrl);

        if (null == _serverUrl || _serverUrl.equals("") || _serverUrl.length() == 0) {
            return false;
        }

        // 取前3个字符wss
        String head = _serverUrl.substring(0, 3);
        Logger.d(TAG, "url head is " + head);

        if (head.compareTo("wss") == 0) {
            return true;
        }
        return false;
    }

    /**
     * 重新连接方法
     * <p>
     * 先关闭连接再使用reconnect()方法
     * <p>
     * reconnect()方法要放在子线程里否则会报错
     * <p>
     * You cannot initialize a reconnect out of the websocket thread. Use reconnect in another thread to insure a successful cleanup.
     * (无法从WebSocket线程初始化重新连接。在另一个线程中使用Reconnect以确保成功清理。)
     */
    public void onReconnect() {
        onCloseWebsocket();
        if (!wsClient.isOpen()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Logger.d(TAG, "websocket reconnect");
                    wsClient.reconnect();
                }
            }).start();
        }
    }

    /**
     * 关闭websocket连接
     */
    public void onCloseWebsocket() {
        try {
            wsClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
