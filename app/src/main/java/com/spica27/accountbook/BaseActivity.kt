package com.spica27.accountbook

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.spica27.accountbook.activity.MainActivity
import kotlinx.android.synthetic.main.activity_base_activity.*
import java.util.*

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        const val ACTIVITY_ID = "activity_id"
    }

    //内容layoutId
    protected abstract val contentLayoutId: Int
        @LayoutRes get//@LayoutRes 表明该参数、变量或者函数返回值应该是一个 layout 布局文件类型的资源

    //内容是否需要可滚动的容器
    protected open val contentNeedsScrollableContainer: Boolean
        get() = true

    //内容是否需要载入布局
    protected open val contentNeedsFindlayout: Boolean
        get() = true

    lateinit var container:ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_activity)
        //显示返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //设置标题
        title = if (intent.getSerializableExtra(ACTIVITY_ID) != null) {
            val activityID = intent.getSerializableExtra(ACTIVITY_ID) as UUID
            val activity = MYACTIVITYS.find { it.id == activityID }
            activity?.title
        } else {
            "账本"
        }

        //把页面加载进合适的布局当中（true：base_detail_scrollable_container/false：base_detail_container）
        container =
            if (contentNeedsScrollableContainer) base_detail_scrollable_container else base_detail_container
        if (contentNeedsFindlayout) {
            layoutInflater.inflate(contentLayoutId, container, true)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //回退到 Intent 指定的父 Activity
                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}