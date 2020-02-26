package com.spica27.accountbook

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.microsoft.officeuifabric.persona.IPersona
import com.microsoft.officeuifabric.persona.Persona

/**
 *收支类型
 **/

fun getAllTypeList(): ArrayList<IPersona> {
    return arrayListOf(
        createBudget("三餐", "早饭·中饭·晚饭·夜宵？"),
        createBudget("零食", "长胖的根源"),
        createBudget("衣服", "人靠衣装"),
        createBudget("旅游", "陶冶情操"),
        createBudget("学习", "好好学习，天天向上"),
        createBudget("请客送礼", "红包，下馆子等费用"),
        createBudget("生活缴费", "话费，水电费，网费等"),
        createBudget("购物", "人类的剁手活动"),
        createBudget("娱乐", "卡拉OK，酒吧?"),
        createBudget("其他", "其他类型的消费"),
        createBudget("工资", "社畜的主要资金来源"),
        createBudget("生活费", "学生党的资金主要来源"),
        createBudget("红包", "嗯，今天是什么日子"),
        createBudget("外快", "兼职，打工"),
        createBudget("股票基金", "股市有风险，投资需谨慎"),
        createBudget("其他", "其他的收入来源")
    )
}


//支出类型
fun getOutTypeList(context: Context?): ArrayList<IPersona> {
    context ?: return arrayListOf()
    return arrayListOf(
        createBudget("三餐", "早饭·中饭·晚饭·夜宵？"),
        createBudget("零食", "长胖的根源"),
        createBudget("衣服", "人靠衣装"),
        createBudget("旅游", "陶冶情操"),
        createBudget("学习", "好好学习，天天向上"),
        createBudget("请客送礼", "红包，下馆子等费用"),
        createBudget("生活缴费", "话费，水电费，网费等"),
        createBudget("购物", "人类的剁手活动"),
        createBudget("娱乐", "卡拉OK，酒吧?"),
        createBudget("其他", "其他类型的消费")
    )

}

//收入类型
fun getInComeTypeList(context: Context?): ArrayList<IPersona> {
    context ?: return arrayListOf()
    return arrayListOf(
        createBudget("工资", "社畜的主要资金来源"),
        createBudget("生活费", "学生党的资金主要来源"),
        createBudget("红包", "嗯，今天是什么日子"),
        createBudget("外快", "兼职，打工"),
        createBudget("股票基金", "股市有风险，投资需谨慎"),
        createBudget("其他", "其他的收入来源")
    )

}


private fun createBudget(
    name: String,
    subtitle: String,
    imageResource: Int? = null,
    imageDrawable: Drawable? = null,
    imageBitmap: Bitmap? = null,
    imageUri: Uri? = null,
    email: String = ""
): IPersona {
    val persona = Persona(name)
    persona.email = email
    persona.subtitle = subtitle
    persona.avatarImageResourceId = imageResource
    persona.avatarImageDrawable = imageDrawable
    persona.avatarImageBitmap = imageBitmap
    persona.avatarImageUri = imageUri
    return persona
}