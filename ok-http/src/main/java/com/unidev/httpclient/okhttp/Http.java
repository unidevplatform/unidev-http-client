package com.unidev.httpclient.okhttp;

import com.unidev.httpclient.SocksSSLSocketFactory;
import com.unidev.httpclient.SocksSocketFactory;
import com.unidev.httpclient.TrustAllX509TrustManager;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Class to do http requests
 */
public class Http {

    /**
     * Configure socks proxy.
     */
    public static OkHttpClient.Builder socksProxy(OkHttpClient.Builder builder, String ip, int port) {
        builder.socketFactory(new SocksSocketFactory(ip, port));
        builder.sslSocketFactory(new SocksSSLSocketFactory(ip, port), TrustAllX509TrustManager.INSTANCE);
        return builder;
    }
    /**
     * Set request timeout in MS
     */
    public static OkHttpClient.Builder timeout(OkHttpClient.Builder builder, int timeout) {
        builder.callTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        return builder;
    }

    public static OkHttpClient.Builder userAgent(OkHttpClient.Builder builder, String userAgent) {
        builder.addNetworkInterceptor(chain -> chain.proceed(
                chain.request()
                        .newBuilder()
                        .header("User-Agent", userAgent)
                        .build()
        ));
        return builder;
    }

    public static OkHttpClient httpClient(OkHttpClient.Builder builder) {
        return new OkHttpClient(builder);
    }

    private final OkHttpClient okHttpClient;

    public Http(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

}
