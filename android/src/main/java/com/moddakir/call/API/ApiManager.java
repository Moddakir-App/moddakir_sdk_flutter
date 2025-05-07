package com.moddakir.call.API;

import android.content.Context;
import android.content.Intent;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.moddakir.call.BuildConfig;
import com.moddakir.call.Constants;
import com.moddakir.call.API.ApiCalls.UserCalls;
import com.moddakir.call.utils.AccountPreference;
import com.moddakir.call.utils.Perference;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {
    private Retrofit retrofitInstance;

    public ApiManager(Context context) {
        this.context = context;
    }

    static Context context;
    public static OkHttpClient.Builder getUnsafeOkHttpClient(boolean IsAuth,boolean IsVerification ,String... token) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.addInterceptor(chain -> {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("Accept-Language", Perference.getLang(context));
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("x-api-key", Constants.getAppKey());
                requestBuilder.header("appName",  Constants.getAppID());

                if (IsAuth) {
                    if (token != null && token.length > 0)
                        requestBuilder.header("Authorization", "Bearer " +AccountPreference.getAccessToken(context));
                    else
                        requestBuilder.header("Authorization", "Bearer " +AccountPreference.getAccessToken(context));
                }

                Request request = requestBuilder.build();

                return chain.proceed(request);
            });
            builder.addInterceptor(chain -> {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if (response.code() == 401 || response.code() == 403) {
                    clearData(context);
                }
                return response;
            });
//            if (BuildConfig.DEBUG) {
//                ChuckerInterceptor chuckerInterceptor = new ChuckerInterceptor(context.getApplicationContext());
//
//                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
//                builder.addInterceptor(loggingInterceptor);
//                builder.addInterceptor(chuckerInterceptor);
//            }


            builder.hostnameVerifier((hostname, session) -> true);
            builder.connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void clearData(Context context) {
       // SocketClientListener.closeSocket();
        AccountPreference.setAccessToken("",context);
        AccountPreference.removeCurrentAccount(context);
    }


    private Retrofit createRetrofit(boolean IsAuth,boolean IsVerification, String... token) {
        return new Retrofit.Builder()
                .baseUrl("https://api.moddakir.com/v9/api/")
                .addConverterFactory(GsonConverterFactory.create()) // <- add this
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getUnsafeOkHttpClient(IsAuth, IsVerification,token).build())
                .build();
    }

    public UserCalls getUserCalls(boolean IsAuth, String... token) {
        final Retrofit retrofit = createRetrofit(IsAuth, false,token);
        return retrofit.create(UserCalls.class);
    }


}
