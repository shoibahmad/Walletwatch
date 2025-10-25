@echo off
echo ========================================
echo Building WalletWatch - Debug Version
echo ========================================
echo.

call gradlew assembleDebug

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Build Successful!
    echo ========================================
    echo.
    echo APK Location: app\build\outputs\apk\debug\app-debug.apk
    echo.
    copy /Y app\build\outputs\apk\debug\app-debug.apk WalletWatch-debug.apk
    echo Copied to: WalletWatch-debug.apk
    echo.
) else (
    echo.
    echo Build Failed!
    echo.
)

pause
