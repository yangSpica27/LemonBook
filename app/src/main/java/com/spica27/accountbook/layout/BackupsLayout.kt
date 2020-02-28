package com.spica27.accountbook.layout

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.microsoft.officeuifabric.listitem.ListItemView
import com.spica27.accountbook.R
import com.spica27.accountbook.utils.DisplayTypedValueUtil

class BackupsLayout : ConstraintLayout {

    private var mcontext: Context

   constructor(context: Context) : super(context) {
        mcontext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mcontext = context
    }

    //WebDav网址（默认为坚果云）
    private var titleHost: TextView
    //https://dav.jianguoyun.com/dav/
    private var etHost: EditText
    //账号
    private var titleAccount: TextView
    //请输入WebDav账号
    private var etAccount: EditText
    //密码
    private var titlePassword: TextView
    //请输入WebDav密码
    private var etPassword: EditText
    //线
    private var line: View

    //立即备份
    private var itemSave: ListItemView
    //恢复数据
    private var itemBackup: ListItemView

    init {
        id = R.id.layout_backups
        titleHost = TextView(context)
        titleHost.id = R.id.title_hosts
        val lpTitleHost = initTitleStyle(titleHost, "WebDav网址（默认为坚果云）")
        lpTitleHost.topToTop = id
        addView(titleHost, lpTitleHost)

        etHost = EditText(context)
        etHost.id = R.id.et_hosts
        val lpEtHost = initEditStyle(etHost, "请输入WebDav服务器地址")
        lpEtHost.topToBottom = titleHost.id
        addView(etHost, lpEtHost)

        titleAccount = TextView(context)
        titleAccount.id = R.id.title_account
        val lpTitleAccount = initTitleStyle(titleAccount, "账号")
        lpTitleAccount.topToBottom = etHost.id
        addView(titleAccount, lpTitleAccount)

        etAccount = EditText(context)
        etAccount.id = R.id.et_account
        val lpEtAccount = initEditStyle(etAccount, "请输入账号")
        lpEtAccount.topToBottom = titleAccount.id
        addView(etAccount, lpEtAccount)

        titlePassword = TextView(context)
        titlePassword.id = R.id.title_password
        val lpTitlePassword = initTitleStyle(titlePassword, "密码")
        lpTitlePassword.topToBottom = etAccount.id
        addView(titlePassword, lpTitlePassword)

        etPassword = EditText(context)
        etPassword.id = R.id.et_password
        val lpEtPassword = initEditStyle(etPassword, "请输入密码")
        lpEtPassword.topToBottom = titlePassword.id
        etPassword.setPadding(
            DisplayTypedValueUtil.dip2px(16),
            DisplayTypedValueUtil.dip2px(8),
            DisplayTypedValueUtil.dip2px(16),
            DisplayTypedValueUtil.dip2px(8)
        )
        etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        addView(etPassword, lpEtPassword)

        line = View(context)
        line.id = R.id.line
        val lpLine = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        line.background = ContextCompat.getDrawable(context, R.drawable.ms_row_divider)
        lpLine.topToBottom = etPassword.id
        addView(line, lpLine)

        itemSave = ListItemView(context)
        itemSave.id = R.id.item_save_db
        val lpItemSave = initItemStyle(
            itemSave,
            "立即备份",
            "将您的数据备份至云盘当中",
            createCustomView(R.drawable.ic_backup_black_24dp)
        )
        lpItemSave.topToBottom = R.id.line
        addView(itemSave, lpItemSave)

        itemBackup = ListItemView(context)
        itemBackup.id = R.id.item_back_db
        val lpItemBack = initItemStyle(
            itemBackup,
            "恢复数据",
            "备份文件将从您的WebDav中读取",
            createCustomView(R.drawable.ic_flip_to_back_black_24dp)
        )
        lpItemBack.topToBottom = R.id.item_save_db
        addView(itemBackup, lpItemBack)
    }

    private fun createCustomView(int: Int): ImageView {
        val imageView = ImageView(context)
        Glide.with(context).load(int).into(imageView)
        return imageView
    }

    private fun initTitleStyle(textView: TextView, text: String): LayoutParams {
        val lp =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        textView.text = text
        textView.setTextAppearance(R.style.TextAppearance_MaterialComponents_Body2)
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.uifabric_white))
        textView.setTextColor(ContextCompat.getColor(context, R.color.uifabric_gray_700))
        textView.setPadding(
            DisplayTypedValueUtil.dip2px(16),
            DisplayTypedValueUtil.dip2px(8),
            DisplayTypedValueUtil.dip2px(16),
            0
        )

        return lp
    }

    private fun initEditStyle(editText: EditText, text: String): LayoutParams {
        val lp =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        editText.hint = text
        editText.setTextAppearance(R.style.TextAppearance_UIFabric_Heading)
        editText.maxLines = 1
        editText.setBackgroundColor(ContextCompat.getColor(context, R.color.uifabric_white))
        editText.setPadding(
            DisplayTypedValueUtil.dip2px(16),
            DisplayTypedValueUtil.dip2px(8),
            DisplayTypedValueUtil.dip2px(16),
            0
        )

        return lp

    }

    private fun initItemStyle(
        listItemView: ListItemView,
        title: String,
        foot: String,
        imageView: ImageView
    ): LayoutParams {
        val lp =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        listItemView.footer = foot
        listItemView.title = title
        listItemView.customView = imageView


        return lp
    }


}