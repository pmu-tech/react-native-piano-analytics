package piano.analytics

import android.content.Context
import expo.modules.core.interfaces.ApplicationLifecycleListener
import expo.modules.core.interfaces.Package

class RNPianoAnalyticsPackage : Package {
    override fun createApplicationLifecycleListeners(context: Context): List<ApplicationLifecycleListener> {
        return listOf(RNPianoAnalyticsApplicationLifecycleListener(context))
    }
}