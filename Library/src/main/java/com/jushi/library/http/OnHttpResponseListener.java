package com.jushi.library.http;

public interface OnHttpResponseListener<Data> {
    void onHttpRequesterResponse(int code, String router, Data data);

    void onHttpRequesterError(int code, String router, String message);
}
