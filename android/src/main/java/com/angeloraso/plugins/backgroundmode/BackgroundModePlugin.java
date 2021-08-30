package com.angeloraso.plugins.backgroundmode;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "BackgroundMode")
public class BackgroundModePlugin extends Plugin {

    private BackgroundMode backgroundMode;

    public void load() {
        AppCompatActivity activity = getActivity();
        Context context = getContext();
        backgroundMode = new BackgroundMode(activity, context);
        backgroundMode.setBackgroundModeEventListener(this::onBackgroundModeEvent);
    }

    void onBackgroundModeEvent(String event) {
      JSObject jsObject = new JSObject();
      switch (event) {
        case BackgroundMode.EVENT_APP_IN_BACKGROUND:
        case BackgroundMode.EVENT_APP_IN_FOREGROUND:
          bridge.triggerWindowJSEvent(event);
          notifyListeners(event, jsObject);
          break;
      }
    }

    @PluginMethod
    public void enable(PluginCall call) {
        if (getActivity().isFinishing()) {
            String appFinishingMsg = getActivity().getString(R.string.app_finishing);
            call.reject(appFinishingMsg);
            return;
        }

        backgroundMode.enable();

        call.resolve();
    }

    /**
     * Called when the system is about to start resuming a previous activity.
     */
    @Override
    protected void handleOnPause() {
        backgroundMode.onPause();
    }

    /**
     * Called when the activity is no longer visible to the user.
     */
    @Override
    public void handleOnStop () {
        backgroundMode.onStop();
    }

    /**
     * Called when the activity will start interacting with the user.
     */
    @Override
    public void handleOnResume () {
        backgroundMode.onResume();
    }

    /**
     * Called when the activity will be destroyed.
     */
    @Override
    public void handleOnDestroy() {
        backgroundMode.onDestroy();
    }

    @PluginMethod
    public void disable(PluginCall call) {
        if (getActivity().isFinishing()) {
            String appFinishingMsg = getActivity().getString(R.string.app_finishing);
            call.reject(appFinishingMsg);
            return;
        }

        backgroundMode.disable();
        call.resolve();
    }

    @PluginMethod
    public void getSettings(PluginCall call) {
        JSObject settings = backgroundMode.getSettings();
        call.resolve(settings);
    }


    @PluginMethod
    public void setSettings(PluginCall call) {
        BackgroundModeSettings settings = new BackgroundModeSettings();

        if (call.hasOption("title")) {
            settings.setTitle((call.getString("title")));
        }

        if (call.hasOption("text")) {
            settings.setText((call.getString("text")));
        }

        if (call.hasOption("subText")) {
            settings.setSubText((call.getString("subText")));
        }

        if (call.hasOption("bigText")) {
            settings.setBigText((call.getBoolean("bigText")));
        }

        if (call.hasOption("resume")) {
            settings.setResume((call.getBoolean("resume")));
        }

        if (call.hasOption("silent")) {
            settings.setSilent((call.getBoolean("silent")));
        }

        if (call.hasOption("hidden")) {
            settings.setHidden((call.getBoolean("hidden")));
        }

        if (call.hasOption("color")) {
            settings.setColor((call.getString("color")));
        }

        if (call.hasOption("icon")) {
            settings.setColor((call.getString("icon")));
        }

        if (call.hasOption("channelName")) {
            settings.setChannelName((call.getString("channelName")));
        }

        if (call.hasOption("channelDescription")) {
            settings.setChannelDescription((call.getString("channelDescription")));
        }

        if (call.hasOption("allowClose")) {
            settings.setAllowClose((call.getBoolean("allowClose")));
        }

        if (call.hasOption("closeIcon")) {
            settings.setCloseIcon((call.getString("closeIcon")));
        }

        if (call.hasOption("closeTitle")) {
            settings.setCloseTitle((call.getString("closeTitle")));
        }

        if (call.hasOption("showWhen")) {
            settings.setShowWhen((call.getBoolean("showWhen")));
        }

        if (call.hasOption("visibility")) {
            settings.setVisibility((call.getString("visibility")));
        }

        backgroundMode.setSettings(settings);
        call.resolve();
    }

    @PluginMethod
    public void isIgnoringBatteryOptimizations(PluginCall call) {
        Boolean isIgnoring = backgroundMode.isIgnoringBatteryOptimizations();
        JSObject res = new JSObject();
        res.put("isIgnoring", isIgnoring == null ? JSObject.NULL : isIgnoring);
        call.resolve(res);
    }

    @PluginMethod
    public void disableBatteryOptimizations(PluginCall call) {
        backgroundMode.disableBatteryOptimizations();
        call.resolve();
    }

    @PluginMethod
    public void openBatteryOptimizationsSettings(PluginCall call) {
        backgroundMode.openBatteryOptimizationsSettings();
        call.resolve();
    }

    @PluginMethod
    public void checkForegroundPermission(PluginCall call) {
        Boolean foregroundPermission = backgroundMode.checkForegroundPermission();
        JSObject res = new JSObject();
        res.put("enabled", foregroundPermission == null ? JSObject.NULL : foregroundPermission);
        call.resolve(res);
    }

    @PluginMethod
    public void requestForegroundPermission(PluginCall call) {
        backgroundMode.requestForegroundPermission();
        call.resolve();
    }

    @PluginMethod
    public void moveToBackground(PluginCall call) {
        backgroundMode.moveToBackground();
        call.resolve();
    }

    @PluginMethod
    public void moveToForeground(PluginCall call) {
        backgroundMode.moveToForeground();
        call.resolve();
    }

    @PluginMethod
    public void isScreenOff(PluginCall call) {
        Boolean isScreenOff = backgroundMode.isScreenOff();
        JSObject res = new JSObject();
        res.put("isScreenOff", isScreenOff == null ? JSObject.NULL : isScreenOff);
        call.resolve(res);
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        Boolean isEnabled = backgroundMode.isEnabled();
        JSObject res = new JSObject();
        res.put("isEnabled", isEnabled == null ? JSObject.NULL : isEnabled);
        call.resolve(res);
    }

    @PluginMethod
    public void isActive(PluginCall call) {
        Boolean isActive = backgroundMode.isActive();
        JSObject res = new JSObject();
        res.put("isActive", isActive == null ? JSObject.NULL : isActive);
        call.resolve(res);
    }

    @PluginMethod
    public void wakeUp(PluginCall call) {
        backgroundMode.wakeUp();
        call.resolve();
    }

    @PluginMethod
    public void unlock(PluginCall call) {
        backgroundMode.unlock();
        call.resolve();
    }

}
