'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const BackgroundMode = core.registerPlugin('BackgroundMode', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.BackgroundModeWeb()),
});

class BackgroundModeWeb extends core.WebPlugin {
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

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    BackgroundModeWeb: BackgroundModeWeb
});

exports.BackgroundMode = BackgroundMode;
//# sourceMappingURL=plugin.cjs.js.map
