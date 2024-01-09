package com.amtechsolutions.becpbas.api.service;

import com.amtechsolutions.becpbas.BuildConfig;
import com.amtechsolutions.becpbas.StringHelper;
import com.amtechsolutions.becpbas.api.HostSelectionInterceptor;
import com.amtechsolutions.becpbas.api.RestUtils;
import com.amtechsolutions.becpbas.api.SslUtils;
import com.amtechsolutions.becpbas.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class BaseService {

    public static Retrofit getAPIClient(String urlType) {
        OkHttpClient.Builder okHttpClient = null;
        TrustManagerFactory trustManagerFactory;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, null);

            okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .addInterceptor(chain -> {
                        Request request;
                        if (StringHelper.isEmpty(AppConstants.token)) {
                            request = chain.request().newBuilder()
                                    .build();
                        }else{
                            request = chain.request().newBuilder().addHeader("Accept","application/json")
                                    .build();
                        }


                        return chain.proceed(request);
                    })
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            okHttpClient.addInterceptor(HostSelectionInterceptor.getSelectionInterceptor());
            okHttpClient.addInterceptor(loggingInterceptor);

            if (SslUtils.INSTANCE.getTrustAllHostsSSLSocketFactory() != null) {
                okHttpClient.sslSocketFactory(SslUtils.INSTANCE.getTrustAllHostsSSLSocketFactory(), trustManager);
            }

            try {
                okHttpClient.sslSocketFactory(SslUtils.INSTANCE.getSslContextForCertificateFile(SellAchaApplication.getInstance().getApplicationContext()).getSocketFactory(), trustManager);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }

        Retrofit retrofit;
        retrofit = new Retrofit.Builder().baseUrl(RestUtils.getEndPoint(urlType))
                // .addConverterFactory(new NullOnEmptyConverterFactory())
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(createGSON()))
                .build();

        return retrofit;
    }

    private static Gson createGSON() {
        return new GsonBuilder().setLenient().create();
    }


    private static OkHttpClient getUnsafeOkHttpClient() {

        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                .build();

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
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


            builder.connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT,spec));
            builder.readTimeout(5, TimeUnit.MINUTES);
            builder.connectTimeout(5,TimeUnit.MINUTES);

            if(BuildConfig.DEBUG){
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
                builder.interceptors().add(interceptor);
            }

            // builder.addInterceptor(headerAuthorizationInterceptor);

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
