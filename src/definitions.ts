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
  disableWebViewOptimization: boolean;
  visibility: 'public' | 'private' | 'secret';
}
export interface BackgroundModePlugin {
  enable(): Promise<void>;
  disable(): Promise<void>;
  getSettings(): Promise<{settings: ISettings}>;
  setSettings(settings: Partial<ISettings>): Promise<void>;
  checkForegroundPermission(): Promise<{enabled: boolean}>;
  requestForegroundPermission(): Promise<void>;
  isIgnoringBatteryOptimizations(): Promise<{isIgnoring: boolean}>;
  disableBatteryOptimizations(): Promise<void>;
  enableWebViewOptimizations(): Promise<void>;
  disableWebViewOptimizations(): Promise<void>;
  moveToBackground(): Promise<void>;
  moveToForeground(): Promise<void>;
  isScreenOff(): Promise<{isScreenOff: boolean}>;
  isEnabled(): Promise<{enabled: boolean}>;
  isActive(): Promise<{activated: boolean}>;
  wakeUp(): Promise<void>;
  unlock(): Promise<void>;

  addListener(
    eventName: 'appInBackground',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  addListener(
    eventName: 'appInForeground',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
  removeAllListeners(): Promise<void>;
}
