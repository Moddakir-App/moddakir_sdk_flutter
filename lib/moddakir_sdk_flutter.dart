
import 'moddakir_sdk_flutter_platform_interface.dart';

class ModdakirSdkFlutter {

  /// A startCall method to launch the ModdakirSDK Calling screen 
  /// 
  ///   * [moddakirId], Moddakir Client ID
  ///   * [moddakirKey], Moddakir Client Key - accept testing and production keys
  ///   * [email], User's email - The user identifier send the same email to the same user
  ///   * [name], User's name
  ///   * [gender], User's gender - ["Male", "Female"]
  ///   [phone], User's phone
  ///   [isLightMode], the app theme mode - [true, false] - Default true.
  ///   [language], the app language used by android only, ios will follow the app localization - ["ar", "en"] - Default "en".
  Future<String?> startCall(String moddakirId, String moddakirKey, String email, String name, String gender, [String phone = '', bool isLightMode = true, String language = 'en']) async {
    return ModdakirSdkFlutterPlatform.instance.startCall(moddakirId, moddakirKey, name, email, gender, phone, isLightMode, language);
  }
}
