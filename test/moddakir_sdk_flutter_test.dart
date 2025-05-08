import 'package:flutter_test/flutter_test.dart';
import 'package:moddakir_sdk_flutter/moddakir_sdk_flutter.dart';
import 'package:moddakir_sdk_flutter/moddakir_sdk_flutter_platform_interface.dart';
import 'package:moddakir_sdk_flutter/moddakir_sdk_flutter_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockModdakirSdkFlutterPlatform
    with MockPlatformInterfaceMixin
    implements ModdakirSdkFlutterPlatform {
  
  @override
  Future<String?> startCall(String moddakirId, String moddakirKey, String name, String email, String gender, [String phone = '', bool isLightMode = true, String language = 'en']) {
    // TODO: implement startCall
    throw UnimplementedError();
  }

}

void main() {
  final ModdakirSdkFlutterPlatform initialPlatform = ModdakirSdkFlutterPlatform.instance;

  test('$MethodChannelModdakirSdkFlutter is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelModdakirSdkFlutter>());
  });
}
