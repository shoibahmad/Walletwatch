@echo off
echo ========================================
echo WalletWatch - Install to Device
echo ========================================
echo.

echo Checking for connected devices...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" devices
echo.

echo Building debug APK...
call gradlew assembleDebug

if %ERRORLEVEL% NEQ 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo.
echo Installing...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" install -r app\build\outputs\apk\debug\app-debug.apk

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Installation successful!
    echo Starting app...
    "%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" shell am start -n com.walletwatch/.MainActivity
    echo.
    echo App launched!
) else (
    echo.
    echo Installation failed!
)

echo.
pause
