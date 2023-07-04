package piano.analytics

import android.content.Context
import android.app.Application
import expo.modules.core.interfaces.ApplicationLifecycleListener

// Piano
import io.piano.analytics.PianoAnalytics;
import io.piano.analytics.Configuration;


class RNPianoAnalyticsApplicationLifecycleListener(context: Context) : ApplicationLifecycleListener {
    var context = context

    override fun onCreate(application: Application) {
        super.onCreate(application)

        var piano = PianoAnalytics.getInstance(this.context);
        RNPianoAnalyticsSingleton.set(piano);
    }
}