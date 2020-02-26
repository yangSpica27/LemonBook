package com.spica27.accountbook.activity


import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.jakewharton.threetenabp.AndroidThreeTen
import com.microsoft.officeuifabric.datetimepicker.DateTimePickerDialog
import com.microsoft.officeuifabric.drawer.Drawer
import com.microsoft.officeuifabric.drawer.OnDrawerContentCreatedListener
import com.microsoft.officeuifabric.persona.IPersona
import com.microsoft.officeuifabric.persona.PersonaListView
import com.microsoft.officeuifabric.snackbar.Snackbar
import com.microsoft.officeuifabric.util.DateStringUtils
import com.microsoft.officeuifabric.widget.ProgressBar
import com.spica27.accountbook.*
import com.spica27.accountbook.db.bean.Record
import com.spica27.accountbook.db.repository.RecordRepository
import kotlinx.android.synthetic.main.activity_base_activity.*
import kotlinx.android.synthetic.main.activity_newrecord.*
import kotlinx.android.synthetic.main.drawer_budget_content.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime


@Suppress("DEPRECATION")
class NewRecordActivity() :
    BaseActivity(), DateTimePickerDialog.OnDateTimePickedListener, TabLayout.OnTabSelectedListener,
    TextWatcher, OnDrawerContentCreatedListener, PersonaListView.OnItemClickedListener {


    private var RECORD_TYPE = false
    private var RECORD_MONEY: Double = 0.0
    private var TIME :Long= 0


    //设置布局
    override val contentLayoutId: Int
        get() = R.layout.activity_newrecord

    private lateinit var drawerContent: View

    init {
        // ZoneDateTime所需的ThreeTenABP初始化
        AndroidThreeTen.init(this)
    }

    //日期选择监听
    override fun onDateTimePicked(dateTime: ZonedDateTime, duration: Duration) {
        selectTime = dateTime
    }

    //被选择的日期
    private var selectTime: ZonedDateTime? = null
        set(value) {
            if (value != null) {
                tv_date.text = DateStringUtils.formatAbbrevDateTime(this, value)
                TIME=value.toEpochSecond()

            }
        }


    private var dateTimePickerDialog: DateTimePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        dateTimePickerDialog?.dismiss()
        dateTimePickerDialog = null
    }

    private fun initView() {
        val tabOut = tab_type.newTab()
        tabOut.text = "支出"
        val tabIn = tab_type.newTab()
        tabIn.text = "收入"
        tab_type.addTab(tabOut, true)
        tab_type.addTab(tabIn, false)
        tab_type.addOnTabSelectedListener(this)
        createDateTimePickerDialog()
        et_money.addTextChangedListener(this)

        tv_date.setOnClickListener {
            dateTimePickerDialog?.show()
        }
        tv_type.setOnClickListener {
            val drawer = Drawer.newInstance(R.layout.drawer_budget_content)
            drawer.show(supportFragmentManager, null)
        }

        btn_save_record.setOnClickListener {
            if (selectTime == null) {
                selectTime = ZonedDateTime.now()
            }
            Log.e("时间:--->>", TIME.toString())

            if (RECORD_MONEY == 0.0) {
                Snackbar.make(root_view, "请设置金额", Snackbar.LENGTH_LONG, Snackbar.Style.REGULAR)
                    .setAction("好的", View.OnClickListener { }).show()
            } else {
                val recordRepository = RecordRepository(application)
                val circularProgress =
                    ProgressBar(this, null, 0, R.style.Widget_UIFabric_CircularProgress_Small)
                circularProgress.indeterminateDrawable.setColorFilter(
                    ContextCompat.getColor(this, R.color.uifabric_white),
                    PorterDuff.Mode.SRC_IN
                )
                val coroutineScope = CoroutineScope(Dispatchers.IO)
                coroutineScope.launch {
                    Snackbar.make(
                        root_view,
                        "添加数据中",
                        Snackbar.LENGTH_LONG
                    )
                        .setCustomView(circularProgress)
                        .show()
                    recordRepository.insertRecord(
                        Record(
                            null,
                            tv_type.text.toString(),
                            TIME,
                            RECORD_MONEY,
                            et_info.text.toString(),
                            RECORD_TYPE
                        )
                    )
                    finish()
                }
            }
        }
    }

    //初始化日历
    private fun createDateTimePickerDialog() {
        dateTimePickerDialog = DateTimePickerDialog(
            this,
            DateTimePickerDialog.Mode.DATE_TIME,
            DateTimePickerDialog.DateRangeMode.NONE,
            selectTime ?: ZonedDateTime.now(),
            Duration.ZERO
        )
        dateTimePickerDialog?.onDateTimePickedListener = this
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.e("选择Tab", tab?.position.toString())
        RECORD_TYPE = tab?.text != "支出"
        if (RECORD_TYPE) {
            Log.e("Tab", "收入")
            getInComeTypeList(this)

        } else {
            Log.e("Tab", "支出")
            getOutTypeList(this)
        }

    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        RECORD_MONEY = if (et_money.text.toString().isNotEmpty()) {
            Log.e("输入金额：", et_money.text.toString().toDouble().toString())
            et_money.text.toString().toDouble()
        } else {
            0.0
        }

    }

    override fun onDrawerContentCreated(drawerContents: View) {
        drawerContent = drawerContents
        drawerContent.drawer_budget_type_list.personas = getAllTypeList()
        drawerContent.drawer_budget_type_list.onItemClickedListener = this
    }

    override fun onItemClicked(persona: IPersona) {
        tv_type.text = persona.name
    }


}


