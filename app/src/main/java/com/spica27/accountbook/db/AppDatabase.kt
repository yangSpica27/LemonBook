package com.spica27.accountbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.spica27.accountbook.DATABASE_NAME
import com.spica27.accountbook.db.bean.BlankCard
import com.spica27.accountbook.db.bean.Record
import com.spica27.accountbook.db.bean.User
import com.spica27.accountbook.db.dao.BlankCardDao
import com.spica27.accountbook.db.dao.RecordDao
import com.spica27.accountbook.db.dao.UserDao


@Suppress("DEPRECATION")
@Database(entities = [Record::class,User::class,BlankCard::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    abstract fun userDao(): UserDao

    abstract fun blankDao():BlankCardDao

    companion object {

        // 数据库单例
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }


}