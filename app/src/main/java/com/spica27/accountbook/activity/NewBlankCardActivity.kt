package com.spica27.accountbook.activity

import android.graphics.PorterDuff
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.microsoft.officeuifabric.util.ThemeUtil
import com.spica27.accountbook.BaseActivity
import com.spica27.accountbook.R
import com.spica27.accountbook.db.bean.BlankCard
import com.spica27.accountbook.db.repository.BlankCardRepository
import kotlinx.android.synthetic.main.activity_new_blank_card.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class NewBlankCardActivity : BaseActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_new_blank_card

    private lateinit var blankCardRepository: BlankCardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blankCardRepository = BlankCardRepository(application)
        initView()
    }

    private fun initView() {
        btn_save_card.setOnClickListener {
            if (et_card_name.text.toString().isEmpty() || et_card_num.text.toString().isEmpty()) {
                Toast.makeText(this, "请填入信息", Toast.LENGTH_SHORT).show()
            } else {
                val blankCard = BlankCard(
                    null,
                    et_card_name.text.toString(),
                    Calendar.getInstance().timeInMillis,
                    et_card_num.text.toString().trim()
                )
                val coroutineScope = CoroutineScope(Dispatchers.IO)
                coroutineScope.launch {
                    blankCardRepository.insert(blankCard)
                    finish()
                }
            }
        }
    }


}