package com.spica27.accountbook.db.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class Record constructor(
    //ID
    @PrimaryKey(autoGenerate = true)
    var recordID: Int?,
    //类型
    @ColumnInfo(name = "r_type")
    var recordType: String,
    //日期
    @ColumnInfo(name = "r_date")
    var recordDate: Long,
    //money
    @ColumnInfo(name = "r_money")
    var recordMoney: Double,
    //备注
    @ColumnInfo(name = "r_info")
    var recordInfo: String?,
    //支出还是收入
    @ColumnInfo(name = "r_iscome")
    var isCome: Boolean


) {
    override fun toString(): String {
        return "id:${recordID.toString()}\n 类型：${recordType}\n 日期：\n 金额${recordMoney.toString()}\n 备注${recordInfo} "
    }
}



