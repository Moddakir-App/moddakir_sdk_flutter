import 'package:flutter/material.dart';

import 'package:moddakir_sdk_flutter/moddakir_sdk_flutter.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _ModdakirSdkFlutterPlugin = ModdakirSdkFlutter();
  String gender = "Male";
  bool isLightMode = true;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Moddakir-SDK Example App')),
        body: Container(
          height: 300,
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Padding(
                  padding: const EdgeInsets.all(30.0),
                  child: Text('Start a call with a randomly selected teacher of the same gender'),
                ),
                Padding(
                  padding: const EdgeInsets.all(20.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      DropdownButton<String>(
                        value: isLightMode ? 'Light Mode' : 'Dark Mode',
                        items:
                            <String>['Light Mode', 'Dark Mode'].map((String value) {
                              return DropdownMenuItem<String>(value: value, child: Text(value));
                            }).toList(),
                        onChanged: (item) {
                          setState(() {
                            isLightMode = item == "Light Mode";
                          });
                        },
                      ),
                      DropdownButton<String>(
                        value: gender,
                        items:
                            <String>['Male', 'Female'].map((String value) {
                              return DropdownMenuItem<String>(value: value, child: Text(value));
                            }).toList(),
                        onChanged: (item) {
                          setState(() {
                            gender = item ?? 'Male';
                          });
                        },
                      ),
                    ],
                  ),
                ),
                SizedBox(height: 20),
                TextButton(
                  onPressed: () {
                    _ModdakirSdkFlutterPlugin.startCall(
                      "sdk_1",
                      "ts6824-adaf49", // For Testing Env use ModdakirTestKey
                      "m.moussa@moddakir.com",
                      "Mahmoud Moussa",
                      gender,
                      "201000215275",
                      isLightMode,
                      "ar",
                    );
                  },
                  child: Text('Call A Teacher'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
