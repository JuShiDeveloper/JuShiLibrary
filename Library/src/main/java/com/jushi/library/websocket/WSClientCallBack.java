package com.jushi.library.websocket;

import org.java_websocket.handshake.ServerHandshake;

/**
 * WebSocket回调接口
 * Created by lichengqing on 2020-04-27.
 */
 interface WSClientCallBack {
    void onOpen(ServerHandshake handshakedata);

    void onMessage(String message);

    void onClose(int code, String reason, boolean remote);

    void onError(Exception ex);
}
