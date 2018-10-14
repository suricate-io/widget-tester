package io.suricate.widgetTester.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.net.ssl.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public final class OkHttpClientUtils {


    /**
     * Default timeout
     */
    private static final int DEFAULT_TIMEOUT = 45;

    /**
     * Connect timeout
     */
    private static final int CONNECT_TIMEOUT = 15;

    private static final int INFINIT_TIMEOUT = 0;

    /**
     * Private constructor
     */
    private OkHttpClientUtils() {
    }

    /**
     * Class used to get an instance of OkHttpClient without certificate validation
     * @return an OkHttpClient instance
     */
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {

                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            // no check client
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            // No check certificate
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .readTimeout(INFINIT_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .retryOnConnectionFailure(true);
                    //.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.225.92.1", 80)));

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
