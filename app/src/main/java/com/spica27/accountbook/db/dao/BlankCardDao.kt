package com.spica27.accountbook.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.spica27.accountbook.db.bean.BlankCard

@Dao
interface BlankCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(blankCard: BlankCard)

    @Delete
    fun delete(blankCard: BlankCard)

    @Query("SELECT * FROM blank_card order by card_date")
    fun queryAll():LiveData<List<BlankCard>>

}