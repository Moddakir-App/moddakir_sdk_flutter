package com.moddakir.call.networking;

public interface IViewState<T> {
    public CommonStatus whichState();

    public T fetchData();

    public boolean isLoading();

    public RetrofitException fetchError();

}


