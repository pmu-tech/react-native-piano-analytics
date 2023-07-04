package piano.analytics

import RNPianoAnalyticsSingleton
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import io.piano.analytics.Event;
import io.piano.analytics.Configuration;



class RNPianoAnalyticsModule() : Module() {
    var piano = RNPianoAnalyticsSingleton.get();
    // Each module class must implement the definition function. The definition consists of components
    // that describes the module's functionality and behavior.
    // See https://docs.expo.dev/modules/module-api for more details about available components.
    override fun definition() = ModuleDefinition {
        // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
        // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
        // The module will be accessible from `requireNativeModule('RNPianoAnalytics')` in JavaScript.
        Name("RNPianoAnalytics")
        
        // Defines a JavaScript synchronous function that runs the native code on the JavaScript thread.
        Function("sendEvent") { eventName: String, params: Map<String, String> ->
            piano.sendEvent(Event(eventName, params));
        }

        Function("setUser") { id: String, category: String?, enableStorage: Boolean ->
            piano.setUser(id, category, enableStorage);
        }

        Function("deleteUser") {
            piano.deleteUser();
        }

        Function("privacySetMode") { mode: String ->
            piano.privacySetMode(mode);
        }

        Function("privacyGetMode") {
            var result = ""
            piano.privacyGetMode { mode: String ->
                result = mode
            }
            result
        }

        Function("setConfiguration") { collectDomain: String, siteId: Int ->
            var config = Configuration.Builder()
                .withCollectDomain(collectDomain)
                .withSite(siteId)
                .build();
            piano.setConfiguration(config);
        }
    }
}
