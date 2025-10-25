@echo off
echo ========================================
echo Building WalletWatch - Release Version
echo ========================================
echo.

call gradlew assembleRelease

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Build Successful!
    echo ========================================
    echo.
    echo APK Location: app\build\outputs\apk\release\app-release-unsigned.apk
    echo.
    copy /Y app\build\outputs\apk\release\app-release-unsigned.apk WalletWatch-release.apk
    echo Copied to: WalletWatch-release.apk
    echo.
    echo NOTE: This APK is unsigned. For production, you need to sign it.
    echo.
) else (
    echo.
    echo Build Failed!
    echo.
)

pause
