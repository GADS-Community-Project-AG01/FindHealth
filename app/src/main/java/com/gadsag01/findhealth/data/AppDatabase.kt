package com.gadsag01.findhealth.data

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gadsag01.findhealth.model.Hospital
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executor

@Database(entities = [Hospital::class], version = 2, exportSchema = true,
    autoMigrations = [AutoMigration (from = 1, to = 2)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "DATABASE")
                .setQueryCallback({ strings, _ ->
                    Log.d("callback", strings)
                }, Dispatchers.IO as Executor)
                .build()
        }

    }

}
