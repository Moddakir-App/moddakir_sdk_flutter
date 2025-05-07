package com.moddakir.call.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseMVVMActivity<VM extends ViewModel> extends BaseActivity {
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setLanguage();
        setContentView(getContentResourceID());//move to baseActivity when refactoring the whole project

        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        initViews();
        initOnCreate(savedInstanceState);
        setViewsData();
        initViewModelListeners();
    }


    abstract protected void initOnCreate(Bundle savedInstanceState);

    protected abstract Class<VM> getViewModelClass();

    protected abstract int getContentResourceID();

    protected abstract void initViews();

    protected abstract void setViewsData();

    protected abstract void initViewModelListeners();

//    protected abstract String getLang();

}
