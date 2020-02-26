package com.spica27.accountbook.db.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.spica27.accountbook.db.bean.Record
import com.spica27.accountbook.db.repository.RecordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    var recordRepository: RecordRepository = RecordRepository(application)
    var allRecord: LiveData<List<Record>>? = null


    init {
        allRecord = recordRepository.allRecords
    }


    fun sync(){
        allRecord = recordRepository.allRecords
    }



    fun insert(record: Record) {
        recordRepository.insertRecord(record)
    }


    fun delete(record: Record) {
        recordRepository.deleteRecord(record)
    }

}