package com.spica27.accountbook.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.officeuifabric.listitem.ListItemView
import com.microsoft.officeuifabric.popupmenu.PopupMenu
import com.microsoft.officeuifabric.popupmenu.PopupMenuItem
import com.microsoft.officeuifabric.util.DateStringUtils
import com.microsoft.officeuifabric.util.activity
import com.spica27.accountbook.R
import com.spica27.accountbook.db.bean.Record
import com.spica27.accountbook.db.repository.RecordRepository

class RecordAdapter(private var items: List<Record>, private val context: Context) :
    RecyclerView.Adapter<RecordAdapter.ViewHolder>(), View.OnClickListener,
    PopupMenuItem.OnClickListener {


    private val recordRepository: RecordRepository =
        RecordRepository(context.activity!!.application)

    var record: Record? = null


    override fun onClick(anchorView: View) {
        val position: Int = anchorView.tag as Int
        record = items[position]
        //定义menuItem的集合
        val popupMenuItems = arrayListOf(
            PopupMenuItem(R.id.menu_delete, "删除")
        )
        val popupMenu = PopupMenu(
            context,
            anchorView,
            popupMenuItems,
            PopupMenu.ItemCheckableBehavior.NONE
        )
        popupMenu.onItemClickListener = this
        popupMenu.show()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val itemView = ListItemView(parent.context)
        itemView.layoutParams = lp
        itemView.footerMaxLines = 4
        itemView.setOnClickListener(this)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = position
        val listItemView: ListItemView = holder.itemView as ListItemView
        Log.e("加载>>>", items[position].toString())
        if (items[position].isCome) {
            listItemView.customAccessoryView =
                createExampleTextView("+${items[position].recordMoney}", true)
        } else {
            listItemView.customAccessoryView =
                createExampleTextView("-${items[position].recordMoney}", false)
        }
        listItemView.title = items[position].recordType
        listItemView.footer =
            DateStringUtils.formatFullDateTime(context, items[position].recordDate * 1000)
        listItemView.subtitle = items[position].recordInfo.toString()
    }

    fun notifyDataChanged(items2: List<Record>) {
        items = items2
        notifyDataSetChanged()
    }

    private fun createExampleTextView(string: String = "", isHappy: Boolean = true): TextView {
        val textCustomAccessoryView = TextView(context)
        TextViewCompat.setTextAppearance(
            textCustomAccessoryView,
            R.style.TextAppearance_ListItemValue
        )
        textCustomAccessoryView.text = string

        if (isHappy) {
            textCustomAccessoryView.setTextColor(context.getColor(R.color.uifabric_communication_blue))
        } else {
            textCustomAccessoryView.setTextColor(Color.parseColor("#FF3D00"))
        }
        return textCustomAccessoryView
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: ListItemView) : RecyclerView.ViewHolder(view)

    override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
        if (record != null) {
            recordRepository.deleteRecord(record!!)
        }

    }
}

