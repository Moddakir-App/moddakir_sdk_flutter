import Flutter
import UIKit
import ModdakirSDK

public class ModdakirSdkFlutterPlugin: NSObject, FlutterPlugin {
  static var language = "ar"

  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "moddakir_sdk_flutter", binaryMessenger: registrar.messenger())
    let instance = ModdakirSdkFlutterPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    
    switch call.method {
    case "startCall":

    let controller : FlutterViewController = UIApplication.shared.keyWindow?.rootViewController as! FlutterViewController
    if let arguments = call.arguments as? Dictionary<String, Any> {

      ModdakirSdkFlutterPlugin.language = (arguments["language"] as? String) ?? "ar"
      let moddakirId = (arguments["moddakirId"] as? String) ?? ""
      let moddakirKey = (arguments["moddakirKey"] as? String) ?? ""
      let name = (arguments["name"]  as? String) ?? ""
      let phone = (arguments["phone"]  as? String) ?? ""
      let genderString = (arguments["gender"]  as? String) ?? ""
      var gender: ModdakirGender = .male
      if genderString == "Female" {
        gender = .female
      }
      let email = (arguments["email"]  as? String) ?? ""
      let isLightMode = (arguments["isLightMode"]  as? Bool) ?? true
      let color = UIColor.red
      var themeMode: ModdakirThemeManager.ThemeMode = .light
      if isLightMode == false {
        themeMode = .dark
      }

      ModdakirService.initiateCall(   
              moddakirId: moddakirId,
              moddakirKey: moddakirKey,           
              userInfo: .init(
                  fullName: name,
                  gender: gender,
                  email: email,
                  phone: phone
              ),
              rootView: controller,
              primaryColor: color,              
              themeMode: themeMode
        )
      ModdakirService.delegate = controller
      result("Done")
     

  } else {
    result(FlutterError.init(code: "errorSetDebug", message: "data or format error", details: nil))
  }

     
    default:
      result(FlutterMethodNotImplemented)
    }
  }
}


extension FlutterViewController: ModdakirServiceDelegate {
    public func failedToInitiateCall(withError error: String, andType type: ModdakirService.InitiateCallError) {
      
      var title: String = "Error!"
      var buttonText: String = "Ok"
      var body: String = "Error!"
      var language = ModdakirSdkFlutterPlugin.language ?? "ar"
      if language == "ar" {
        title = "حدث خطأ!"
        buttonText = "حسناً"
      }
        switch type {
            case .noInternet:
                 body = "No internet connection"
                if language == "ar" {
                  body = "لايوجد اتصال بالانترنت"
                }
            case .notValidEmail:
                 body = "Email is not valid"
                if language == "ar" {
                  body = "البريد الالكتروني غير صحيح"
                }
            case .emptyName:
                body = "Name is required"
                if language == "ar" {
                  body = "يجب ادخال الاسم بشكل صحيح"
                }
            case .unauthorized:
               body = "Wrong credentials"
                if language == "ar" {
                  body = "بيانات التحقق غير صحيحة"
                }
        }

        let alert = UIAlertController(title: title, message: body, preferredStyle: UIAlertController.Style.alert)
        let ok = UIAlertAction(title: buttonText, style: .default, handler: { (action) -> Void in
            self.dismiss(animated: true, completion: nil)
        })
        alert.addAction(ok)
        self.present(alert, animated: true, completion: nil)
    }
}
