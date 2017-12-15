@file:Suppress("unused", "UNCHECKED_CAST", "RedundantVisibilityModifier")

package ir.hossainco.prefkot

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.View
import kotlin.reflect.KClass

typealias Provider<T> = () -> T

inline fun <T> tri(block: () -> T) = try {
	block()
} catch (e: Throwable) {
	null
}

fun <T> T?.toFun(): Provider<T>? = if (this != null) ({ this }) else null
fun Context.getDefaultSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(this)!!

object PrefKot {
	var defaultSharedPreferences: SharedPreferences? = null
		private set

	public fun init(pref: SharedPreferences?) {
		defaultSharedPreferences = pref
	}

	public fun init(context: Context?)
		= init(context?.getDefaultSharedPreferences())
}


inline fun <reified T : Any> View.pref(key: String? = null, cache: Boolean = true, noinline default: Provider<T>?) = context.pref(key, cache, default)
inline fun <reified T : Any> View.pref(key: String? = null, cache: Boolean = true, default: T? = null) = context.pref(key, cache, default)


inline fun <reified T : Any> Fragment.pref(key: String? = null, cache: Boolean = true, noinline default: Provider<T>?) = context!!.pref(key, cache, default)
inline fun <reified T : Any> Fragment.pref(key: String? = null, cache: Boolean = true, default: T? = null) = context!!.pref(key, cache, default)


inline fun <reified T : Any> android.app.Fragment.pref(key: String? = null, cache: Boolean = true, noinline default: Provider<T>?) = activity.pref(key, cache, default)
inline fun <reified T : Any> android.app.Fragment.pref(key: String? = null, cache: Boolean = true, default: T? = null) = activity.pref(key, cache, default)


inline fun <reified T : Any> Context.pref(key: String? = null, cache: Boolean = true, default: T? = null)
	= pref(this::getDefaultSharedPreferences, key, cache, default.toFun())

inline fun <reified T : Any> Context.pref(key: String? = null, cache: Boolean = true, noinline default: Provider<T>?)
	= pref(this::getDefaultSharedPreferences, key, cache, default)


inline fun <reified T : Any> pref(noinline pref: Provider<SharedPreferences>? = null, key: String? = null, cache: Boolean = true, default: T? = null)
	= pref(T::class, pref, key, cache, default.toFun())

inline fun <reified T : Any> pref(noinline pref: Provider<SharedPreferences>? = null, key: String? = null, cache: Boolean = true, noinline default: Provider<T>?)
	= pref(T::class, pref, key, cache, default)


fun <T : Any> pref(clazz: KClass<T>, prefProvider: Provider<SharedPreferences>? = null, key: String? = null, cache: Boolean = true, default: Provider<T>?): PrefProperty<T> {
	val pref = prefProvider ?: { PrefKot.defaultSharedPreferences ?: throw RuntimeException("PrefProperty is not initialised yet!!") }
	return when (clazz) {
		String::class -> StringPrefProperty(pref, key, cache, default as Provider<String>?)
		Boolean::class -> BooleanPrefProperty(pref, key, cache, default as Provider<Boolean>?)
		Int::class -> IntPrefProperty(pref, key, cache, default as Provider<Int>?)
		Long::class -> LongPrefProperty(pref, key, cache, default as Provider<Long>?)
		Float::class -> FloatPrefProperty(pref, key, cache, default as Provider<Float>?)
		Set::class -> StringSetPrefProperty(pref, key, cache, default as Provider<Set<String>>?)
		ByteArray::class -> ByteArrayPrefProperty(pref, key, cache, default as Provider<ByteArray>?)
		Byte::class -> BytePrefProperty(pref, key, cache, default as Provider<Byte>?)
		else -> throw RuntimeException("Invalid Type")
	} as PrefProperty<T>
}
