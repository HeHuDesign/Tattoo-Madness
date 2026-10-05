# Tattoo Madness for Android

This folder is an Android Studio project for Tattoo Madness, built with Capacitor. The game itself lives in `www/index.html`, and Capacitor wraps it in a native Android app.

## What you need

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

## Building an APK or app bundle to share

In Android Studio: **Build > Generate Signed App Bundle or APK**. Choose APK to install on phones directly, or Android App Bundle for Google Play. Keep the keystore file and passwords safe, because Google Play needs the same key for every update.

## What's different from the browser version

- Runs full screen in landscape and keeps the screen awake.
- The Pixelify Sans font is bundled, so the game works offline.
- The phone's back button or back gesture closes menus, pauses a tattoo, or leaves the store and map. On the title screen it puts the app in the background.
- A controller's B button behaves the same as in the browser version, and never closes the app.
- Saves are stored on the device and survive closing the app. Uninstalling the app or clearing its data erases them.
- The "Save PNG" button is hidden in the app, because it relied on a feature of the Claude preview.

## App details you can change

- App name and ID: `capacitor.config.json` (`appName`, `appId`). If you change the ID, change it before you publish, since Play treats a new ID as a different app.
- Icon and splash screen: PNG files in `android/app/src/main/res/mipmap-*` and `drawable*/splash.png`.
- Orientation: `android:screenOrientation` in `android/app/src/main/AndroidManifest.xml`.
