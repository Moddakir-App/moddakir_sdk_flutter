import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:moddakir_sdk_flutter/moddakir_sdk_flutter_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelModdakirSdkFlutter platform = MethodChannelModdakirSdkFlutter();
  const MethodChannel channel = MethodChannel('moddakir_sdk_flutter');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });
}
