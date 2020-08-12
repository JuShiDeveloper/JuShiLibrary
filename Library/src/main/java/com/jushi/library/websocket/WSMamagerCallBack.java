package com.jushi.library.websocket;

interface WSMamagerCallBack {

    long heartbeatTime();

    void onMessages(String message);

    void onErrors(String error);
}
