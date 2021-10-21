import { WebPlugin } from '@capacitor/core';
import type { BackgroundModePlugin, ISettings } from './definitions';
export declare class BackgroundModeWeb extends WebPlugin implements BackgroundModePlugin {
    enable(): Promise<void>;
    disable(): Promise<void>;
    getSettings(): Promise<{
        settings: ISettings;
    }>;
    setSettings(_settings: Partial<ISettings>): Promise<void>;
    checkForegroundPermission(): Promise<{
        enabled: boolean;
    }>;
    requestForegroundPermission(): Promise<void>;
    isIgnoringBatteryOptimizations(): Promise<{
        isIgnoring: boolean;
    }>;
    disableBatteryOptimizations(): Promise<void>;
    enableWebViewOptimizations(): Promise<void>;
    disableWebViewOptimizations(): Promise<void>;
    moveToBackground(): Promise<void>;
    moveToForeground(): Promise<void>;
    isScreenOff(): Promise<{
        isScreenOff: boolean;
    }>;
    isEnabled(): Promise<{
        enabled: boolean;
    }>;
    isActive(): Promise<{
        activated: boolean;
    }>;
    wakeUp(): Promise<void>;
    unlock(): Promise<void>;
}
