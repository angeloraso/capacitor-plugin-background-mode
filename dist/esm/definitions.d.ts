import type { PluginListenerHandle } from "@capacitor/core";
export interface ISettings {
    title: string;
    text: string;
    subText: string;
    bigText: boolean;
    resume: boolean;
    silent: boolean;
    hidden: boolean;
    color: string;
    icon: string;
    channelName: string;
    channelDescription: string;
    allowClose: boolean;
    closeIcon: string;
    closeTitle: string;
    showWhen: boolean;
    visibility: 'public' | 'private' | 'secret';
}
export interface BackgroundModePlugin {
    enable(): Promise<void>;
    disable(): Promise<void>;
    getSettings(): Promise<{
        settings: ISettings;
    }>;
    setSettings(settings: Partial<ISettings>): Promise<void>;
    isIgnoringBatteryOptimizations(): Promise<boolean>;
    disableBatteryOptimizations(): Promise<void>;
    openBatteryOptimizationsSettings(): Promise<void>;
    checkForegroundPermission(): Promise<boolean>;
    requestForegroundPermission(): Promise<void>;
    moveToBackground(): Promise<void>;
    moveToForeground(): Promise<void>;
    isScreenOff(): Promise<boolean>;
    isEnabled(): Promise<boolean>;
    isActive(): Promise<boolean>;
    wakeUp(): Promise<void>;
    unlock(): Promise<void>;
    addListener(eventName: 'appInBackground', listenerFunc: () => void): Promise<PluginListenerHandle> & PluginListenerHandle;
    addListener(eventName: 'appInForeground', listenerFunc: () => void): Promise<PluginListenerHandle> & PluginListenerHandle;
    removeAllListeners(): Promise<void>;
}
