# Can You Spot It?

An offline Android app that helps people practice recognizing scam messages and check suspicious texts on their own phone.

## Authors
Nilambari Jaywant Nerkar and Asish Chellamani

## Course
Mobile Systems, 2026, FH Schmalkalden

## Features
- **Scanner**: paste a message and get an instant Safe, Caution, or Likely Scam verdict, with an explanation of what to look for.
- **Learn to Spot It**: swipe through example messages to practice telling real messages apart from scams, with three difficulty levels.
- **Emotional Response**: after a scan, pick how the message made you feel, and get a short reflection that responds to both the verdict and the feeling you picked.
- **Light sensor**: the app switches between light and dark theme automatically based on the room's ambient light.
- **GPS region detection**: with your permission, detects a general region (India, Germany, or worldwide) so the practice examples match scams common in your area. If a location can't be found the first time, it falls back to worldwide examples and quietly retries from the Home screen on later launches, without asking for permission again.

## Tech stack
- Kotlin only
- Room for local storage
- Material Design 3 components
- Android API 34 to 36 (minSdk 34, targetSdk 36, compileSdk 36)
- Fully offline: no network calls, everything runs and stays on the device

## Build and run
1. Clone the repository.
2. Open the project folder in Android Studio.
3. Let Gradle sync.
4. Run on a device or emulator with API 34 or higher.

Or from the command line:
```
./gradlew assembleDebug
./gradlew installDebug
```

## Download
A signed release APK is available on the [v1.0 Release](../../releases/tag/v1.0) page.

## Privacy
- The app asks for consent the first time you open it.
- Everything stays on the device. Nothing is sent anywhere else.
- You can turn history saving off, export your data, or delete it at any time in Settings.

## Data sources
The practice examples in Learn to Spot It were compiled and adapted from
  several open-source spam and phishing message datasets. Phone numbers, links and other identifying details have been masked. Detailed source attribution will
  be added in a future update.
