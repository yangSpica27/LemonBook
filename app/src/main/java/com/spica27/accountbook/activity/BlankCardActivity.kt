package com.spica27.accountbook.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.spica27.accountbook.BaseActivity
import com.spica27.accountbook.MYACTIVITYS
import com.spica27.accountbook.R
import com.spica27.accountbook.adapter.BlankCardAdapter
import com.spica27.accountbook.db.bean.BlankCard
import com.spica27.accountbook.db.viewmodel.BlankCardViewModel
import com.spica27.accountbook.view.FullyLinearLayoutManager
import com.spica27.accountbook.view.ItemTouchCallBack
import kotlinx.android.synthetic.main.activity_blank_card.*

class BlankCardActivity : BaseActivity(), BlankCardAdapter.OnItemClickListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_blank_card

    private lateinit var blankCardAdapter: BlankCardAdapter
    private var items: List<BlankCard> = ArrayList()


    //viewModel
    private lateinit var blankCardViewModel: BlankCardViewModel

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
        rcv_blank_card.layoutManager = FullyLinearLayoutManager(this)
        blankCardAdapter = BlankCardAdapter(items, this, this, blankCardViewModel)
        val touchCallBack = ItemTouchCallBack()
        touchCallBack.setOnItemTouchListener(blankCardAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallBack)
        rcv_blank_card.adapter = blankCardAdapter
        itemTouchHelper.attachToRecyclerView(rcv_blank_card)
        btn_addcard.setOnClickListener {
            val activity = MYACTIVITYS.find { it.activityClass == NewBlankCardActivity::class }
            val intent = Intent(this, NewBlankCardActivity::class.java)
            intent.putExtra(ACTIVITY_ID, activity?.id)
            startActivity(intent)
        }
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


}