package com.spica27.accountbook.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.officeuifabric.util.ThemeUtil
import com.spica27.accountbook.BaseActivity
import com.spica27.accountbook.MYACTIVITYS
import com.spica27.accountbook.R
import com.spica27.accountbook.adapter.BlankCardAdapter
import com.spica27.accountbook.db.bean.BlankCard
import com.spica27.accountbook.db.viewmodel.BlankCardViewModel
import com.spica27.accountbook.utils.DisplayTypedValueUtil
import com.spica27.accountbook.view.FullyLinearLayoutManager
import com.spica27.accountbook.view.ItemTouchCallBack


@Suppress("DEPRECATION")
class BlankCardActivity : BaseActivity(), BlankCardAdapter.OnItemClickListener {
    override val contentLayoutId: Int
        get() = -1

    private lateinit var blankCardAdapter: BlankCardAdapter
    private var items: List<BlankCard> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    //viewModel
    private lateinit var blankCardViewModel: BlankCardViewModel

    companion object {
        val padding = DisplayTypedValueUtil.dip2px(16)
    }

    //禁用Xml布局
    override val contentNeedsFindlayout: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blankCardViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(BlankCardViewModel::class.java)
        initView()

        blankCardViewModel.allBlankCard?.observe(this, Observer {
            items = it
            Log.e("卡片数量-->", it.size.toString())
            blankCardAdapter.notifyData(items)
        })

    }


    private fun initView() {
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        recyclerView = RecyclerView(this)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.id=R.id.layout_all_card
        recyclerView.setPadding(padding, padding, padding, padding)

        container.addView(recyclerView, lp)
        recyclerView.layoutManager = FullyLinearLayoutManager(this)
        blankCardAdapter = BlankCardAdapter(items, this, this, blankCardViewModel)
        val touchCallBack = ItemTouchCallBack()
        touchCallBack.setOnItemTouchListener(blankCardAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallBack)
        recyclerView.adapter = blankCardAdapter
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onItemClick(position: Int) {
        copyText(this, items[position].cardNum)
    }

    private fun copyText(context: Context, text: String) {
        Log.e("复制到剪切板:", text)
        val cmd: ClipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmd.setPrimaryClip(ClipData.newPlainText(null, text))
        Toast.makeText(context, "卡号已经复制至剪切板", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_card_appbar_layout, menu)

        for (index in 0 until menu.size()) {
            val drawable = menu.getItem(index).icon
            drawable?.setColorFilter(
                ThemeUtil.getThemeAttrColor(this, R.attr.uifabricToolbarIconColor),
                PorterDuff.Mode.SRC_IN
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_card -> {
                val activity = MYACTIVITYS.find { it.activityClass == NewBlankCardActivity::class }
                val intent = Intent(this, NewBlankCardActivity::class.java)
                intent.putExtra(ACTIVITY_ID, activity?.id)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


}