package com.spica27.accountbook

import com.spica27.accountbook.activity.AboutActivity
import com.spica27.accountbook.activity.AllRecordActivity
import com.spica27.accountbook.activity.MainActivity
import com.spica27.accountbook.activity.NewRecordActivity
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

val MYACTIVITYS = arrayListOf(
    MyActivity(MAIN, MainActivity::class),
    MyActivity(ALLRECORD, AllRecordActivity::class),
    MyActivity(NewRecord, NewRecordActivity::class)

)

data class MyActivity(val title: String, val activityClass: KClass<out BaseActivity>) {
    val id: UUID = UUID.randomUUID()
}