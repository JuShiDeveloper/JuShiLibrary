package com.jushi.library.websocket;


import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * WebSocket客户端实例
 */
class WSClient extends WebSocketClient {
    private final String TAG = WebSocketClient.class.getSimpleName();
    private WSClientCallBack callBack;

    public WSClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public WSClient(URI serverUri) {
        super(serverUri);
    }

    public void setCallBack(WSClientCallBack wsCallBack) {
        callBack = wsCallBack;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        callBack.onOpen(handshakedata);
    }

    @Override
    public void onMessage(String message) {
        callBack.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        callBack.onClose(code, reason, remote);

    }

    @Override
    public void onError(Exception ex) {
        callBack.onError(ex);
    }

    /**
     * ping通时的方法
     * <p>
     * 此方法可以判断websocket是否连接，如果超过超就重新连接
     *
     * @param conn
     * @param f
     */
    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        super.onWebsocketPong(conn, f);
    }


}
