package ir.hossainco.prefkot.test

import android.app.Activity
import android.os.Bundle
import ir.hossainco.prefkot.pref
import org.jetbrains.anko.button
import org.jetbrains.anko.editText
import org.jetbrains.anko.verticalLayout

class MainActivity : Activity() {
	//	private var message by StringPrefProperty(
//		pref = PrefKot.defaultSharedPreferences!!,
//		key = null,//"message",
//		cache = true,
//		default = { "Hello" }
//	)
	private var message by pref<String>(
		//pref = PrefKot.defaultSharedPreferences!!,
		//key = "messaged",
		//cache = true,
		//default = "Hossain Khademian"
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		verticalLayout {
			val text = editText(message)
			button("Load").setOnClickListener {
				text.setText(message)
			}
			button("Save").setOnClickListener {
				message = text.text.toString()
			}
		}
	}
}
