export type PrivacyMode = 'optin' | 'exempt' | 'no-storage' | 'no-consent' | 'optout';
export declare function sendEvent(eventName: string, params: Record<string, string>): void;
export declare function setUser(userId: string, category?: string, enableStorage?: boolean): void;
export declare function deleteUser(): void;
export declare function privacySetMode(mode: PrivacyMode): void;
export declare function privacyGetMode(): PrivacyMode;
export declare function setConfiguration(collectionName: string, siteId: number): void;
//# sourceMappingURL=index.d.ts.map