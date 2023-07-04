import RNPianoAnalyticsModule from './RNPianoAnalyticsModule';

export type PrivacyMode = 'optin' | 'exempt' | 'no-storage' | 'no-consent' | 'optout';

export function sendEvent(eventName: string, params: Record<string, string>) {
  RNPianoAnalyticsModule.sendEvent(eventName, params);
}

export function setUser(userId: string, category?: string, enableStorage?: boolean) {
  RNPianoAnalyticsModule.setUser(userId, category, enableStorage);
}

export function deleteUser() {
  RNPianoAnalyticsModule.deleteUser();
}

export function privacySetMode(mode: PrivacyMode) {
  RNPianoAnalyticsModule.privacySetMode(mode);
}

export function privacyGetMode(): PrivacyMode {
  return RNPianoAnalyticsModule.privacyGetMode();
}

export function setConfiguration(collectionName: string, siteId: number) {
  RNPianoAnalyticsModule.setConfiguration(collectionName, siteId);
}
