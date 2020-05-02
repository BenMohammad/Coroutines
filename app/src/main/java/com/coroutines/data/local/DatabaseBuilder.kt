package com.coroutines.data.local

import android.content.Context
import android.view.ViewDebug
import androidx.room.Room
import androidx.room.RoomDatabase

object DatabaseBuilder {

    private var INSTANCE : AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if(INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)

            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "coroutines"
        ).build()
}