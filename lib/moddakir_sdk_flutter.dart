
import 'moddakir_sdk_flutter_platform_interface.dart';

class ModdakirSdkFlutter {
  Future<String?> getPlatformVersion() {
    return ModdakirSdkFlutterPlatform.instance.getPlatformVersion();
  }

  Future<String?> startCall() {
    return ModdakirSdkFlutterPlatform.instance.startCall();
  }
}
