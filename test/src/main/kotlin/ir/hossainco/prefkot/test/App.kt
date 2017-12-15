package ir.hossainco.prefkot.test

import android.app.Application
import ir.hossainco.prefkot.PrefKot

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		PrefKot.init(this)
	}
}
