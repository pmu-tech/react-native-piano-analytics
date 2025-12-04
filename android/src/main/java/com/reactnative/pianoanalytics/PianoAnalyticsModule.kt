package com.reactnative.pianoanalytics

import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import io.piano.android.analytics.Configuration
import io.piano.android.analytics.PianoAnalytics
import io.piano.android.analytics.model.Event
import io.piano.android.analytics.model.PrivacyMode
import io.piano.android.analytics.model.Property
import io.piano.android.analytics.model.PropertyName
import io.piano.android.analytics.model.User
import io.piano.android.analytics.model.VisitorIDType

@ReactModule(name = RNPianoAnalyticsModule.NAME)
class RNPianoAnalyticsModule(reactContext: ReactApplicationContext) :
  RNPianoAnalyticsSpec(reactContext) {

  @ReactMethod
  override fun sendEvent(eventName: String?, params: ReadableMap?, promise: Promise?) {
    val properties = mutableListOf<Property>()
    params?.let {
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
      val event = eventName?.let { evtName -> Event.Builder(evtName).properties(properties).build() }
      event?.let { e -> PianoAnalytics.getInstance().sendEvents(e) }

      promise?.resolve("event sent successfully")
    } ?: {
      promise?.reject(RNPianoAnalyticsModule.NAME,"params are not defined")
    }
  }

  @ReactMethod
  override fun setUser(
    userId: String?,
    category: String?,
    enableStorage: Boolean,
    promise: Promise?
  ) {
    val userStorage = PianoAnalytics.getInstance().userStorage
    userId?.let {
      userStorage.currentUser = User(userId, category, enableStorage)
      promise?.resolve("userId set successfully")
    } ?: {
      promise?.reject(RNPianoAnalyticsModule.NAME,"userId not defined")
    }
  }

  override fun deleteUser(promise: Promise?) {
    PianoAnalytics.getInstance().userStorage.currentUser = null
  }

  @ReactMethod
  override fun privacySetMode(mode: String?, promise: Promise?) {
    mode?.let {
      val privacyMode = getPrivacyMode(mode) ?: return
      PianoAnalytics.getInstance().privacyModesStorage.currentMode = privacyMode
      promise?.resolve("privacy mode set successfully")
    } ?: {
      promise?.reject(RNPianoAnalyticsModule.NAME,"mode is not defined")
    }
  }

  @ReactMethod
  override fun privacyGetMode(promise: Promise) {
    val currentMode = PianoAnalytics.getInstance().privacyModesStorage.currentMode
    promise.resolve(currentMode)
  }

  @ReactMethod
  override fun setVisitorId(visitorId: String?, promise: Promise?) {
    PianoAnalytics.getInstance().customVisitorId = visitorId
  }

  @ReactMethod
  override fun getVisitorId(promise: Promise) {
    val visitorId = PianoAnalytics.getInstance().visitorId
    if (visitorId != null) {
      promise.resolve(visitorId)
    } else {
      promise.reject("Fail to retrieve custom visitor id")
    }
  }

  @ReactMethod
  override fun setConfiguration(collectionName: String?, siteId: Double, promise: Promise?) {
    collectionName?.let {
      val config = Configuration.Builder(
        collectDomain = collectionName,
        site = siteId.toInt(),
        visitorIDType = VisitorIDType.UUID,
      ).build()
      PianoAnalytics.init(reactApplicationContext, config)
      promise?.resolve("config initialized successfully")
    } ?: {
      promise?.reject(RNPianoAnalyticsModule.NAME,"collectionName is not defined")
    }
  }

  // PRIVACY INCLUDE PROPERTY
  private fun includePropertiesInPrivacyModes(propertiesList: List<String>, privacyModesList: List<String>, eventNamesList: List<String>) {
      // Currently, adding properties for a custom mode is not supported. We're using EXEMPT as a placeholder.
      // Feel free to extend the functionality to allow custom modes.
      for (eventName in eventNamesList) {
        for (property in propertiesList) {
          PrivacyMode.EXEMPT.allowedPropertyKeys[eventName]?.add(PropertyName(property))
        }
      }
  }

  @ReactMethod
  override fun privacyIncludeProperties(properties: ReadableArray, privacyModes: ReadableArray?, eventNames: ReadableArray?) {
    val propertiesList = Arguments.toList(properties)?.map { it.toString() } ?: emptyList()
    val privacyModesList = Arguments.toList(privacyModes)?.map { it.toString() } ?: listOf()
    val eventNamesList = Arguments.toList(eventNames)?.map { it.toString() } ?: listOf(Event.ANY)
    includePropertiesInPrivacyModes(propertiesList, privacyModesList, eventNamesList)
  }

  @ReactMethod
  override fun privacyIncludeProperty(property: String, privacyModes: ReadableArray?, eventNames: ReadableArray?) {
    val propertiesList = listOf(property)
    val privacyModesList: List<String> = Arguments.toList(privacyModes)?.map { it.toString() } ?: emptyList()
    val eventNamesList: List<String> = Arguments.toList(eventNames)?.map { it.toString() } ?: listOf(Event.ANY)
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
