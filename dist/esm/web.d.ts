import { WebPlugin } from '@capacitor/core';
import type { BackgroundModePlugin, ISettings } from './definitions';
export declare class BackgroundModeWeb extends WebPlugin implements BackgroundModePlugin {
    enable(): Promise<void>;
    disable(): Promise<void>;
    getSettings(): Promise<{
        settings: ISettings;
    }>;
    setSettings(_settings: Partial<ISettings>): Promise<void>;
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
}
