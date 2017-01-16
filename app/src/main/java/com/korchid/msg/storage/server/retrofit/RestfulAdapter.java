package com.korchid.msg.storage.server.retrofit;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.korchid.msg.storage.server.retrofit.ApiService.API_URL;

/**
 * Created by mac0314 on 2017-01-03.
 */

// http://tiii.tistory.com/11
public class RestfulAdapter {
    private static final String TAG = "RestfulAdapter";
    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;
    private static final String SERVER_URL = "https://www.korchid.com/"; // Retrofit 2부터 url뒤에 /를 입력
    private static OkHttpClient client;
    private static ApiService apiService;

    public synchronized static ApiService getInstance(){
        if(apiService == null){
            // 통신 로그를 확인하기 위한 부분
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 쿠키 매니저의 cookie policy를 변경
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            // OkHttpClient를 생성
            client = configureClient(new OkHttpClient().newBuilder())
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            apiService = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService.class);


        }
        return apiService;
    }

    /**
     * Uncertificated 허용
     *
     * @param builder
     * @return
     */

    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder){
        final TrustManager[] cert = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        SSLContext context = null;
        try{
            context = SSLContext.getInstance("TLS");
            context.init(null, cert, new SecureRandom());
        }catch (final java.security.GeneralSecurityException e){
            e.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            };

            builder.sslSocketFactory(context.getSocketFactory()).hostnameVerifier(hostnameVerifier);
        }catch (final Exception e){
            e.printStackTrace();
        }

        return builder;
    }

}
