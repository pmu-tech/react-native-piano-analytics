# @pmu-tech/react-native-piano-analytics

React Native Piano Analytics

# Installation in managed Expo projects

For [managed](https://docs.expo.dev/archive/managed-vs-bare/) Expo projects, please follow the installation instructions in the [API documentation for the latest stable release](#api-documentation). If you follow the link and there is no documentation available then this library is not yet usable within managed projects &mdash; it is likely to be included in an upcoming Expo SDK release.

# Installation in bare React Native projects

For bare React Native projects, you must ensure that you have [installed and configured the `expo` package](https://docs.expo.dev/bare/installing-expo-modules/) before continuing.

### Add the package to your npm dependencies

```
npm install @pmu-tech/react-native-piano-analytics
```

### Configure for iOS

Run `npx pod-install` after installing the npm package.

# Usage

You may want to give a try? Run example application in `./example`.

## import react-native-piano-analytics

```javascript
import * as PianoAnalytics from '@pmu-tech/react-native-piano-analytics';
```

## Set a configuration (mandatory)

```javascript
function setConfiguration(collectionName: string, siteId: number): void
```

## Set Privacy consent (mandatory)

By default Piano Analytics will set **"no-consent"** mode.

```javascript
type PrivacyMode = 'optin' | 'exempt' | 'no-storage' | 'no-consent' | 'optout';

// SET:
function privacySetMode(mode: PrivacyMode): void

// GET:
function privacyGetMode(): PrivacyMode
```

## Set User

```javascript
// SET USER:
function setUser(userId: string, category: string, enableStorage: boolean): void

// REMOVE USER:
function deleteUser(): void
```

## Set Privacy id

```javascript
// SET PRIVACY ID:
function setVisitorId(visitorId: string): void

// GET PRIVACY ID:
function getVisitorId(): string
```

## Set Privacy Property

```javascript
// SET PRIVACY INCLUDE PROPERTY :
function privacyIncludeProperty(property: string, privacyModes?: string[], eventNames?: string[]): void

// SET PRIVACY INCLUDE PROPERTIES:
function privacyIncludeProperties(properties: [string], privacyModes?: string[], eventNames?: string[]): void
```

## Send events

```javascript
export type ClickEvents = "click.action" | "click.navigation" | "click.download" | "click.exit";

export type PageEvents = "page.display";

export type EventName = ClickEvents | PageEvents;
function sendEvent(eventName: EventName, params: Record<string, string>): void
```

# Contributing

Contributions are very welcome! Please refer to guidelines described in the [contributing guide](https://github.com/expo/expo#contributing).
