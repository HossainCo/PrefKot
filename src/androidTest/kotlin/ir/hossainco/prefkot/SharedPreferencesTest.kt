package ir.hossainco.prefkot

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferencesTest(pref: SharedPreferences) {
	var x by StringPrefProperty({ pref }, null, true, { "Hello" })

	@Test
	fun test() {
		Log.e("XxX", x)

		x += " (${x.length})"

		Log.e("XxX", x)
	}
}
