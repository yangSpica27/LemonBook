package com.spica27.accountbook.db.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User constructor(

    @PrimaryKey()
    var userID: Int = 1,

    @ColumnInfo(name = "month")
    var month: Int,
    @ColumnInfo(name = "income")
    var income: Double,
    @ColumnInfo(name = "outcome")
    var outCome: Double,
    @ColumnInfo(name = "planMoney")
    var planMoney: Int


) {


    override fun toString(): String {
        return super.toString()
    }
}