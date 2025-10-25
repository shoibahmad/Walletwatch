# Getting Started with WalletWatch

## 🚀 Quick Start (For Users)

### Install the App
1. Transfer `WalletWatch.apk` to your Android phone
2. Open the APK file
3. Allow installation from unknown sources if prompted
4. Tap "Install"
5. Open WalletWatch

### Use the App
1. **View Expenses**: See all your expenses on the main screen
2. **Add Expense**: Tap the purple + button
   - Enter amount (required)
   - Enter description (optional)
   - Select category
   - Tap "Add"
3. **Delete Expense**: Tap the trash icon on any expense
4. **View Total**: See your total expenses at the top

## 💻 For Developers

### Prerequisites
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK (API 24+)
- Firebase account

### Setup Project
1. Clone or download the project
2. Open in Android Studio
3. Wait for Gradle sync
4. Ensure `google-services.json` is in `app/` folder

### Build & Run
```bash
# Build debug APK
build-debug.bat

# Install to connected device
install.bat

# Build release APK
build-release.bat
```

### Project Structure
```
WalletWatch/
├── app/
│   ├── src/main/
│   │   ├── java/com/walletwatch/
│   │   │   ├── MainActivity.kt
│   │   │   ├── Expense.kt
│   │   │   ├── ExpenseAdapter.kt
│   │   │   ├── AddExpenseDialog.kt
│   │   │   └── WalletWatchApp.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── drawable/
│   │   │   └── values/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json
├── build.gradle
├── settings.gradle
└── README.md
```

## 🔧 Configuration

### Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new project or use existing
3. Add Android app with package: `com.walletwatch`
4. Download `google-services.json`
5. Place in `app/` directory
6. Enable Firestore Database
7. Set Firestore rules:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /expenses/{expense} {
      allow read, write: if true;
    }
  }
}
```

### Build Configuration
- **Debug**: No obfuscation, includes debug symbols
- **Release**: ProGuard enabled, optimized, smaller size

## 📱 Testing

### On Emulator
1. Open Android Studio
2. Create/start an emulator (API 24+)
3. Click Run button
4. App will install and launch

### On Physical Device
1. Enable Developer Options on your phone
2. Enable USB Debugging
3. Connect via USB
4. Run `install.bat`
5. App will install and launch

## 🎯 Features to Test

- [ ] Add expense with all fields
- [ ] Add expense with only amount
- [ ] Delete expense
- [ ] View total calculation
- [ ] Check empty state (when no expenses)
- [ ] Test offline mode (disable internet)
- [ ] Test sync (re-enable internet)
- [ ] Rotate screen (landscape mode)

## 🐛 Troubleshooting

### App won't install
- Check Android version (need 7.0+)
- Enable "Install from unknown sources"
- Uninstall old version first

### App crashes on open
- Check Firebase configuration
- Verify internet connection
- Check logcat for errors

### Data not syncing
- Check internet connection
- Verify Firebase rules
- Check Firestore in Firebase Console

### Build fails
- Run `gradlew clean`
- Check `google-services.json` exists
- Verify Android SDK is installed

## 📚 Learn More

- [Android Documentation](https://developer.android.com)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Material Design](https://material.io/design)

## ✅ Checklist

### Before Building
- [ ] `google-services.json` in place
- [ ] Firebase Firestore enabled
- [ ] Android SDK installed
- [ ] Gradle synced successfully

### Before Distribution
- [ ] Test on multiple devices
- [ ] Test offline functionality
- [ ] Verify all features work
- [ ] Check Firebase rules
- [ ] Sign APK for production

## 🎉 You're Ready!

The app is fully functional and ready to use. Install `WalletWatch.apk` and start tracking your expenses!
