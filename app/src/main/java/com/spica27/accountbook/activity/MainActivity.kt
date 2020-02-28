package com.spica27.accountbook.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.icu.util.Calendar
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.microsoft.officeuifabric.bottomsheet.BottomSheet
import com.microsoft.officeuifabric.bottomsheet.BottomSheetItem
import com.microsoft.officeuifabric.drawer.DrawerDialog
import com.microsoft.officeuifabric.drawer.OnDrawerContentCreatedListener
import com.microsoft.officeuifabric.listitem.ListItemDivider
import com.microsoft.officeuifabric.search.Searchbar
import com.microsoft.officeuifabric.util.ThemeUtil
import com.microsoft.officeuifabric.util.getTintedDrawable
import com.spica27.accountbook.BaseActivity
import com.spica27.accountbook.MYACTIVITYS
import com.spica27.accountbook.R
import com.spica27.accountbook.adapter.RecordAdapter
import com.spica27.accountbook.db.bean.Record
import com.spica27.accountbook.db.repository.UserRepository
import com.spica27.accountbook.db.viewmodel.RecordViewModel
import com.spica27.accountbook.view.BarChartUtils
import com.spica27.accountbook.view.ButtonItem
import com.spica27.accountbook.view.FullyLinearLayoutManager
import kotlinx.android.synthetic.main.activity_base_activity.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_plan.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : BaseActivity(), View.OnClickListener, BottomSheetItem.OnClickListener,
    OnDrawerContentCreatedListener {

    object DimenUtils {

        fun dp2px(dp: Float): Float =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().displayMetrics
            )

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_all_record -> {
                val activity = MYACTIVITYS.find { it.activityClass == AllRecordActivity::class }
                val intent = Intent(this, AllRecordActivity::class.java)
                intent.putExtra(ACTIVITY_ID, activity?.id)
                startActivity(intent)
            }
            R.id.btn_all_chart -> {
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show()
            }
            R.id.container_plan -> {
                //点击预算
                dialog = DrawerDialog(this)
                dialog?.onDrawerContentCreatedListener = this
                dialog?.setContentView(R.layout.layout_plan)
                dialog?.show()
            }
            R.id.btn_save_plan -> {

                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    val user = userRepository.getUser()
                    user?.planMoney = drawerContent.et_planmoney.text.toString().toInt()
                    tv_hasmoney.text = "￥${user?.planMoney.toString()}"
                    if (user != null) {
                        userRepository.updateUser(user)
                    }
                    recordViewModel.sync()
                }
                dialog?.dismiss()
            }
        }
    }

    //设置布局
    override val contentLayoutId: Int
        get() = R.layout.activity_main

    //Menu对象
    private var optionsMenu: Menu? = null
        set(value) {
            field = value
            updateSearchbar()
            updateSearchbarFocus()
            updateSearchbarQuery()
        }
    //搜索框是否拥有焦点
    private var searchbarHasFocus: Boolean = false
        set(value) {
            field = value
            updateSearchbarFocus()
        }

    //是否展示搜素框
    private var searchbarIsActionMenuView: Boolean = false
        set(value) {
            field = value
            updateSearchbar()
        }


    private var searchbarQuery: String = ""
        set(value) {
            field = value
            updateSearchbarQuery()
        }

    //搜索栏
    private lateinit var searchbar: Searchbar

    private lateinit var searchbarButton: ButtonItem

    private var recordAdapter: RecordAdapter? = null

    //ViewModel
    private lateinit var recordViewModel: RecordViewModel

    private lateinit var userRepository: UserRepository


    //预算输入布局
    private lateinit var drawerContent: View
    private var dialog: DrawerDialog? = null

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recordViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(RecordViewModel::class.java)

        userRepository = UserRepository(application)

        recordAdapter = RecordAdapter(listOf(), this)
        //主页设置无返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        Log.d("进入", "")
        searchbarButton = ButtonItem(
            buttonText = resources.getString(R.string.app_bar_layout_searchbar_accessory_view_button),
            id = R.id.app_bar_layout_toggle_searchbar_type_button,
            onClickListener = this
        )


        recordViewModel.allRecord?.observe(this, Observer {
            //监听数据库变化
            Log.e("data数量：", it.size.toString())
            recordAdapter?.notifyDataChanged(it)

            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                //此步在IO线程中运行
                val incomes = recordViewModel.recordRepository.queryInCome()
                //此步在IO线程中运行
                val outComes = recordViewModel.recordRepository.queryOutCome()
                //此步在高性能运算线程中运行
                val barData = BarChartUtils.loadData(createEntrty(outComes), createEntrty(incomes))
                //IO线程获取用户信息
                val user = userRepository.getUser()
                //以上任务完成后回到主线程（UI线程）更新界面
                chart_linechart.data = barData
                //运算线程：获取已经消费的钱
                val outMoney = getMoney(outComes)
                tv_use.text = outMoney.toString()
                //运算线程：获取收入的钱
                val inMoney = getMoney(incomes)
                tv_income.text = "￥$inMoney"
                tv_hasmoney.text = "￥${user?.planMoney.toString()}"
                tv_hasday.text = "${getday()}天"
                val per = 100 - outMoney / user?.planMoney!! * 100
                tv_hasper.text = "${String.format("%.1f", per)}%"
                if (per > 0) {
                    tv_hasper.setTextColor(Color.parseColor("#005A9E"))
                } else {
                    tv_hasper.setTextColor(Color.parseColor("#F50057"))
                }


                chart_linechart.invalidate()


            }
        })
        initView()
    }


    private suspend fun getday(): Int = withContext(Dispatchers.Default) {
        val cal = Calendar.getInstance()
        val start = cal.get(Calendar.DAY_OF_MONTH)
        cal.set(Calendar.DATE, 1)      //把日期设置为当月第一天
        cal.roll(Calendar.DATE, -1)//回溯一天，到最后一天
        val end = cal.get(Calendar.DAY_OF_MONTH)
        return@withContext end - start
    }

    private suspend fun getMoney(list: List<Record>): Double = withContext(Dispatchers.Default) {
        var money = 0.0
        list.forEach {
            money += it.recordMoney
        }
        return@withContext money
    }

    private suspend fun createEntrty(list: List<Record>): List<Entry> =
        withContext(Dispatchers.Default) {
            val enterys: MutableList<Entry> = ArrayList()

            var index = 0
            list.forEach {
                enterys.add(Entry(index.toFloat(), it.recordMoney.toFloat()))
                index++
            }
            return@withContext enterys
        }


    //界面初始化
    private fun initView() {
        app_bar.accessoryView = null
        searchbar = createSearchbar()
        initChartStyle()
        //设置布局
        rcv_record.layoutManager =
            FullyLinearLayoutManager(this, RecyclerView.VERTICAL, false)
        //设置适配器
        rcv_record.adapter = recordAdapter
        //加分割线
        rcv_record.addItemDecoration(
            ListItemDivider(this, DividerItemDecoration.VERTICAL)
        )
        btn_all_chart.setOnClickListener(this)
        btn_all_record.setOnClickListener(this)
        container_plan.setOnClickListener(this)

    }


    private fun initChartStyle() {
        //加载样式
        BarChartUtils.initStyle(chart_linechart)
        //加载数据
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        searchbarQuery = searchbar.query.toString()
    }

    //添加Menu
    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //载入Menu
        menuInflater.inflate(R.menu.menu_app_bar_layout, menu)
        //获取Menu
        optionsMenu = menu
        //给图标上色
        for (index in 0 until menu!!.size()) {
            val drawable = menu.getItem(index).icon
            drawable?.setColorFilter(
                ThemeUtil.getThemeAttrColor(this, R.attr.uifabricToolbarIconColor),
                PorterDuff.Mode.SRC_IN
            )
        }
        return true
    }

    //Menu选择监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_bar_layout_action_search)
            (item.actionView as? Searchbar)?.requestSearchViewFocus()
        //是否展示搜索框
        //searchbarIsActionMenuView = !searchbarIsActionMenuView

        when (item.itemId) {
            R.id.action_add -> {
                //打开记账功能
                val activity = MYACTIVITYS.find { it.activityClass == NewRecordActivity::class }
                val intent = Intent(this, NewRecordActivity::class.java)
                intent.putExtra(ACTIVITY_ID, activity?.id)
                startActivity(intent)
            }
            R.id.action_more -> {
                createBottomSheet()
            }

        }

        return super.onOptionsItemSelected(item)
    }


    //更新搜索框
    private fun updateSearchbar() {
        if (app_bar == null)
            return
        searchbar.isActionMenuView = searchbarIsActionMenuView
        if (searchbarIsActionMenuView) {
            val optionsMenu = optionsMenu ?: return
            app_bar.accessoryView = null

            val searchIcon = getTintedDrawable(
                R.drawable.ms_ic_search_24_filled,
                ThemeUtil.getThemeAttrColor(this, R.attr.uifabricToolbarIconColor)
            )
            optionsMenu.add(
                R.id.app_bar_menu,
                R.id.app_bar_layout_action_search,
                0,
                getString(R.string.app_bar_layout_menu_search)
            )
                .setIcon(searchIcon)
                .setActionView(searchbar)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_ALWAYS)

            searchbarButton.buttonText =
                resources.getString(R.string.app_bar_layout_searchbar_accessory_view_button)
        } else {
            optionsMenu?.removeItem(R.id.app_bar_layout_action_search)
            app_bar.accessoryView = searchbar
            searchbarButton.buttonText =
                resources.getString(R.string.app_bar_layout_searchbar_action_view_button)
        }
    }

    //获取焦点
    private fun updateSearchbarFocus() {
        if (app_bar == null)
            return
        //如果没有，则获取
        if (searchbarHasFocus) {
            optionsMenu?.performIdentifierAction(R.id.app_bar_layout_action_search, 0)
            searchbar.requestSearchViewFocus()
        }
    }

    private fun updateSearchbarQuery() {
        if (app_bar == null)
            return
        searchbar.setQuery(searchbarQuery, false)
    }

    private fun createSearchbar(): Searchbar {
        val searchbar = Searchbar(ContextThemeWrapper(this, R.style.AppTheme))
        searchbar.onQueryTextFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            searchbarHasFocus = hasFocus
        }

        return searchbar
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when (item.id) {
            R.id.bottom_sheet_item_setting -> {
                Toast.makeText(this, "点击设置", Toast.LENGTH_LONG).show()
            }
            R.id.bottom_sheet_item_help -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            R.id.bottom_sheet_item_card -> {
                val activity = MYACTIVITYS.find { it.activityClass == BlankCardActivity::class }
                val intent = Intent(this, BlankCardActivity::class.java)
                intent.putExtra(ACTIVITY_ID, activity?.id)
                startActivity(intent)
            }
            R.id.bottom_sheet_item_sync -> {
                val activity = MYACTIVITYS.find { it.activityClass == BackupActivity::class }
                val intent = Intent(this, BackupActivity::class.java)
                intent.putExtra(ACTIVITY_ID, activity?.id)
                startActivity(intent)
            }
            R.id.bottom_sheet_item_snacks -> {

                Toast.makeText(this, "敬请期待", Toast.LENGTH_LONG).show()
            }
            R.id.bottom_sheet_item_code -> {
                Toast.makeText(this, "敬请期待", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun createBottomSheet() {
        val bottomSheet = BottomSheet.newInstance(
            arrayListOf(
                BottomSheetItem(
                    R.id.bottom_sheet_item_snacks,
                    R.drawable.ic_flag_24_filled,
                    "零食",
                    "您的帮助让开发者更加有积极"
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_sync,
                    R.drawable.ic_sync_24_filled,
                    "同步",
                    "该功能还未上线"
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_card,
                    R.drawable.ic_news_28_regular,
                    "卡片",
                    "您可以记录一些您的所思所想"
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_code,
                    R.drawable.ic_share_24_filled,
                    "开源",
                    "觉得不错可以给Star"
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_setting,
                    R.drawable.ic_settings_24_regular,
                    "设置",
                    "您可以在此进行一些自定义设置"
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_help,
                    R.drawable.ic_people_team_28_regular,
                    "帮助",
                    "想更加了解我们吗？"
                )
            )
        )
        bottomSheet.show(supportFragmentManager, null)

    }

    override fun onDrawerContentCreated(drawerContents: View) {
        drawerContent = drawerContents
        drawerContent.btn_save_plan.setOnClickListener(this)
    }


}


