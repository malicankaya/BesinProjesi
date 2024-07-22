package com.malicankaya.besinprojesi.util

import android.content.Context
import android.content.SharedPreferences

class OzelSharedPreferences {

    companion object {

        private val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        private var instance: OzelSharedPreferences? = null

        private var lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createSharedPreferences(context: Context): OzelSharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }

        fun zamaniKaydet(zaman: Long){
            sharedPreferences?.edit()?.putLong(TIME,zaman)?.apply()
        }

        fun zamaniAl() = sharedPreferences?.getLong(TIME,0)


    }

}