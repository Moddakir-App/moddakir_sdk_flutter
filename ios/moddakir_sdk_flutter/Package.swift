// swift-tools-version: 5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package: Package = Package(
    name: "moddakir_sdk_flutter",
    platforms: [
        .iOS("14.0")
    ],
    products: [
        .library(name: "moddakir-sdk-flutter", targets: ["moddakir_sdk_flutter"]),
    ],
    dependencies: [
        .package(url: "https://github.com/Moddakir-App/ModdakirSDK", .upToNextMajor(from: "0.1.7")),
    ],
    targets: [
        .target(
            name: "moddakir_sdk_flutter",
            dependencies: [ 
                .target(name: "AgoraRtmKit"),              
                .product(name: "ModdakirSDK", package: "ModdakirSDK")
            ],
            resources: [
                // If your plugin requires a privacy manifest, for example if it uses any required
                // reason APIs, update the PrivacyInfo.xcprivacy file to describe your plugin's
                // privacy impact, and then uncomment these lines. For more information, see
                // https://developer.apple.com/documentation/bundleresources/privacy_manifest_files
                // .process("PrivacyInfo.xcprivacy"),

                // If you have other resources that need to be bundled with your plugin, refer to
                // the following instructions to add them:
                // https://developer.apple.com/documentation/xcode/bundling-resources-with-a-swift-package
            ]
        ),
        .binaryTarget(name: "AgoraRtmKit", path: "Sources/AgoraRtmKit.xcframework.zip")
    ]
)
