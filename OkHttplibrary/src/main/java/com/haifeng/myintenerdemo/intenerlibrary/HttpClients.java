package com.haifeng.myintenerdemo.intenerlibrary;


import com.haifeng.myintenerdemo.intenerlibrary.Utils.SSlUtil;
import com.haifeng.myintenerdemo.intenerlibrary.Utils.StringUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 创建人：liuhaifeng
 * 时间：2019/2/15.
 * 描述：Okhttp工具类
 * 修改历史：
 */

public class HttpClients {

    private OkHttpClient client;
    private static HttpClients httpClients;
    public static final int HTTP = 0;
    public static final int HTTPS = 1;
    //服务器地址
    private String BASEURL = "";
    //链接超时
    private long CONNECT_TIMEOUT = 30;
    //读取超时
    private long READ_TIMEOUT = 30;
    //写超时
    private long WRITE_TIMEOUT = 30;
    //请求头
    private Headers header;
    //链接方式
    private boolean FLG = true;

    public static HttpClients init() {
        if (httpClients == null) {
            httpClients = new HttpClients();
        }
        return httpClients;
    }

    public HttpClients() {
        if (FLG) {
            HttpClent();
        } else {
            HttpsClient();
        }
    }
    /**
     * HTTP
     */
    public void HttpClent() {
        client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(true).build();
    }

    /**
     * HTTPS
     */
    public void HttpsClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(SSlUtil.initSSLSocketFactory(), SSlUtil.initTrustManager())
                .build();
    }

    /**
     * 设置Okhttp的baseurl
     */
    public void setBASEURL(String BASEURL) {
        this.BASEURL = BASEURL;
    }

    /**
     * 设置链接超时
     * @param CONNECT_TIMEOUT 链接超时时间（long类型）
     */
    public void setCONNECT_TIMEOUT(long CONNECT_TIMEOUT) {
        this.CONNECT_TIMEOUT = CONNECT_TIMEOUT;
        new HttpClients();
    }

    /**
     * 设置读取超时
     * @param READ_TIMEOUT 读取超时时间（long类型）
     */
    public void setREAD_TIMEOUT(long READ_TIMEOUT) {
        this.READ_TIMEOUT = READ_TIMEOUT;
        new HttpClients();
    }

    /**
     * 设置读取超时
     * @param WRITE_TIMEOUT 写超时时间（long类型）
     */
    public void setWRITE_TIMEOUT(long WRITE_TIMEOUT) {
        this.WRITE_TIMEOUT = WRITE_TIMEOUT;
        new HttpClients();
    }

    /**
     * 设置请求头
     */
    public void setHeader(Headers header) {
        this.header = header;
    }

    /**
     * 获取请求头
     */
    public Headers getHeader() {
        if (null == header) {
            header = new Headers.Builder().build();
        }
        return header;
    }

    /***
     * 设置链接方式
     *@param type  HTTP  http  HTTPS  https
     * */
    public void ISHttps(int type) {
        if (type == HTTP) {
            FLG = true;
        } else {
            FLG = false;
        }
        new HttpClients();
    }

    /**
     * GET请求
     * @param url    接口 （如：api/text/***）
     * @param params 入参
     */
    public void DoGet(String url, Map<String, Object> params) {
        Request request = new Request.Builder().url(StringUtils.buildUrl(BASEURL + "/" + url, params)).headers(getHeader()).get().build();
    }


}
