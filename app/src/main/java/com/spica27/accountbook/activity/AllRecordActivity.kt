package com.spica27.accountbook.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.officeuifabric.listitem.ListItemDivider
import com.spica27.accountbook.BaseActivity
import com.spica27.accountbook.R
import com.spica27.accountbook.adapter.RecordAdapter
import com.spica27.accountbook.db.viewmodel.RecordViewModel
import com.spica27.accountbook.view.FullyLinearLayoutManager


class AllRecordActivity : BaseActivity() {

    override val contentLayoutId: Int
        get() = -1

    //禁用xml布局
    override val contentNeedsFindlayout: Boolean
        get() = false

    private lateinit var recordViewModel: RecordViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        recyclerView = RecyclerView(this)
        recyclerView.id = R.id.layout_all_record
        container.addView(recyclerView, lp)




        recyclerView.layoutManager =
            FullyLinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(
            ListItemDivider(this, DividerItemDecoration.VERTICAL)
        )

        recordViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(RecordViewModel::class.java)

        recordViewModel.allRecord?.observe(this, Observer {
            recyclerView.adapter = RecordAdapter(it, this)
        })


    }


}