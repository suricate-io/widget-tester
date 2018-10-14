package io.suricate.widgetTester.service.nashorn.script;

import io.suricate.widgetTester.model.dto.error.RemoteError;
import io.suricate.widgetTester.model.dto.error.RequestException;
import io.suricate.widgetTester.utils.OkHttpClientUtils;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;
import java.util.concurrent.TimeoutException;

public final class Methods {

    /**
     * Default httpClient
     */
    private static OkHttpClient client = OkHttpClientUtils.getUnsafeOkHttpClient();

    /**
     * Method used to call a webservice
     * @param url the url to call
     * @param headerName the header name to add
     * @param headerValue the header value to add
     * @param returnHeader can be null, if it was not null this method return the header value
     * @return the response body of the request or the header value if returnHeader is defined
     */
    public static String call(String url, String headerName, String headerValue, String returnHeader) throws Exception{

        Request.Builder builder = new Request.Builder().url(url);
        if (StringUtils.isNotBlank(headerName)) {
            builder.addHeader(headerName, headerValue);
        }
        Request request = builder.build();
        Response response = null;
        String ret = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()){
                if (StringUtils.isNotBlank(returnHeader)){
                    ret = response.header(returnHeader);
                } else {
                    ret = response.body().string();
                }
            } else {
                if (response.code() >= 500) {
                    throw new RemoteError("Response error: " + response.message()+" code:"+response.code());
                } else {
                    throw new RequestException(response.message() + " - code:" + response.code() , response.body() != null ? response.body().string() : null);
                }
            }
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly();
        }
        return ret;
    }

    /**
     * Method used to valid is a thread is interrupted
     * @throws InterruptedException an exception if the thread is interrupted
     */
    public static void checkInterupted() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()){
            throw new InterruptedException("Script Interrupted");
        }
    }


    /**
     * Method used to convert ASCII string to base 64
     * @param data string to convert
     * @return Base64 string
     */
    public static String btoa(String data){
        if (StringUtils.isBlank(data)){
            return null;
        }
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    /**
     * Method used to throw new error
     * @throws RemoteError
     */
    public static void throwError() throws RemoteError {
        throw new RemoteError("Error");
    }

    /**
     * Method used to throw new fatal error
     * @throws RemoteError
     */
    public static void throwFatalError(String msg) throws RemoteError {
        throw new UnknownError(msg);
    }

    /**
     * Method used to throw new timeout exception
     * @throws TimeoutException timeout exception
     */
    public static void throwTimeout() throws TimeoutException {
        throw new TimeoutException("Timeout");
    }

    /**
     * private constructor
     */
    private Methods() {
    }
}
