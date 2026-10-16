# Tattoo Madness for Android and iOS

This folder holds Tattoo Madness wrapped as a native app with Capacitor: an Android Studio project in `android/` and an Xcode project in `ios/`. The game itself lives in `www/index.html`, and Capacitor wraps it in the native apps.

The Android steps below work on Windows. The iOS app can only be built on a Mac (see "iOS on a Mac" further down).

## What you need (Android)

- Node.js 22 or newer
- Android Studio, a recent version (Capacitor 8 needs JDK 21 and Android SDK 36; Android Studio downloads these for you)

## First run

1. Open a terminal in this folder and run:

   ```
   npm install
   npm run android
   ```

   `npm install` downloads Capacitor (the Android project needs it). `npm run android` copies the game into the Android project and opens it in Android Studio.

2. In Android Studio, wait for the Gradle sync to finish (bottom status bar). The first sync downloads a lot and can take several minutes.

3. Plug in your phone with USB debugging turned on, or start an emulator, then press the green Run button.

## Making changes to the game

Edit `www/index.html`, then run:

```
npm run sync
```

and press Run in Android Studio again. If you skip the sync step, the app keeps the old version of the game.

## iOS on a Mac

Apple only lets you build iPhone and iPad apps on a Mac with Xcode. The iOS project is already set up (landscape only, status bar hidden, the pixel heart icon and launch screen, screen kept awake), so on the Mac you only need to build and run it.

### What you need

- A Mac with the latest Xcode from the Mac App Store (open it once so it installs its extra components)
- Node.js 22 or newer
- An Apple ID. A free one is enough to run the game on your own iPhone; publishing to the App Store needs the paid Apple Developer Program.
- No CocoaPods: Capacitor 8 uses Swift Package Manager, which Xcode handles by itself.

### First run

1. Get the project onto the Mac (`git clone https://github.com/HeHuDesign/Tattoo-Madness.git`), open Terminal in the folder and run:

   ```
   npm install
   npm run ios
   ```

   `npm run ios` copies the game into the Xcode project and opens it in Xcode.

2. In Xcode, click **App** at the top of the file list, open the **Signing & Capabilities** tab, tick **Automatically manage signing** and pick your Apple ID under **Team** (add it in Xcode > Settings > Accounts if it isn't there). If Xcode says the bundle identifier `com.tattoomadness.game` isn't available, change it to something unique like `com.yourname.tattoomadness` (only in Xcode, for testing).

3. Plug in your iPhone and pick it at the top of the Xcode window (or pick a simulator). On the iPhone, turn on **Settings > Privacy & Security > Developer Mode** (it restarts the phone).

4. Press the Run button (the triangle). The first time, the iPhone may say the developer isn't trusted: go to **Settings > General > VPN & Device Management**, tap your Apple ID and trust it, then press Run again.

With a free Apple ID, the app stops opening after 7 days; just press Run in Xcode again to reinstall it. Saves stay on the phone.

### Making changes on the Mac

Edit `www/index.html`, then run `npm run sync:ios` and press Run in Xcode again. `npx cap sync` updates both Android and iOS at once.

## Building an APK or app bundle to share

In Android Studio: **Build > Generate Signed App Bundle or APK**. Choose APK to install on phones directly, or Android App Bundle for Google Play. Keep the keystore file and passwords safe, because Google Play needs the same key for every update.

## What's different from the browser version

- Runs full screen in landscape and keeps the screen awake.
- The Pixelify Sans font is bundled, so the game works offline.
- The phone's back button or back gesture closes menus, pauses a tattoo, or leaves the store and map. On the title screen it puts the app in the background.
- A controller's B button behaves the same as in the browser version, and never closes the app.
- On iOS there is no back button: use the in-game Menu and back buttons. Controllers work on iOS too.
- Saves are stored on the device and survive closing the app. Uninstalling the app or clearing its data erases them.
- The "Save PNG" button is hidden in the app, because it relied on a feature of the Claude preview.

## App details you can change

- App name and ID: `capacitor.config.json` (`appName`, `appId`). If you change the ID, change it before you publish, since Play treats a new ID as a different app.
- Icon and splash screen: PNG files in `android/app/src/main/res/mipmap-*` and `drawable*/splash.png` on Android; on iOS `ios/App/App/Assets.xcassets/AppIcon.appiconset/AppIcon-512@2x.png` (1024x1024, no transparency) and `Splash.imageset`.
- Orientation: `android:screenOrientation` in `android/app/src/main/AndroidManifest.xml`; on iOS `UISupportedInterfaceOrientations` in `ios/App/App/Info.plist`.
