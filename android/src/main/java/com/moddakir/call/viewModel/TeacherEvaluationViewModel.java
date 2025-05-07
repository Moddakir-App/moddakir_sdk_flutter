package com.moddakir.call.viewModel;

import android.content.Context;

import com.moddakir.call.API.ApiManager;
import com.moddakir.call.SingleLiveEvent;
import com.moddakir.call.base.BaseViewModel;
import com.moddakir.call.networking.IViewState;
import com.moddakir.call.Model.EvaluateTeacherResponse;
import java.util.HashMap;

public class TeacherEvaluationViewModel extends BaseViewModel {
    SingleLiveEvent<IViewState<EvaluateTeacherResponse>> rateObserver = new SingleLiveEvent<>();



    public SingleLiveEvent<IViewState<EvaluateTeacherResponse>> getRateObserver() {
        return rateObserver;
    }


    public void rateRequest(HashMap<String, Object> map, Context context) {
        executeCall((new ApiManager(context).getUserCalls(true)).rateTeacher(map), rateObserver);
    }



}
