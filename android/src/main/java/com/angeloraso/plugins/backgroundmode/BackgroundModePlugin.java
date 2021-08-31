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
        backgroundMode.setSettings(call);
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
