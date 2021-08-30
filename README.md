# capacitor-plugin-background-mode

Capacitor plugin to perform infinite background execution

## Install

```bash
npm install capacitor-plugin-background-mode
npx cap sync
```

## API

<docgen-index>

* [`enable()`](#enable)
* [`disable()`](#disable)
* [`getSettings()`](#getsettings)
* [`setSettings(...)`](#setsettings)
* [`isIgnoringBatteryOptimizations()`](#isignoringbatteryoptimizations)
* [`disableBatteryOptimizations()`](#disablebatteryoptimizations)
* [`openBatteryOptimizationsSettings()`](#openbatteryoptimizationssettings)
* [`checkForegroundPermission()`](#checkforegroundpermission)
* [`requestForegroundPermission()`](#requestforegroundpermission)
* [`moveToBackground()`](#movetobackground)
* [`moveToForeground()`](#movetoforeground)
* [`isScreenOff()`](#isscreenoff)
* [`isEnabled()`](#isenabled)
* [`isActive()`](#isactive)
* [`wakeUp()`](#wakeup)
* [`unlock()`](#unlock)
* [`addListener(...)`](#addlistener)
* [`addListener(...)`](#addlistener)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### enable()

```typescript
enable() => any
```

**Returns:** <code>any</code>

--------------------


### disable()

```typescript
disable() => any
```

**Returns:** <code>any</code>

--------------------


### getSettings()

```typescript
getSettings() => any
```

**Returns:** <code>any</code>

--------------------


### setSettings(...)

```typescript
setSettings(settings: any) => any
```

| Param          | Type             |
| -------------- | ---------------- |
| **`settings`** | <code>any</code> |

**Returns:** <code>any</code>

--------------------


### isIgnoringBatteryOptimizations()

```typescript
isIgnoringBatteryOptimizations() => any
```

**Returns:** <code>any</code>

--------------------


### disableBatteryOptimizations()

```typescript
disableBatteryOptimizations() => any
```

**Returns:** <code>any</code>

--------------------


### openBatteryOptimizationsSettings()

```typescript
openBatteryOptimizationsSettings() => any
```

**Returns:** <code>any</code>

--------------------


### checkForegroundPermission()

```typescript
checkForegroundPermission() => any
```

**Returns:** <code>any</code>

--------------------


### requestForegroundPermission()

```typescript
requestForegroundPermission() => any
```

**Returns:** <code>any</code>

--------------------


### moveToBackground()

```typescript
moveToBackground() => any
```

**Returns:** <code>any</code>

--------------------


### moveToForeground()

```typescript
moveToForeground() => any
```

**Returns:** <code>any</code>

--------------------


### isScreenOff()

```typescript
isScreenOff() => any
```

**Returns:** <code>any</code>

--------------------


### isEnabled()

```typescript
isEnabled() => any
```

**Returns:** <code>any</code>

--------------------


### isActive()

```typescript
isActive() => any
```

**Returns:** <code>any</code>

--------------------


### wakeUp()

```typescript
wakeUp() => any
```

**Returns:** <code>any</code>

--------------------


### unlock()

```typescript
unlock() => any
```

**Returns:** <code>any</code>

--------------------


### addListener(...)

```typescript
addListener(eventName: 'appOnBackground', listenerFunc: BackgroundListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>"appOnBackground"</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>any</code>

--------------------


### addListener(...)

```typescript
addListener(eventName: 'appOnForeground', listenerFunc: ForegroundListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>"appOnForeground"</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>any</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => any
```

**Returns:** <code>any</code>

--------------------


### Interfaces


#### ISettings

| Prop                     | Type                                           |
| ------------------------ | ---------------------------------------------- |
| **`title`**              | <code>string</code>                            |
| **`text`**               | <code>string</code>                            |
| **`subText`**            | <code>string</code>                            |
| **`bigText`**            | <code>boolean</code>                           |
| **`resume`**             | <code>boolean</code>                           |
| **`silent`**             | <code>boolean</code>                           |
| **`hidden`**             | <code>boolean</code>                           |
| **`color`**              | <code>string</code>                            |
| **`icon`**               | <code>string</code>                            |
| **`channelName`**        | <code>string</code>                            |
| **`channelDescription`** | <code>string</code>                            |
| **`allowClose`**         | <code>boolean</code>                           |
| **`closeIcon`**          | <code>string</code>                            |
| **`closeTitle`**         | <code>string</code>                            |
| **`showWhen`**           | <code>boolean</code>                           |
| **`visibility`**         | <code>"public" \| "private" \| "secret"</code> |


#### PluginListenerHandle

| Prop         | Type                      |
| ------------ | ------------------------- |
| **`remove`** | <code>() =&gt; any</code> |

</docgen-api>
