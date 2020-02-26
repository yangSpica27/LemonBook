package com.spica27.accountbook.activity

import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_allrecord.*

class AllRecordActivity() : BaseActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_allrecord

    private lateinit var recordViewModel: RecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rcv_all_record.layoutManager =
            FullyLinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv_all_record.addItemDecoration(
            ListItemDivider(this, DividerItemDecoration.VERTICAL)
        )

        recordViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(RecordViewModel::class.java)

        recordViewModel.allRecord?.observe(this, Observer {
            rcv_all_record.adapter = RecordAdapter(it, this)
        })


    }


}