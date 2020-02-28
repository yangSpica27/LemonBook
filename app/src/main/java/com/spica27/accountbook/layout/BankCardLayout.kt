package com.spica27.accountbook.layout

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.spica27.accountbook.R
import com.spica27.accountbook.utils.DisplayTypedValueUtil
import com.spica27.accountbook.view.BandCardEditText

class BankCardLayout : ConstraintLayout {

    private var mContext: Context? = null


    //卡片名称
    private var tvTitleCardName: TextView? = null
    //校园卡
    var tvCardName: TextView? = null

    //123 2344 XXXX
    var tvCardNum: BandCardEditText? = null

    //日期
    private var tvTitleDate: TextView? = null
    //23日/7月
    var tvDate: TextView? = null


    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
    }

//    /**
//     * 当布局加载完毕
//     */
//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        //如果没有在布局中设置id，则在这里提供磨人设置
//        if (id == -1) {
//            id = R.id.container
//        }
//        init()
//    }

    /**
     * 初始化
     */

    init {
        id = R.id.container
        val lpcontainer = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        background = ContextCompat.getDrawable(context, R.drawable.bg_blank_card)
        lpcontainer.bottomMargin = DisplayTypedValueUtil.dip2px(16)
        setPadding(DisplayTypedValueUtil.dip2px(16))

        //名称标题的布局属性
        val lpTitleName =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        lpTitleName.leftToLeft = id
        lpTitleName.topToTop = id
        tvTitleCardName = TextView(context)
        tvTitleCardName?.id = R.id.tv_title_card_name
        tvTitleCardName?.text = "卡片名称"
        tvTitleCardName?.setTextAppearance(R.style.TextAppearance_MaterialComponents_Subtitle1)
        tvTitleCardName?.setTextColor(Color.parseColor("#7EFFFFFF"))
        addView(tvTitleCardName, lpTitleName)

        //卡片名称
        val lpName =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        lpName.topMargin = DisplayTypedValueUtil.dip2px(8)
        lpName.topToBottom = tvTitleCardName!!.id
        lpName.leftToLeft = id
        tvCardName = TextView(context)
        tvCardName?.id = R.id.tv_card_name
        tvCardName?.text = "校园卡"
        tvCardName?.setTextAppearance(R.style.TextAppearance_UIFabric_Heading)
        tvCardName?.setTextColor(ContextCompat.getColor(context, R.color.uifabric_white))
        addView(tvCardName, lpName)

        //卡号
        val lpCardNum = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        lpCardNum.topMargin = DisplayTypedValueUtil.dip2px(8)
        lpCardNum.topToBottom = tvCardName!!.id
        lpCardNum.leftToLeft = id
        tvCardNum = BandCardEditText(context)
        tvCardNum?.maxLines = 1
        tvCardNum?.id = R.id.tv_card_num
        tvCardNum?.setTextColor(ContextCompat.getColor(context, R.color.uifabric_white))
        tvCardNum?.setText("0000000")
        tvCardNum?.typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
        tvCardNum?.textSize = 36F
        addView(tvCardNum, lpCardNum)

        val lpTitleDate = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        lpTitleDate.topMargin = DisplayTypedValueUtil.dip2px(8)
        lpTitleDate.topToBottom = tvCardNum!!.id
        lpTitleDate.leftToLeft = id
        tvTitleDate = TextView(context)
        tvTitleDate?.id = R.id.tv_title_card_date
        tvTitleDate?.setTextAppearance(R.style.TextAppearance_MaterialComponents_Subtitle1)
        tvTitleDate?.setTextColor(Color.parseColor("#7EFFFFFF"))
        tvTitleDate?.text = "日期"
        addView(tvTitleDate, lpTitleDate)

        val lpDate = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        lpDate.topMargin = DisplayTypedValueUtil.dip2px(8)
        lpDate.leftToLeft = id
        lpDate.topToBottom = tvTitleDate!!.id
        tvDate = TextView(context)
        tvDate?.id = R.id.tv_card_date
        tvDate?.setTextAppearance(R.style.TextAppearance_MaterialComponents_Subtitle1)
        tvDate?.setTextColor(ContextCompat.getColor(context, R.color.uifabric_white))
        addView(tvDate, lpDate)
        //初始化完成


    }



    //防止重复点击
    private var lastClickTime: Long = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (isFrequentlyClick()) {

                Toast.makeText(context, "请不要重复点击", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    fun isFrequentlyClick(): Boolean {
        var clickTime = System.currentTimeMillis()
        var value = clickTime - lastClickTime
        lastClickTime = clickTime
        return value <= 500


    }
}