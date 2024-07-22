package com.malicankaya.besinprojesi.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.malicankaya.besinprojesi.model.Besin

@Database(entities = [Besin::class], version = 1)
abstract class BesinDatabase : RoomDatabase() {
    abstract fun besinDao() : BesinDAO


    companion object {

        @Volatile
        private var instance : BesinDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BesinDatabase::class.java,
            "BesinDatabase"
            ).build()
    }

}