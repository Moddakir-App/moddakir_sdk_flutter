# moddakir_sdk_flutter

 Aflutter package for Integrating with Moddakir Services, it connects you Directly with on of our distinct teachers in order to share the learning of the Holy Qur’an 📖

## Getting Started
### Adding moddakir_sdk_flutter package to pubspec.yaml file
```bash
dependencies:
  flutter:
    sdk: flutter

  moddakir_sdk_flutter:
    git:
     url: https://github.com/Moddakir-App/moddakir_sdk_flutter.git
     ref: main
```
Run Command
```bash
flutter pub get
```

### IOS
Update the Info.plist and add the mic permission description
```bash
<key>NSMicrophoneUsageDescription</key>
<string>To be able to make video and audio calls</string>
```

### Android
No setup required

### Make Call 
use the startCall method to start calling a teacher 
```bash
_ModdakirSdkFlutterPlugin.startCall(
  "ModdakirId",
  "ModdakirKey", // For Testing Env use ModdakirTestKey
  "user_email@moddakir.com",
  "User Full Name",
  "Male",
  "0123456789",
  true,
  "ar",
);
```

## Example Project
check the example project included in the source code 
```bash
cd example
flutter run
```

## Screenshots
<img src="https://github.com/user-attachments/assets/fb7b0ed8-8213-417a-b81d-e50e74c25d38" width="200" height="450">
<img src="https://github.com/user-attachments/assets/f8a4b27a-7474-485a-9b12-40f623e9d139" width="200" height="450">
<img src="https://github.com/user-attachments/assets/fc2f99c3-74a3-4aa5-bb9c-6223de937b1f" width="200" height="450">
<img src="https://github.com/user-attachments/assets/0b8398a7-9f67-43f8-8890-4b17566b4503" width="200" height="450">

