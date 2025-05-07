package com.moddakir.call.base;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moddakir.call.networking.IViewState;
import com.moddakir.call.networking.Result;
import com.moddakir.call.networking.RetrofitExceptionHandler;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class BaseViewModel extends ViewModel {


    private static final String TAG = "BaseViewModel";
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected <T> void executeCall(Single<T> call, MutableLiveData<IViewState<T>> responseLiveData) {//generic method to execute apis calls
        responseLiveData.setValue(Result.loading(true));
        compositeDisposable.add(call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSuccess(t -> Timber.v("OnSucess"))
                .subscribe(response ->//onSuccess get called for 2xx response code  like 200
                {
                    responseLiveData.setValue(Result.loading(false));
                    responseLiveData.setValue(Result.success(response));
                }, throwable -> {//onError get called when an error occurred or status code != 2xx like 500
                    responseLiveData.setValue(Result.loading(false));
                    /*if (throwable.getMessage() != null)
                        Timber.e(throwable);*/
                    responseLiveData.setValue(RetrofitExceptionHandler.handleError(throwable));

                }));

    }


    @Override
    protected void onCleared() {//dispose and clear  and dispose all disposable if viewModel is cleared to cancel all previous calls
//        compositeDisposable.dispose();
        compositeDisposable.clear();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
