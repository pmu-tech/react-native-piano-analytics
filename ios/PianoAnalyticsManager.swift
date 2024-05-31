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
  final func sendEvent(_ eventName: String, body: [String: Any]) {
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
  
  @objc
  final func setVisitorId(_ visitorId: String) {
    pa.setVisitorId(visitorId)
  }

  @objc
  final func getVisitorId(
    _ resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    pa.getVisitorId { visitorId in
      resolve(visitorId)
    }
  }

  @objc
  final func privacyIncludeProperty(_ property: String, privacyModes: [String]? = ["*"], eventNames: [String]? = ["*"]) {
    pa.privacyIncludeProperty(property, privacyModes: privacyModes, eventNames: eventNames)
  }

  @objc
  final func privacyIncludeProperties(_ properties: [String], privacyModes: [String]? = ["*"], eventNames: [String]? = ["*"]) {
    pa.privacyIncludeProperties(properties, privacyModes: privacyModes, eventNames: eventNames)
  }
}



