package com.reactnative.pianoanalytics

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.*

abstract class RNPianoAnalyticsSpec internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  abstract fun sendEvent(eventName: String, params: ReadableMap)

  abstract fun setUser(id: String, category: String?, enableStorage: Boolean)

  abstract fun deleteUser()

  abstract fun privacySetMode(mode: String)

  abstract fun privacyGetMode(promise: Promise)

  abstract fun setConfiguration(collectDomain: String, siteId: Int)
}
