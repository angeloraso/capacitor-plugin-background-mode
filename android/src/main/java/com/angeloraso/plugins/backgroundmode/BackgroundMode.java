package com.angeloraso.plugins.backgroundmode;
import androidx.activity.OnBackPressedCallback;
import static android.content.Context.POWER_SERVICE;
import static android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;
import static android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;
import static android.view.WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BackgroundMode {
    private final Context mContext;
    private final AppCompatActivity mActivity;
    private BackgroundModeSettings mSettings;
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
        mActivity = activity;
        mContext = context;
        mSettings = new BackgroundModeSettings();
        // Override back button functionality
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                moveToBackground();
            }
        };
        mActivity.getOnBackPressedDispatcher().addCallback(mActivity, callback);
    }

    final private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            foregroundService = ((ForegroundService.LocalBinder)iBinder).getService();
            foregroundService.updateNotification(mSettings);
            mIsBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            foregroundService = null;
            mIsBound = false;
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
        backgroundModeEventListener.onBackgroundModeEvent(EVENT_APP_IN_FOREGROUND);
    }

    public void onDestroy() {
        stopService();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void clearKeyguardFlags () {
        mActivity.runOnUiThread(() -> mActivity.setShowWhenLocked(false));
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

        Intent intent = new Intent(mContext, ForegroundService.class);

        try {
            mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            mContext.startForegroundService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopService() {
        if (!mIsBound) {
            return;
        }

        Intent intent = new Intent(mContext, ForegroundService.class);
        mContext.unbindService(mConnection);
        mContext.stopService(intent);
        mIsBound = false;
    }

    public BackgroundModeSettings getSettings() {
        return mSettings;
    }

    public void setSettings(BackgroundModeSettings settings) {
        mSettings = settings;
      if (mInBackground && mIsBound) {
        foregroundService.updateNotification(settings);
      }
    }

    public Boolean isIgnoringBatteryOptimizations() {
        String pkgName = mActivity.getPackageName();
        PowerManager pm = (PowerManager) mActivity.getSystemService(POWER_SERVICE);
        return pm.isIgnoringBatteryOptimizations(pkgName);
    }

    @SuppressLint("BatteryLife")
    public void disableBatteryOptimizations() {
        Intent intent = new Intent();
        String pkgName = mActivity.getPackageName();
        PowerManager pm = (PowerManager) mActivity.getSystemService(POWER_SERVICE);

        if (pm.isIgnoringBatteryOptimizations(pkgName))
            return;

        intent.setAction(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + pkgName));

        mActivity.startActivity(intent);
    }

    public void openBatteryOptimizationsSettings() {
        Intent intent = new Intent(ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        mActivity.startActivity(intent);
    }

    public boolean checkForegroundPermission() {
        return Settings.canDrawOverlays(mActivity);
    }

    public void requestForegroundPermission() {
        String pkgName = mActivity.getPackageName();
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkgName));
        mActivity.startActivity(intent);
    }

    public void moveToBackground() {
        if (mInBackground) {
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            mActivity.startActivity(intent);
            backgroundModeEventListener.onBackgroundModeEvent(EVENT_APP_IN_BACKGROUND);
            mInBackground = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveToForeground() {
        if (!mInBackground) {
            return;
        }

        try {
            Intent launchIntent = mActivity.getPackageManager().getLaunchIntentForPackage(mActivity.getPackageName());
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            clearScreenAndKeyguardFlags();
            mActivity.startActivity(launchIntent);
            mInBackground = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isScreenOff() {
        PowerManager pm = (PowerManager) mActivity.getSystemService(POWER_SERVICE);
        return !pm.isInteractive();
    }

    public boolean isEnabled() {
        return !mIsDisabled;
    }

    public boolean isActive() {
        return mInBackground;
    }

    private void clearScreenAndKeyguardFlags() {
        mActivity.runOnUiThread(() -> {
            mActivity.setShowWhenLocked(false);
            mActivity.setTurnScreenOn(false);
            mActivity.getWindow().clearFlags(FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        });
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
        PowerManager pm = (PowerManager) mActivity.getSystemService(POWER_SERVICE);
        releaseWakeLock();

        if (!isScreenOff()) {
            return;
        }

        int level = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | PowerManager.ACQUIRE_CAUSES_WAKEUP;
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
        openApp();

    }

    private void addScreenAndKeyguardFlags() {
        mActivity.runOnUiThread(() -> {
            mActivity.setShowWhenLocked(true);
            mActivity.setTurnScreenOn(true);
            mActivity.getWindow().addFlags(FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        });
    }

    public void setBackgroundModeEventListener(@Nullable BackgroundModeEventListener backgroundModeEventListener) {
      this.backgroundModeEventListener = backgroundModeEventListener;
    }

    private void openApp() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        mActivity.startActivity(intent);
    }

}
