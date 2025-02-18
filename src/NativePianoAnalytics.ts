import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  sendEvent(
    eventName: string,
    params: { [key: string]: string | number | boolean | string[] }
  ): Promise<void>;

  setUser(
    userId: string,
    category: string,
    enableStorage: boolean
  ): Promise<void>;

  deleteUser(): Promise<void>;

  privacySetMode(mode: string): Promise<void>;

  privacyGetMode(): Promise<string>;

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
