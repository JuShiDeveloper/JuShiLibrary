package com.jushi.library.http;


import android.view.Gravity;

import com.jushi.library.base.BaseApplication;
import com.jushi.library.manager.UserManager;
import com.jushi.library.utils.LogUtil;
import com.jushi.library.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载文件
 */
public class DownloadFileRequester implements Callback {
    private static final OkHttpClient httpClient;
    private OnDownloadListener downloadListener;
    protected UserManager userManager = BaseApplication.getInstance().getManager(UserManager.class);
    private String downloadPath;
    private String fileName;

    static {
        httpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * @param url              文件下载地址
     * @param savePath         文件保存路径
     * @param fileName         保存的文件名
     * @param downloadListener
     */
    public void download(String url, String savePath, String fileName, OnDownloadListener downloadListener) {
        log("****************************开始下载文件*********************");
        log("url ：" + url);
        log("savePath ：" + savePath);
        log("fileName ：" + fileName);
        this.downloadListener = downloadListener;
        this.downloadPath = savePath;
        this.fileName = fileName;
        File file = new File(downloadPath, fileName);
        if (file.exists() && file.isFile() && file.length() > 10) {
            if (downloadListener != null)
                downloadListener.onSuccess(file.getAbsolutePath());
            log("文件已存在：" + file.getPath());
            log("*************************************************************");
            return;
        }
        log("*************************************************************");
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(getHeaders()))
                .get()
                .build();
        httpClient.newCall(request).enqueue(this);
    }

    /**
     * @param url              文件下载地址
     * @param savePath         文件保存路径
     * @param fileName         保存的文件名
     * @param downloadListener
     */
    public void download2(String url, String savePath, String fileName, OnDownloadListener downloadListener) {
        log("****************************开始下载文件*********************");
        log("url ：" + url);
        log("savePath ：" + savePath);
        log("fileName ：" + fileName);
        this.downloadListener = downloadListener;
        this.downloadPath = savePath;
        this.fileName = fileName;
        log("*************************************************************");
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(getHeaders()))
                .get()
                .build();
        httpClient.newCall(request).enqueue(this);
    }

    private void log(String log) {
//        LogUtil.v(log);
    }

    /**
     * 请求头参数
     *
     * @return
     */
    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        if (userManager.getUserInfo() != null)
            headers.put("Authorization", userManager.getUserInfo().getToken());
        return headers;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        if (downloadListener != null)
            downloadListener.onError(-1, e.getMessage());
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.code() != 200) {
            if (downloadListener != null)
                downloadListener.onError(response.code(), "文件下载链接无法访问");
            return;
        }
        InputStream is = null;
        byte[] buf = new byte[2048];
        int length = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            File file = new File(downloadPath, fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((length = is.read(buf)) != -1) {
                fos.write(buf, 0, length);
                sum += length;
                if (downloadListener != null)
                    downloadListener.onProgress((int) (sum * 1.0f / total * 100));
            }
            fos.flush();
            if (downloadListener != null)
                downloadListener.onSuccess(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            if (downloadListener != null)
                downloadListener.onError(-1, e.getMessage());
            File file = new File(downloadPath, fileName);
            if (file.exists())
                file.delete();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnDownloadListener {
        void onProgress(int progress);

        void onSuccess(String filePath);

        void onError(int code, String msg);
    }

    //自定义SS验证相关类
    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }
}
