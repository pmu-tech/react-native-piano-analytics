package com.reactnative.pianoanalytics

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.*
import io.piano.android.analytics.PrivacyModesStorage
import io.piano.android.analytics.model.PrivacyMode

abstract class RNPianoAnalyticsSpec internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  abstract fun sendEvent(eventName: String, params: ReadableMap)

  abstract fun setUser(id: String, category: String?, enableStorage: Boolean)

  abstract fun privacySetMode(mode: String)

  abstract fun privacyGetMode(promise: Promise)
  
  abstract fun setVisitorId(visitorId: String)

  abstract fun getVisitorId(promise: Promise)

  abstract fun setConfiguration(collectDomain: String, siteId: Int)

  abstract fun privacyIncludeProperty(property: String, privacyModesArray: ReadableArray?, eventNames: ReadableArray?)

  abstract fun privacyIncludeProperties(properties: ReadableArray, privacyModesArray: ReadableArray?, eventNames: ReadableArray?)
}
