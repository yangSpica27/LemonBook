package com.spica27.accountbook.db.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blank_card")
data class BlankCard(
    //ID
    @PrimaryKey(autoGenerate = true)
    var cardID: Int?,
    //类型
    @ColumnInfo(name = "card_name")
    var cardName: String,
    //日期
    @ColumnInfo(name = "card_date")
    var cardDate: Long,
    //卡号
    @ColumnInfo(name = "card_num")
    var cardNum: String


)