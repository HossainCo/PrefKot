@file:Suppress("unused", "RedundantVisibilityModifier")

package ir.hossainco.prefkot

import android.content.SharedPreferences
import ir.hossainco.commonkotlin.provider.Provider
import ir.hossainco.commonkotlin.provider.tri
import org.json.JSONArray
import org.json.JSONObject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class PrefProperty<T>
(prefFun: Provider<SharedPreferences>,
 private val key: String?,
 private val cache: Boolean,
 private val default: Provider<T>)
	: ReadWriteProperty<Any, T> {
	protected val pref by lazy(prefFun)

	protected abstract fun getValueInternal(thisRef: Any, property: KProperty<*>): T
	protected abstract fun setValueInternal(thisRef: Any, property: KProperty<*>, value: T)

	private var value: T? = null

	@Suppress("UNUSED_PARAMETER")
	fun remove(thisRef: Any, property: KProperty<*>)
		= pref.edit().remove(getKey(property)).apply()

	protected fun getKey(property: KProperty<*>)
		= key ?: property.name

	@Suppress("UNUSED_PARAMETER")
	protected fun getDefaultValue(property: KProperty<*>)
		= default.invoke()


	override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
		if (cache)
			this.value?.let { return@getValue it }

		val value = getValueInternal(thisRef, property)
		this.value = value
		return value
	}

	override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
		this.value = value
		setValueInternal(thisRef, property, value)
	}
}

class StringPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<String>?)
	: PrefProperty<String>(pref, key, cache, default ?: { "" }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getString(getKey(property), null) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: String)
		= pref.edit().remove(getKey(property)).putString(getKey(property), value).apply()
}

class BooleanPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<Boolean>?)
	: PrefProperty<Boolean>(pref, key, cache, default ?: { false }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getBoolean(getKey(property), getDefaultValue(property)) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: Boolean)
		= pref.edit().remove(getKey(property)).putBoolean(getKey(property), value).apply()
}

class IntPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<Int>?)
	: PrefProperty<Int>(pref, key, cache, default ?: { 0 }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getInt(getKey(property), getDefaultValue(property)) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: Int)
		= pref.edit().remove(getKey(property)).putInt(getKey(property), value).apply()
}

class LongPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<Long>?)
	: PrefProperty<Long>(pref, key, cache, default ?: { 0 }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getLong(getKey(property), getDefaultValue(property)) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: Long) {
		pref.edit().remove(getKey(property)).apply()
		pref.edit().putLong(getKey(property), value).apply()
	}
}

class FloatPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<Float>?)
	: PrefProperty<Float>(pref, key, cache, default ?: { 0f }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getFloat(getKey(property), getDefaultValue(property)) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: Float)
		= pref.edit().remove(getKey(property)).putFloat(getKey(property), value).apply()
}

class StringSetPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<Set<String>>?)
	: PrefProperty<Set<String>>(pref, key, cache, default ?: { HashSet() }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getStringSet(getKey(property), null) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: Set<String>)
		= pref.edit().remove(getKey(property)).putStringSet(getKey(property), value).apply()
}

/* Created Types */

class BytePrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<Byte>?)
	: PrefProperty<Byte>(pref, key, cache, default ?: { 0 }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getInt(getKey(property), getDefaultValue(property).toInt()) }?.toByte() ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: Byte)
		= pref.edit().remove(getKey(property)).putInt(getKey(property), value.toInt()).apply()
}

class ByteArrayPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<ByteArray>?)
	: PrefProperty<ByteArray>(pref, key, cache, default ?: { ByteArray(0) }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { pref.getString(getKey(property), null) }?.toByteArray() ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: ByteArray) {
		pref.edit().remove(getKey(property)).apply()
		pref.edit().putString(getKey(property), value.toString()).apply()
	}
}

class JsonObjectPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<JSONObject>?)
	: PrefProperty<JSONObject>(pref, key, cache, default ?: { JSONObject() }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { JSONObject(pref.getString(getKey(property), null)) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: JSONObject) {
		pref.edit().remove(getKey(property)).apply()
		pref.edit().putString(getKey(property), value.toString()).apply()
	}
}

class JsonArrayPrefProperty
(pref: Provider<SharedPreferences>, key: String?, cache: Boolean, default: Provider<JSONArray>?)
	: PrefProperty<JSONArray>(pref, key, cache, default ?: { JSONArray() }) {

	override fun getValueInternal(thisRef: Any, property: KProperty<*>)
		= tri { JSONArray(pref.getString(getKey(property), null)) } ?: getDefaultValue(property)

	override fun setValueInternal(thisRef: Any, property: KProperty<*>, value: JSONArray) {
		pref.edit().remove(getKey(property)).apply()
		pref.edit().putString(getKey(property), value.toString()).apply()
	}
}
