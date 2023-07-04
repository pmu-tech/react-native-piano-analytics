import ExpoModulesCore
import PianoAnalytics

public class RNPianoAnalyticsModule: Module {
    // Each module class must implement the definition function. The definition consists of components
    // that describes the module's functionality and behavior.
    // See https://docs.expo.dev/modules/module-api for more details about available components.
    public func definition() -> ModuleDefinition {
        // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
        // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
        // The module will be accessible from `requireNativeModule('RNPianoAnalytics')` in JavaScript.
        Name("RNPianoAnalytics")

        // Defines a JavaScript synchronous function that runs the native code on the JavaScript thread.
        Function("sendEvent") { (eventName: String, body: [String: String]) in
            pa.sendEvent(Event(eventName, data: body))
        }

        Function("setUser") { (id: String, category: String? , enableStorage: Bool) in
            pa.setUser(id, category: category, enableStorage: enableStorage)
        }
        
        Function("privacySetMode") { (mode: String) in
            pa.privacySetMode(mode)
        }
        
        Function("privacyGetMode") { () -> String in
            var result: String = ""
            pa.privacyGetMode { privacyMode in
                result = privacyMode
            }
            return result
        }
        
        Function("deleteUser") {
            pa.deleteUser()
        }

        Function("setConfiguration") { (collectionName: String, siteId: Int) in
            pa.setConfiguration(ConfigurationBuilder()
                .withCollectDomain(collectionName)
                .withSite(siteId)
                .build()
            )
        }
    }
}



