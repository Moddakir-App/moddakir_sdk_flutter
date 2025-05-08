import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'moddakir_sdk_flutter_platform_interface.dart';

/// An implementation of [ModdakirSdkFlutterPlatform] that uses method channels.
class MethodChannelModdakirSdkFlutter extends ModdakirSdkFlutterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('moddakir_sdk_flutter');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> startCall(String moddakirId, String moddakirKey, String name, String email, String phone, String gender, [bool isLightMode = true, String language = 'en']) async {
    final version = await methodChannel.invokeMethod<String>('startCall', 
    {
      'moddakirId': moddakirId,
      'moddakirKey': moddakirKey,
      'name': name,
      'email': email,
      'gender': gender,
      'isLightMode': isLightMode,
      'language': language,
      'phone': phone,
    }
    );
    return version;
  }
}
