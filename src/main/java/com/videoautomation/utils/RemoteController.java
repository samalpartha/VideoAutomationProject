package com.videoautomation.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import okhttp3.*;

import java.io.IOException;

public class RemoteController {

    private AppiumDriver driver;
    private String rokuIp;
    private String platform;
    private final OkHttpClient client = new OkHttpClient();

    public RemoteController(AppiumDriver driver, String platform, String rokuIp) {
        this.driver = driver;
        this.platform = platform;
        this.rokuIp = rokuIp;
    }

    public void pressSelect() {
        if (platform.equalsIgnoreCase("androidtv")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DPAD_CENTER)); // or ENTER
        } else if (platform.equalsIgnoreCase("appletv")) {
            // Apple TV uses specific remote commands or element interactions
            // driver.executeScript("mobile: pressButton", ImmutableMap.of("name",
            // "select"));
        } else if (platform.equalsIgnoreCase("roku")) {
            sendRokuCommand("Select");
        }
    }

    public void pressRight() {
        if (platform.equalsIgnoreCase("androidtv")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
        } else if (platform.equalsIgnoreCase("roku")) {
            sendRokuCommand("Right");
        }
    }

    public void pressLeft() {
        if (platform.equalsIgnoreCase("androidtv")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
        } else if (platform.equalsIgnoreCase("roku")) {
            sendRokuCommand("Left");
        }
    }

    private void sendRokuCommand(String key) {
        // ECP POST: http://<ip>:8060/keypress/<key>
        String url = "http://" + rokuIp + ":8060/keypress/" + key;
        Request request = new Request.Builder().url(url).post(RequestBody.create(new byte[0])).build();
        try (Response response = client.newCall(request).execute()) {
            // log response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
