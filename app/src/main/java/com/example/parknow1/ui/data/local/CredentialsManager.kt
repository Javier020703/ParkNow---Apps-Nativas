package com.example.parknow1.data.local

import android.content.Context

object CredentialsManager {

    private const val PREFS_NAME = "auth"

    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_FINGERPRINT = "fingerprint_enabled"

    fun saveCredentials(
        context: Context,
        email: String,
        password: String
    ) {

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_FINGERPRINT, true)
            .apply()
    }

    fun clearCredentials(context: Context) {

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }

    fun isFingerprintEnabled(context: Context): Boolean {

        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val email = prefs.getString(KEY_EMAIL, null)
        val password = prefs.getString(KEY_PASSWORD, null)
        val fingerprint = prefs.getBoolean(KEY_FINGERPRINT, false)

        return email != null &&
                password != null &&
                fingerprint
    }

    fun getEmail(context: Context): String? {

        return context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        ).getString(KEY_EMAIL, null)
    }

    fun getPassword(context: Context): String? {

        return context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        ).getString(KEY_PASSWORD, null)
    }
}