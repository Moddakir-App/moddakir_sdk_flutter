package com.moddakir.call.networking;

import android.util.Log;

import com.moddakir.call.R;

import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import retrofit2.Response;

public class RetrofitExceptionHandler {


    private static final String TAG = "RetrofitException";

    public static RetrofitException httpError(Response<?> response) {//handle http network exceptions like 500 internal server error.....
        String message = null;
        String responseBody = "";
        int responseCode = 0;
        int errorCode = 0;
        int messageResourceId = R.string.server_error;
        try {
            if (response != null) {
                responseCode = response.code();
                messageResourceId = getErrorCodeMessage(responseCode);

                if (response.errorBody() != null) {
                    responseBody = response.errorBody().string();
                }

/*
                BaseResponse<?> apiErrorJson = new Gson().fromJson(
                        responseBody,
                        BaseResponse.class
                );

                if (apiErrorJson.result != null) {
                    message = apiErrorJson.result.message;
                    errorCode = apiErrorJson.result.code;
                }
*/
            }
        } catch (Exception e) {
            message = String.valueOf(responseCode);
            if (response != null)
                message = " " + response.message();
            e.printStackTrace();

        }
        if (message == null)
            message = "Something went wrong";
        return new RetrofitException(
                message,
                messageResourceId,
                responseBody,
                responseCode,
                NetworkErrorKind.HTTP,
                null,
                errorCode
        );
    }

    private static RetrofitException networkError(Throwable exception) {
        return new RetrofitException(
                exception.getMessage(),
                R.string.internetConnectionError,
                null,
                0,
                NetworkErrorKind.NETWORK,
                exception,
                0
        );
    }//handle network connection errors

    private static RetrofitException unknownError(//unknown or unhandled errors
                                                  Throwable exception
    ) {
        return new RetrofitException(
                exception.getMessage(),
                R.string.somethingWrong,
                null,
                0,
                NetworkErrorKind.UNEXPECTED,
                exception,
                0
        );
    }

    private static int getErrorCodeMessage(int code) {
        if (code == 405 || (code >= 500 && code <= 599))
            return R.string.server_error;//HTTP 405 Method Not Allowed
        else if (code == 401 || code == 403)
            return R.string.unAuthentication; // handled by interceptor
        else return R.string.server_error;//todo ask what to show
    }

    public static <T> IViewState<T> handleError(Throwable throwable) {//handle all types of exceptions like network or parsing errors
        if (throwable instanceof HttpException) {
            return Result.error(RetrofitExceptionHandler.httpError(((HttpException) throwable).response()));
        } else if (throwable instanceof java.net.ConnectException || throwable instanceof java.net.UnknownHostException || throwable instanceof SocketTimeoutException) {
            return Result.error(RetrofitExceptionHandler.networkError(throwable));
        }
        return Result.error(RetrofitExceptionHandler.unknownError(throwable));// errors like Gson parsing

    }
}
