package com.example.sergey.departmenttest.data.local

import android.content.Context

interface PreferenceManager {
    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String): String
}

class PreferenceManagerImpl(context: Context) : PreferenceManager {
    companion object {
        private const val PREFERENCES = "saved_preferences"
    }

    private val prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    override fun putString(key: String, value: String) = prefs.edit().putString(key, value).apply()
    override fun getString(key: String, defaultValue: String) = prefs.getString(key, defaultValue)!!
}