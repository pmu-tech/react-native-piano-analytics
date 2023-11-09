package com.reactnative.pianoanalytics

import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import com.reactnative.pianoanalytics.RNPianoAnalyticsSpec
import io.piano.analytics.Event;
import io.piano.analytics.Configuration;
import io.piano.analytics.PianoAnalytics;

@ReactModule(name = RNPianoAnalyticsModule.NAME)
class RNPianoAnalyticsModule internal constructor(context: ReactApplicationContext) :
  RNPianoAnalyticsSpec(context) {

  var piano = PianoAnalytics.getInstance(context);

  override fun getName(): String {
    return NAME
  }

  // CONFIG
  @ReactMethod
  override fun setConfiguration(collectDomain: String, siteId: Int) {
    var config = Configuration.Builder()
      .withCollectDomain(collectDomain)
      .withSite(siteId)
      .build()
    piano.setConfiguration(config)
  }

  // EVENTS METHODS
  @ReactMethod
  override fun sendEvent(eventName: String, params: ReadableMap) {
    piano.sendEvent(Event(eventName, params.toHashMap() as Map<String, String>))
  }

  // USER METHODS
  @ReactMethod
  override fun setUser(id: String, category: String?, enableStorage: Boolean) {
    piano.setUser(id, category, enableStorage)
  }

  @ReactMethod
  override fun deleteUser() {
    piano.deleteUser()
  }

  // PRIVACY MODE
  @ReactMethod
  override fun privacySetMode(mode: String) {
    piano.privacySetMode(mode)
  }

   @ReactMethod
   override fun privacyGetMode(promise: Promise) {
     piano.privacyGetMode { mode: String ->
       promise.resolve(mode)
     }
   }

   // VISITOR ID
   @ReactMethod
   override fun setVisitorId(visitorId: String) {
     piano.setVisitorId(visitorId)
   }

   @ReactMethod
   override fun getVisitorId(promise: Promise) {
     piano.getVisitorId { visitorId ->
        if (visitorId != null) {
          promise.resolve(visitorId)
        } else {
          promise.reject("Fail to retrieve visitor id");
        }
       }
   }

  // PRIVACY INCLUDE PROPERTY
  @ReactMethod
  override fun privacyIncludeProperty(property: String, privacyModes: Array<String>?, eventNames: Array<String>?) {
    piano.privacyIncludeProperty(property, privacyModes, eventNames)
  }

// // PRIVACY INCLUDE PROPERTIES
  @ReactMethod
  override fun privacyIncludeProperties(properties: Array<String>, privacyModes: Array<String>?, eventNames: Array<String>?){
  // piano.privacyIncludeProperties(new String[]{"article_category", "article_date"}); // Will be included in all modes
  // piano.privacyIncludeProperties(new String[]{"article_category", "article_date"}, new String[]{"exempt", "customMode"}); // Will be included in selected modes
  // piano.privacyIncludeProperties(new String[]{"article_category", "article_date"}, null, new String[]{"page.display", "click.*"}); // Will be included for all modes, but only selected events
    piano.privacyIncludeProperties(properties, privacyModes, eventNames)
  }

  companion object {
    const val NAME = "RNPianoAnalytics"
  }
}
