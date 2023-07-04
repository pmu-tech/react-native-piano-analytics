import RNPianoAnalyticsModule from './RNPianoAnalyticsModule';
export function sendEvent(eventName, params) {
    RNPianoAnalyticsModule.sendEvent(eventName, params);
}
export function setUser(userId, category, enableStorage) {
    RNPianoAnalyticsModule.setUser(userId, category, enableStorage);
}
export function deleteUser() {
    RNPianoAnalyticsModule.deleteUser();
}
export function privacySetMode(mode) {
    RNPianoAnalyticsModule.privacySetMode(mode);
}
export function privacyGetMode() {
    return RNPianoAnalyticsModule.privacyGetMode();
}
export function setConfiguration(collectionName, siteId) {
    RNPianoAnalyticsModule.setConfiguration(collectionName, siteId);
}
//# sourceMappingURL=index.js.map