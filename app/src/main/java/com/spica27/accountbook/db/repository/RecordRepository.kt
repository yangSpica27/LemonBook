package com.spica27.accountbook.db.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.spica27.accountbook.db.AppDatabase
import com.spica27.accountbook.db.bean.Record
import com.spica27.accountbook.db.dao.RecordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordRepository constructor(application: Application) {

    private var recordDao: RecordDao
    var allRecords: LiveData<List<Record>>

    init {
        val appDatabase = AppDatabase.getInstance(application)
        recordDao = appDatabase.recordDao()
        allRecords = recordDao.getAllRecords()
    }

    fun insertRecord(record: Record) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            insert(record)
        }
    }

    fun deleteRecord(record: Record) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            delete(record)
        }
    }



    suspend fun queryInCome(): List<Record> = withContext(Dispatchers.IO) {
        return@withContext recordDao.getInComeList()
    }

    suspend fun queryOutCome(): List<Record> = withContext(Dispatchers.IO) {
        return@withContext recordDao.getOutComeList()
    }

    suspend fun queryAll(): LiveData<List<Record>> = withContext(Dispatchers.IO) {
        return@withContext recordDao.getAllRecords()
    }


    private suspend fun insert(record: Record) = withContext(Dispatchers.IO) {
        Log.e("插入数据:", record.recordType)
        recordDao.insert(record)
    }

    private suspend fun delete(record: Record) = withContext(Dispatchers.IO) {
        Log.e("删除数据:", record.recordType)
        recordDao.delete(record)
    }


}