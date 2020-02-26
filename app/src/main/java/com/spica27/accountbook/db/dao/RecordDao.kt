package com.spica27.accountbook.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.spica27.accountbook.db.bean.Record

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: Record)

    @Insert
    fun insertAll(users: List<Record>)


    @Delete
    fun delete(element: Record)

    @Query("SELECT * FROM record Order by recordID DESC")
    fun getAllRecords(): LiveData<List<Record>>

    @Query("select * from record")
    fun getRecordList(): List<Record>

    //收入列表
    @Query("select * from record where r_iscome = 1 Order by recordID DESC")
    fun getInComeList(): List<Record>

    //支出列表
    @Query("select * from record where r_iscome = 0 Order by recordID DESC")
    fun getOutComeList(): List<Record>

    @Query("select * from record where recordID = :recordId")
    fun getRecord(recordId: Int): Record

    @Query("select * from record order by recordID desc ")
    fun getAllByDateDesc(): LiveData<List<Record>>

    @Query("delete from record")
    fun deleteAll()

}