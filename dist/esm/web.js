import { WebPlugin } from '@capacitor/core';
export class BackgroundModeWeb extends WebPlugin {
    async enable() {
        throw this.unimplemented('Not implemented on web.');
    }
    async disable() {
        throw this.unimplemented('Not implemented on web.');
    }
    async getSettings() {
        throw this.unimplemented('Not implemented on web.');
    }
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    async setSettings(_settings) {
        throw this.unimplemented('Not implemented on web.');
    }
    async checkForegroundPermission() {
        throw this.unimplemented('Not implemented on web.');
    }
    async requestForegroundPermission() {
        throw this.unimplemented('Not implemented on web.');
    }
    async isIgnoringBatteryOptimizations() {
        throw this.unimplemented('Not implemented on web.');
    }
    async disableBatteryOptimizations() {
        throw this.unimplemented('Not implemented on web.');
    }
    async enableWebViewOptimizations() {
        throw this.unimplemented('Not implemented on web.');
    }
    async disableWebViewOptimizations() {
        throw this.unimplemented('Not implemented on web.');
    }
    async moveToBackground() {
        throw this.unimplemented('Not implemented on web.');
    }
    async moveToForeground() {
        throw this.unimplemented('Not implemented on web.');
    }
    async isScreenOff() {
        throw this.unimplemented('Not implemented on web.');
    }
    async isEnabled() {
        throw this.unimplemented('Not implemented on web.');
    }
    async isActive() {
        throw this.unimplemented('Not implemented on web.');
    }
    async wakeUp() {
        throw this.unimplemented('Not implemented on web.');
    }
    async unlock() {
        throw this.unimplemented('Not implemented on web.');
    }
}
//# sourceMappingURL=web.js.map