package com.jushi.library.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 文件上传 带上传进度
 */
public class UploadFileRequester implements Callback {
    private final String TAG = UploadFileRequester.class.getSimpleName();
    private static final OkHttpClient httpClient;
    private OnUploadListener onUploadListener;

    static {
        httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (onUploadListener != null)
                onUploadListener.onProgress(msg.arg1);
            return false;
        }
    });


    public void uploadFile(String url, String fileName, OnUploadListener onUploadListener) {
        this.onUploadListener = onUploadListener;
        Request request = new Request.Builder()
                .url(url)
                .post(getStreamBody(fileName))
                .build();
        httpClient.newCall(request).enqueue(this);
    }

    private RequestBody getStreamBody(final String fileName) {
        return new RequestBody() {

            @Override
            public long contentLength() throws IOException {
                return new File(fileName).length();//若是断点续传则返回剩余的字节数
            }

            @Override
            public MediaType contentType() {
                return MediaType.parse("application/octet-stream");
//                return MediaType.parse("image/png");
                //这个根据上传文件的后缀变化，要是不知道用application/octet-stream
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                //方式一：
                FileInputStream fis = new FileInputStream(new File(fileName));
                fis.skip(0);//跳到指定位置，断点续传
                long writeLength = 0;
                int length;
                byte[] buffer = new byte[2048];
                OutputStream outputStream = sink.outputStream();
                while ((length = fis.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, length);
                    //或者
                    sink.write(buffer, 0, length);
                    writeLength += length;
                    Message message = handler.obtainMessage();
                    message.arg1 = (int) (writeLength * 1.0f / contentLength() * 100);
                    handler.sendMessage(message);
                }
            }
        };
    }


    /**
     * 上传多个文件
     *
     * @param url
     * @param fileNames
     */
    public void uploadFiles(String url, List<String> fileNames) {
        Log.v(TAG, "url = " + url);
        Call call = httpClient.newCall(getRequest(url, fileNames));
        call.enqueue(this);
    }

    /**
     * 获得Request实例
     *
     * @param url
     * @param fileNames 完整的文件路径
     * @return
     */
    private Request getRequest(String url, List<String> fileNames) {
        Request.Builder builder = new Request.Builder();
        builder.url(url).post(getRequestBody(fileNames));
        return builder.build();
    }

    /**
     * 通过上传的文件的完整路径生成RequestBody
     *
     * @param fileNames 完整的文件路径
     * @return
     */
    private RequestBody getRequestBody(List<String> fileNames) {
        //创建MultipartBody.Builder，用于添加请求的数据
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < fileNames.size(); i++) {
            File file = new File(fileNames.get(i));
            String fileType = getMimeType(file.getName());
            builder.addFormDataPart( //给Builder添加上传的文件
                    "image",  //请求的名字
                    file.getName(), //文件的名字，服务器端用来解析的
                    RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
            );
        }
        return builder.build();
    }

    private static String getMimeType(String name) {
        return name.substring(name.lastIndexOf("."), name.length());
    }


    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        onUploadListener.onError(e.getMessage());
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        onUploadListener.onSuccess();
    }

    public interface OnUploadListener {
        void onProgress(int progress);

        void onSuccess();

        void onError(String msg);
    }

}
