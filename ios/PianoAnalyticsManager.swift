//
//  PianoAnalyticsManager.swift
//  PianoAnalytics
//
//  Created by Kialungila Faustino  on 21/07/2023.
//  Copyright © 2023 Facebook. All rights reserved.
//

import AVFoundation
import Foundation
import React
import PianoAnalytics

@objc(PianoAnalyticsManager)
final class PianoAnalyticsManager: RCTViewManager {
  // pragma MARK: React Functions

  @objc
  final func setConfiguration(_ collectionName: String, siteId: NSNumber) {
    pa.setConfiguration(ConfigurationBuilder()
      .withCollectDomain(collectionName)
      .withSite(siteId.intValue)
      .build()
    )
  }

  @objc
  final func sendEvent(_ eventName: String, body: [String: String]) {
    pa.sendEvent(Event(eventName, data: body))
  }

  @objc
  final func setUser (_ id: String, category: String? , enableStorage: Bool) {
    pa.setUser(id, category: category, enableStorage: enableStorage)
  }

  @objc
  final func deleteUser() {
    pa.deleteUser()
  }

  @objc
  final func privacySetMode(_ mode: String) {
    pa.privacySetMode(mode)
  }

  @objc
  final func privacyGetMode(
    _ resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    pa.privacyGetMode { privacyMode in
      resolve(privacyMode)
    }
  }

}



