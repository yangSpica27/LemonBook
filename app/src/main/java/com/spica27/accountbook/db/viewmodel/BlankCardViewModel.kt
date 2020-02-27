package com.spica27.accountbook.db.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.spica27.accountbook.db.bean.BlankCard
import com.spica27.accountbook.db.repository.BlankCardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlankCardViewModel(application: Application) : AndroidViewModel(application) {


    val blankCardRepository = BlankCardRepository(application)
    var allBlankCard: LiveData<List<BlankCard>>? = null

    init {
        allBlankCard = blankCardRepository.allBlankCard
    }

    fun insert(blankCard: BlankCard) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            blankCardRepository.insert(blankCard)
        }
    }

    fun delete(blankCard: BlankCard) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            blankCardRepository.delete(blankCard)
        }
    }


}