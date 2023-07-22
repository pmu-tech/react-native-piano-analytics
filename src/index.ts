import { NativeModules, Platform } from 'react-native';

import type { EventName } from './types';
import type { PrivacyMode } from './types';

export * from './types';

const LINKING_ERROR =
  `The package '@pmu-tech/react-native-piano-analytics' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const PianoAnalyticsModule = isTurboModuleEnabled
  ? require('./RNPianoAnalytics').default
  : NativeModules.RNPianoAnalytics;

const PianoAnalytics = PianoAnalyticsModule
  ? PianoAnalyticsModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function sendEvent(
  eventName: EventName,
  params: Record<string, string>
) {
  return PianoAnalytics.sendEvent(eventName, params);
}

export function setUser(
  userId: string,
  category: string,
  enableStorage: boolean
) {
  return PianoAnalytics.setUser(userId, category, enableStorage);
}

export function deleteUser() {
  return PianoAnalytics.deleteUser();
}

export function privacySetMode(mode: PrivacyMode) {
  PianoAnalytics.privacySetMode(mode);
}

export function privacyGetMode(): Promise<PrivacyMode> {
  return PianoAnalytics.privacyGetMode();
}

export function setConfiguration(collectionName: string, siteId: number) {
  return PianoAnalytics.setConfiguration(collectionName, siteId);
}
