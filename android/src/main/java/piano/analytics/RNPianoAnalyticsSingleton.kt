import io.piano.analytics.PianoAnalytics;


abstract class RNPianoAnalyticsSingleton {

    companion object {

        private lateinit var piano: PianoAnalytics

        fun set(pa: PianoAnalytics) {
            piano = pa
        }

        fun get(): PianoAnalytics {
            return piano
        }
    }
}