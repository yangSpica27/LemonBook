package com.spica27.accountbook.db.repository

import android.app.Application
import com.spica27.accountbook.db.AppDatabase
import com.spica27.accountbook.db.bean.User
import com.spica27.accountbook.db.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(application: Application) {


    private var userDao: UserDao

    init {
        val appDatabase = AppDatabase.getInstance(application)
        userDao = appDatabase.userDao()
    }


    suspend fun getUser(): User? = withContext(Dispatchers.IO) {
        return@withContext userDao.getUser()
    }

    suspend fun initUser(user: User) = withContext(Dispatchers.IO) {
        userDao.insert(user)
    }

    suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        userDao.update(user)
    }


}