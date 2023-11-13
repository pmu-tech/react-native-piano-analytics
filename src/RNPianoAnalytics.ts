import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

import type { EventName, PrivacyMode } from './types';

export interface Spec extends TurboModule {
  sendEvent(
    eventName: EventName,
    params: Record<string, string>
  ): Promise<void>;

  setUser(
    userId: string,
    category: string,
    enableStorage: boolean
  ): Promise<void>;

  deleteUser(): Promise<void>;

  privacySetMode(mode: PrivacyMode): Promise<void>;

  privacyGetMode(): Promise<PrivacyMode>;

  setVisitorId(visitorId: string): Promise<void>;

  getVisitorId(): Promise<string>;

  setConfiguration(collectionName: string, siteId: number): Promise<void>;

  privacyIncludeProperty(
    property: string,
    privacyModes?: string[],
    eventNames?: string[]
  ): void;

  privacyIncludeProperties(
    properties: string[],
    privacyModes?: string[],
    eventNames?: string[]
  ): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RNPianoAnalytics');
