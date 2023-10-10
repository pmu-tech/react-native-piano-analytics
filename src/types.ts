export type PrivacyMode =
  | 'optin'
  | 'exempt'
  | 'no-storage'
  | 'no-consent'
  | 'optout';
export type ClickEvents =
  | 'click.action'
  | 'click.navigation'
  | 'click.download'
  | 'click.exit';
export type PageEvents = 'page.display';
export type EventName = ClickEvents | PageEvents;
