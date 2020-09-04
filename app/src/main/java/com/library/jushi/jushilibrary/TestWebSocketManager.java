package com.library.jushi.jushilibrary;

import com.jushi.library.websocket.WSBaseManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * WebSocket使用示例
 */
public class TestWebSocketManager extends WSBaseManager {

    @Override
    protected void onMessage(String message) {
        //接收到消息
    }

    @Override
    protected void onError(String error) {
        // 出错
    }

    @Override
    protected long onHeartbeatTime() {
        return 500; //发送心跳包时间，单位毫秒
    }

    @Override
    protected JSONObject onHearbeatContent() { //发送的心跳包内容
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key","value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public void connectWS(String url){
        //初始化并连接WebSocket
        initWs(url);
    }


    public void sendMsg(String msg){
        //发送消息
        sendStringMessage(msg);
    }
}
