# Video Automation Project (TV)

Unified automation framework for **Android TV**, **Apple TV**, **Roku**, and **Samsung Tizen**.

## Architecture
*   **Java + Maven + TestNG**: Standard stack.
*   **Appium**: Used for Android TV and Apple TV.
*   **OkHttp (ECP)**: Used for Roku control via REST API.
*   **Unified RemoteController**: Abstracts D-Pad navigation (`pressRight`, `pressSelect`) across disparate platforms.

## Key Features
*   **Multi-Platform Support**: Android TV, Apple TV, Roku, Tizen, Chromecast.
*   **Auto-Healing**: Infrastructure ready via `HealingHelper`.
*   **Chromecast Support**: Debugger Protocol integration on port 9222.
*   **Allure Reporting**: Integrated for visual test results.

## Prerequisites
1.  **Android TV**: Android Emulator or Device with USB debugging.
2.  **Apple TV**: Xcode Simulator or Device + Appium setup.
3.  **Roku**: Enable "Developer Mode" on Roku device (Home -> 3xUp, 2xUp, R, L, R, L, R). Get IP address.
4.  **Tizen**: Tizen Studio + Appium Tizen Driver.
5.  **Chromecast**:
    *   Enable Developer Mode on Chromecast.
    *   Start your Receiver App (CAF Receiver) on the Chromecast.
    *   Get IP address of Chromecast.
    *   Ensure port 9222 (or configured port) is accessible.

## Configuration
Edit `src/test/resources/config.properties`:
```properties
# Select Platform: androidtv, appletv, roku, tizen, chromecast
platform=chromecast

# Chromecast Specific
chromecastIp=192.168.1.55
chromecastPort=9222
```

## Running Tests
```bash
mvn clean test -DplatformName=androidtv
mvn clean test -DplatformName=chromecast
```
