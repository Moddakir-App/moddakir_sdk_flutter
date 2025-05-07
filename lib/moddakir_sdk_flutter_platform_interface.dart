import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'moddakir_sdk_flutter_method_channel.dart';

abstract class ModdakirSdkFlutterPlatform extends PlatformInterface {
  /// Constructs a ModdakirSdkFlutterPlatform.
  ModdakirSdkFlutterPlatform() : super(token: _token);

  static final Object _token = Object();

  static ModdakirSdkFlutterPlatform _instance = MethodChannelModdakirSdkFlutter();

  /// The default instance of [ModdakirSdkFlutterPlatform] to use.
  ///
  /// Defaults to [MethodChannelModdakirSdkFlutter].
  static ModdakirSdkFlutterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [ModdakirSdkFlutterPlatform] when
  /// they register themselves.
  static set instance(ModdakirSdkFlutterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> startCall() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
