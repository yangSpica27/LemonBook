package com.spica27.accountbook

import com.spica27.accountbook.activity.*
import java.util.*
import kotlin.reflect.KClass

/**
 * 项目中的Activity及其识别码
 */

const val ABOUT = "关于"
const val MAIN = "账本"
const val NewRecord = "记账"
const val ALLRECORD = "全部记录"
const val SETTING = "设置"
const val HELP = "帮助"
const val BLANKCARD = "卡包"
const val NEWBLANKCARD = "创建卡片"
const val BACKUP="恢复与备份"

val MYACTIVITYS = arrayListOf(
    MyActivity(MAIN, MainActivity::class),
    MyActivity(ALLRECORD, AllRecordActivity::class),
    MyActivity(NewRecord, NewRecordActivity::class),
    MyActivity(BLANKCARD, BlankCardActivity::class),
    MyActivity(NEWBLANKCARD, NewBlankCardActivity::class),
    MyActivity(BACKUP,BackupActivity::class)

)

data class MyActivity(val title: String, val activityClass: KClass<out BaseActivity>) {
    val id: UUID = UUID.randomUUID()
}