package com.reactnative.pianoanalytics

import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import io.piano.android.analytics.Configuration
import io.piano.android.analytics.PianoAnalytics
import io.piano.android.analytics.PrivacyModesStorage
import io.piano.android.analytics.model.Event
import io.piano.android.analytics.model.PrivacyMode
import io.piano.android.analytics.model.Property
import io.piano.android.analytics.model.PropertyName
import io.piano.android.analytics.model.User
import io.piano.android.analytics.model.VisitorIDType

@ReactModule(name = RNPianoAnalyticsModule.NAME)
class RNPianoAnalyticsModule internal constructor(var context: ReactApplicationContext) :
  RNPianoAnalyticsSpec(context) {

  override fun getName(): String {
    return NAME
  }

  // CONFIG
  @ReactMethod
  override fun setConfiguration(collectDomain: String, siteId: Int) {
    val config = Configuration.Builder(
      collectDomain = collectDomain,
      site = siteId,
      visitorIDType = VisitorIDType.CUSTOM
    ).build()
    PianoAnalytics.init(context, config)
  }

  // EVENTS METHODS
  @ReactMethod
  override fun sendEvent(eventName: String, params: ReadableMap) {
    val properties = mutableListOf<Property>()
    val iterator = params.keySetIterator()
    while (iterator.hasNextKey()) {
      val key = iterator.nextKey()
      when (params.getType(key)) {
        ReadableType.String -> properties.add(Property(PropertyName(key), params.getString(key).toString()))
        ReadableType.Boolean -> properties.add(Property(PropertyName(key), params.getBoolean(key)))
        ReadableType.Number -> properties.add(Property(PropertyName(key), params.getDouble(key)))
        ReadableType.Array -> {
          val array = params.getArray(key)
          val stringArray = array?.toArrayList()?.map { it.toString() }?.toTypedArray()
          properties.add(Property(PropertyName(key), stringArray ?: emptyArray()))
        }
        else -> {}
      }
    }
    val event = Event.Builder(eventName).properties(properties).build()
    PianoAnalytics.getInstance().sendEvents(event)
  }

  // USER METHODS
  @ReactMethod
  override fun setUser(id: String, category: String?, enableStorage: Boolean) {
    val userStorage = PianoAnalytics.getInstance().userStorage
    userStorage.currentUser = User(id, category, enableStorage)
  }

  // PRIVACY MODE
  @ReactMethod
  override fun privacySetMode(mode: String) {
    val privacyMode = getPrivacyMode(mode) ?: return
    PianoAnalytics.getInstance().privacyModesStorage.currentMode = privacyMode
  }

  @ReactMethod
  override fun privacyGetMode(promise: Promise) {
    val currentMode = PianoAnalytics.getInstance().privacyModesStorage.currentMode
    promise.resolve(currentMode)
  }

  // VISITOR ID
  @ReactMethod
  override fun getVisitorId(promise: Promise) {
    val visitorId = PianoAnalytics.getInstance().customVisitorId
    if (visitorId != null) {
      promise.resolve(visitorId)
    } else {
      promise.reject("Fail to retrieve custom visitor id")
    }
  }

  @ReactMethod
  override fun setVisitorId(visitorId: String) {
    PianoAnalytics.getInstance().customVisitorId = visitorId
  }

  // PRIVACY INCLUDE PROPERTY
  private fun includePropertiesInPrivacyModes(propertiesList: List<String>, privacyModesList: List<String>, eventNamesList: List<String>) {
    for (privacyMode in privacyModesList) {
      val mode = getPrivacyMode(privacyMode) ?: continue

      for (eventName in eventNamesList) {
        val eventKey = if (eventName == "any") Event.ANY else eventName
        for (property in propertiesList) {
          mode.allowedPropertyKeys[eventKey]?.add(PropertyName(property))
        }
      }
    }
  }

  @ReactMethod
  override fun privacyIncludeProperties(properties: ReadableArray, privacyModes: ReadableArray?, eventNames: ReadableArray?) {
    val propertiesList = Arguments.toList(properties)?.map { it.toString() } ?: emptyList()
    val privacyModesList = Arguments.toList(privacyModes)?.map { it.toString() } ?: emptyList()
    val eventNamesList = Arguments.toList(eventNames)?.map { it.toString() } ?: emptyList()
    includePropertiesInPrivacyModes(propertiesList, privacyModesList, eventNamesList)
  }

  @ReactMethod
  override fun privacyIncludeProperty(property: String, privacyModes: ReadableArray?, eventNames: ReadableArray?) {
    val propertiesList = listOf(property)
    val privacyModesList: List<String> = Arguments.toList(privacyModes)?.map { it.toString() } ?: emptyList()
    val eventNamesList: List<String> = Arguments.toList(eventNames)?.map { it.toString() } ?: emptyList()
    includePropertiesInPrivacyModes(propertiesList, privacyModesList, eventNamesList)
  }

  private fun getPrivacyMode(mode: String): PrivacyMode? {
    return when (mode) {
      "exempt" -> PrivacyMode.EXEMPT
      "optin" -> PrivacyMode.OPTIN
      "optout" -> PrivacyMode.OPTOUT
      else -> null
    }
  }

  companion object {
    const val NAME = "RNPianoAnalytics"
  }
}
