package com.moddakir.call;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import com.moddakir.call.view.agora.AgoraActivity;
import android.content.Context;

import android.app.Activity;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

/** ModdakirSdkFlutterPlugin */
public class ModdakirSdkFlutterPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will handle communication between Flutter and native Android
  private MethodChannel channel;
  private Context context;
  private Activity activity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "moddakir_sdk_flutter");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("startCall")) {
      String moddakirId = call.argument("moddakirId");
      String moddakirKey = call.argument("moddakirKey");
      String gender = call.argument("gender");
      String name = call.argument("name");
      String phone = call.argument("phone");
      String email = call.argument("email");
      String language = call.argument("language");
      Boolean isLightMode = call.argument("isLightMode");

      System.out.println("moddakirKey: " + moddakirKey);
      System.out.println("name: " + name);

        AgoraActivity.makeCall(moddakirId, moddakirKey, activity, gender, name, phone, email, language, isLightMode, 0);
        result.success("Done"); 
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    // TODO: Handle if needed
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    // TODO: Handle if needed
  }

  @Override
  public void onDetachedFromActivity() {
    // TODO: Handle if needed
  }


}
