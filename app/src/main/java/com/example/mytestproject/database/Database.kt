package com.example.mytestproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytestproject.database.users.Users
import com.example.mytestproject.database.users.UsersDao

@Database(
    entities = [Users::class],
    version = 1, exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract val usersDao: UsersDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "data_base"
                    )
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
