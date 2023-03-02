package com.geektech.taskmanager.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun getImage(): String {
        return pref.getString(IMAGE_KEY, "").toString()

    }

    fun setImage(image: String) {
        pref.edit().putString(IMAGE_KEY, image).apply()

    }


    fun isUser(): Boolean {
        return pref.getBoolean(SEEN_KEY, false)
    }

    fun savUserSeen() {
        pref.edit().putBoolean(SEEN_KEY, true).apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun saveName(name: String) {
        pref.edit().putString(NAME_KEY, name).apply()
    }

    fun getName(): String {
        return pref.getString(NAME_KEY, "").toString()
    }


    companion object {
        const val PREF_NAME = "pref.task"
        const val SEEN_KEY = "seen.key"
        const val NAME_KEY = "name.pref"
        const val IMAGE_KEY = "image"
    }
}