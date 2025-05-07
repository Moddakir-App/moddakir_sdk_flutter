package com.moddakir.call.API.ApiCalls;


import com.moddakir.call.Model.BaseResponse;
import com.moddakir.call.Model.Plan.afterCallResponse;
import com.moddakir.call.Model.ConnectingBannersResponse;
import com.moddakir.call.Model.CreateCallResponseModel;
import com.moddakir.call.Model.EvaluateTeacherResponse;
import com.moddakir.call.Model.FreezeAccountResponseModel;
import com.moddakir.call.Model.LoginResponseModel;
import com.moddakir.call.Model.RandomTeacherResponseModel;
import com.moddakir.call.Model.ResponseModel;
import java.util.Map;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface UserCalls {

    @POST("create-call-log")
    Single<Response<CreateCallResponseModel>> CreateCall(@Body Map<String, String> param);


    @POST("update-call-log")
    Single<Response<ResponseModel>> UpdateCall(@Body Map<String, Object> param);

    @HTTP(method = "GET", path = "get-path-items", hasBody = true)
    Single<Response<afterCallResponse>> GetDataAfterCall(@Body Map<String, Object> param);

    @POST("joined-agora-signaling")
    Single<Response<BaseResponse>> JoinAgoraSignaling(@Body Map<String, Object> param);

    @GET("sdk/banner")
    Single<Response<ConnectingBannersResponse>> getSdkBanners();

    @POST("sdk/login")
    Single<Response<LoginResponseModel>> loginToSdk(@Body Map<String, Object> param);

    @GET("random-teacher-id")
    Single<Response<RandomTeacherResponseModel>> getRandomTeacher();


    @POST("add-teacher-review")
    Single<EvaluateTeacherResponse> rateTeacher(@Body Map<String, Object> param);


    @GET("unfreeze-account")
    Single<FreezeAccountResponseModel> unFreezeAccount();


    @POST("update-call-end-reason")
    Single<BaseResponse> endReason(@Body Map<String, Object> param);
    @POST("update-rtm-logs")
    Single<BaseResponse> updateRtmLogs(@Body Map<String, Object> param);

}
