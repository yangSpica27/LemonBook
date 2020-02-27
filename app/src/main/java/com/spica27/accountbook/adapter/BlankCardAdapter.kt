package com.spica27.accountbook.adapter

import android.content.Context
import android.util.Log
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.officeuifabric.util.DateStringUtils
import com.spica27.accountbook.db.bean.BlankCard
import com.spica27.accountbook.db.viewmodel.BlankCardViewModel
import com.spica27.accountbook.layout.BankCardLayout
import com.spica27.accountbook.utils.DisplayTypedValueUtil
import com.spica27.accountbook.view.ItemTouchCallBack
import java.util.*
import kotlin.math.log


class BlankCardAdapter(
    var items: List<BlankCard>,
    val context: Context,
    val itemClickListener: OnItemClickListener,
    private var blankCardViewModel: BlankCardViewModel
) :
    RecyclerView.Adapter<BlankCardAdapter.ViewHolder>(), View.OnClickListener,
    ItemTouchCallBack.OnItemTouchListener {

    var recyclerView: RecyclerView? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.bottomMargin=DisplayTypedValueUtil.dip2px(16)
        val view = BankCardLayout(context)
        view.layoutParams=lp
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("加载卡片","")
        holder.itemView.tag = position
        val card = items[position]
        holder.tvCardDate?.text = DateStringUtils.formatDateAbbrevAll(context, card.cardDate)
        holder.tvCardName?.text = card.cardName
        holder.tvCardNum?.tag = position
        holder.tvCardNum?.setText(card.cardNum)
        holder.tvCardNum?.isEnabled=false
        holder.tvCardNum?.setOnClickListener(this)
    }

    fun notifyData(items: List<BlankCard>) {
        this.items = items
        Log.e("数据重新载入","")
        notifyDataSetChanged()
    }

    public interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: BankCardLayout) : RecyclerView.ViewHolder(itemView) {
        val tvCardNum: EditText? = itemView.tvCardNum
        val tvCardName: TextView? = itemView.tvCardName
        val tvCardDate: TextView? = itemView.tvDate
    }

    override fun onClick(v: View?) {
        itemClickListener.onItemClick(v?.tag as Int)
    }

    override fun onSwiped(position: Int) {
        items.drop(position)
        blankCardViewModel.delete(items[position])
        notifyItemChanged(position)
    }

    override fun onMove(fromPosition: Int, toPosition: Int) {

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }
}