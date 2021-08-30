package com.angeloraso.plugins.backgroundmode;

import static android.content.Context.POWER_SERVICE;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.M;
import static android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;
import static android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;
import static android.view.WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getcapacitor.JSObject;

public class BackgroundMode implements ForegroundService.CallBack{
    private Context context;
    private AppCompatActivity activity;
    private BackgroundModeSettings settings;
    private ForegroundService foregroundService;
    private Boolean mIsBound = false;
    private PowerManager.WakeLock wakeLock;
    @Nullable
    private BackgroundModeEventListener backgroundModeEventListener;
    static final String EVENT_APP_IN_BACKGROUND = "appInBackground";
    static final String EVENT_APP_IN_FOREGROUND = "appInForeground";

    interface BackgroundModeEventListener {
      void onBackgroundModeEvent(String event);
    }

    // Flag indicates if the app is in background or foreground
    private boolean mInBackground = false;

    // Flag indicates if the plugin is enabled or disabled
    private boolean mIsDisabled = true;

    BackgroundMode(final AppCompatActivity activity, final Context context) {
        this.activity = activity;
        this.context = context;
        this.settings = new BackgroundModeSettings();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            foregroundService = ((ForegroundService.LocalBinder)iBinder).getService();
            foregroundService.setCallBack(BackgroundMode.this);
            foregroundService.setSettings(settings);
        }

        public void onServiceDisconnected(ComponentName className) {
            foregroundService = null;
        }
    };

    public void onPause() {
        try {
            mInBackground = true;
            enable();
        } finally {
            clearKeyguardFlags();
        }
    }

    public void onStop() {
        clearKeyguardFlags();
    }

    public void onResume() {
        mInBackground = false;
        stopService();
    }

    public void onDestroy() {
        stopService();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void clearKeyguardFlags () {
        activity.runOnUiThread(() -> activity.getWindow().clearFlags(FLAG_DISMISS_KEYGUARD));
    }

    public void enable() {
        mIsDisabled = false;

        if (mInBackground) {
            startService();
        }
    }

    public void disable() {
        stopService();
        mIsDisabled = true;
    }

    private void startService() {
        if (mIsDisabled || mIsBound) {
            return;
        }

        Intent intent = new Intent(context, ForegroundService.class);

        try {
            context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            context.startForegroundService(intent);
            mIsBound = true;
        } catch (Exception e) {

        }

        mIsBound = true;
    }

    private void stopService() {
        if (!mIsBound) {
            return;
        }

        Intent intent = new Intent(context, ForegroundService.class);
        context.unbindService(mConnection);
        context.stopService(intent);
        mIsBound = false;
    }

    public JSObject getSettings() {
        JSObject settings = new JSObject();
        settings.put("title", this.settings.getTitle());
        settings.put("text", this.settings.getText());
        settings.put("subText", this.settings.getSubText());
        settings.put("bigText", this.settings.getBigText());
        settings.put("resume", this.settings.getResume());
        settings.put("silent", this.settings.getSilent());
        settings.put("hidden", this.settings.getHidden());
        settings.put("color", this.settings.getColor());
        settings.put("icon", this.settings.getIcon());
        settings.put("channelName", this.settings.getChannelName());
        settings.put("channelDescription", this.settings.getChannelDescription());
        settings.put("allowClose", this.settings.getAllowClose());
        settings.put("closeIcon", this.settings.getCloseIcon());
        settings.put("closeTitle", this.settings.getCloseTitle());
        settings.put("showWhen", this.settings.getShowWhen());
        settings.put("visibility", this.settings.getVisibility());
        return settings;
    }

    public void setSettings(BackgroundModeSettings settings) {
        this.settings = settings;
        if (mInBackground) {
          foregroundService.updateNotification(this.settings);
        }
    }

    public Boolean isIgnoringBatteryOptimizations() {
        if (SDK_INT < M) {
            return null;
        }

        String pkgName     = activity.getPackageName();
        PowerManager pm    = (PowerManager)activity.getSystemService(POWER_SERVICE);
        boolean isIgnoring = pm.isIgnoringBatteryOptimizations(pkgName);
        return isIgnoring;
    }

    @SuppressLint("BatteryLife")
    public void disableBatteryOptimizations() {
        Intent intent = new Intent();
        String pkgName = activity.getPackageName();
        PowerManager pm = (PowerManager) activity.getSystemService(POWER_SERVICE);

        if (SDK_INT < M)
            return;

        if (pm.isIgnoringBatteryOptimizations(pkgName))
            return;

        intent.setAction(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + pkgName));

        activity.startActivity(intent);

    }

    public void openBatteryOptimizationsSettings() {
        if (SDK_INT < M) {
            return;
        }

        Intent intent = new Intent(ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        activity.startActivity(intent);
    }

    public boolean checkForegroundPermission() {
        Boolean granted = false;
        if (SDK_INT >= M) {
            granted = Settings.canDrawOverlays(activity);
        } else {
            granted = true;
        }
        return granted;
    }

    public void requestForegroundPermission() {
        if (SDK_INT >= M) {
            String pkgName    = activity.getPackageName();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkgName));
            activity.startActivity(intent);
        }
    }

    public void moveToBackground() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);
        backgroundModeEventListener.onBackgroundModeEvent(EVENT_APP_IN_BACKGROUND);
    }

    public void moveToForeground() {
        Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        clearScreenAndKeyguardFlags();
        activity.startActivity(launchIntent);
        backgroundModeEventListener.onBackgroundModeEvent(EVENT_APP_IN_FOREGROUND);
    }

    public boolean isScreenOff() {
        PowerManager pm = (PowerManager) activity.getSystemService(POWER_SERVICE);
        return !pm.isInteractive();
    }

    public boolean isEnabled() {
        return !mIsDisabled;
    }

    public boolean isActive() {
        return mInBackground;
    }

    private void clearScreenAndKeyguardFlags() {
        activity.runOnUiThread(() -> activity.getWindow().clearFlags(FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | FLAG_SHOW_WHEN_LOCKED | FLAG_TURN_SCREEN_ON | FLAG_DISMISS_KEYGUARD));
    }


    public void wakeUp() {
        try {
            acquireWakeLock();
        } catch (Exception e) {
            releaseWakeLock();
        }
    }

    private void acquireWakeLock()
    {
        PowerManager pm = (PowerManager) activity.getSystemService(POWER_SERVICE);
        releaseWakeLock();

        if (!isScreenOff()) {
            return;
        }

        int level = PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP;

        wakeLock = pm.newWakeLock(level, "backgroundmode:wakelock");
        wakeLock.setReferenceCounted(false);
        wakeLock.acquire(1000);
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }



    public void unlock() {
        wakeUp();

        addScreenAndKeyguardFlags();
        Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        activity.startActivity(launchIntent);

    }

    private void addScreenAndKeyguardFlags() {
        activity.runOnUiThread(() -> activity.getWindow().addFlags(FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | FLAG_SHOW_WHEN_LOCKED | FLAG_TURN_SCREEN_ON | FLAG_DISMISS_KEYGUARD));
    }

    @Override
    public void onClick() {
        moveToForeground();
    }

    @Nullable
    public BackgroundModeEventListener getBackgroundModeEventListener() {
      return backgroundModeEventListener;
    }

    public void setBackgroundModeEventListener(@Nullable BackgroundModeEventListener backgroundModeEventListener) {
      this.backgroundModeEventListener = backgroundModeEventListener;
    }

}
