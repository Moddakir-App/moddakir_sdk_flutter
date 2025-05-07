package com.moddakir.call.networking;

public class Result<T> implements IViewState<T> {
    CommonStatus status;
    T data;
    boolean isLoading;
    RetrofitException error;

    public Result(CommonStatus status, T data, RetrofitException error) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.isLoading = false;
    }

    public Result(CommonStatus status, T data, RetrofitException error, boolean isLoading) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.isLoading = isLoading;
    }

    @Override
    public CommonStatus whichState() {
        return status;
    }

    @Override
    public T fetchData() {
        return data;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public RetrofitException fetchError() {
        return this.error;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(CommonStatus.SUCCESS, data, null);
    }

    public static <T> Result<T> success() {
        return new Result<T>(CommonStatus.SUCCESS, null, null);
    }


    public static <T> Result<T> error(RetrofitException retrofitException) {
        return new Result<T>(CommonStatus.ERROR, null, retrofitException);
    }


    public static <T> Result<T> loading(boolean isLoading) {
        return new Result<T>(CommonStatus.LOADING, null, null, isLoading);
    }

}
