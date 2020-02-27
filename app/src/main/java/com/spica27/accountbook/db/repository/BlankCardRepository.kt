package com.spica27.accountbook.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.spica27.accountbook.db.AppDatabase
import com.spica27.accountbook.db.bean.BlankCard
import com.spica27.accountbook.db.dao.BlankCardDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BlankCardRepository(application: Application) {

    private var blankCardDao: BlankCardDao = AppDatabase.getInstance(application).blankDao()
    var allBlankCard: LiveData<List<BlankCard>>



    init {
        allBlankCard = blankCardDao.queryAll()
    }

    suspend fun insert(blankCard: BlankCard) = withContext(Dispatchers.IO) {
        blankCardDao.insert(blankCard)
    }

    suspend fun delete(blankCard: BlankCard) = withContext(Dispatchers.IO) {
        blankCardDao.delete(blankCard)
    }

    suspend fun queryAll(): LiveData<List<BlankCard>> = withContext(Dispatchers.IO) {
        return@withContext blankCardDao.queryAll()
    }


}
