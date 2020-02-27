package com.spica27.accountbook.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.spica27.accountbook.R


//手写布局示例（未完成）
class Testlayout(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    val editText = EditText(context)


    init {
        id = R.id.container

    }


}