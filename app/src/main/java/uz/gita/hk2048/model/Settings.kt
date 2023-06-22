package uz.gita.hk2048.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class Settings private constructor(context: Context) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var INSTANCE: Settings
        fun init(context: Context) {
            if(!(::INSTANCE.isInitialized)) {
                    INSTANCE = Settings(context)
                }
            }
        fun getInstance() = INSTANCE
        }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("ABABABAB", Context.MODE_PRIVATE)
    fun gerSharpedPref() = sharedPreferences
}