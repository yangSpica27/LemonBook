package com.spica27.accountbook.db.dao

import androidx.room.*
import com.spica27.accountbook.db.bean.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: User)

    @Query("SELECT * FROM user WHERE userID =1")
    fun getUser(): User

    @Update
    fun update(element: User)

}