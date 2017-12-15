package ir.hossainco.prefkot

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestApp {
	lateinit var context: Context
	lateinit var pref: SharedPreferences

	@Before
	fun before(){
		context = InstrumentationRegistry.getTargetContext()
		pref = PreferenceManager.getDefaultSharedPreferences(context)
	}

	@Test
	fun test() {
		SharedPreferencesTest(pref).test()
	}
}
