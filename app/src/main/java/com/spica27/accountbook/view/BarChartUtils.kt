package com.spica27.accountbook.view

import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.spica27.accountbook.activity.MainActivity

object BarChartUtils {
    fun initStyle(lineChart: LineChart) {
        lineChart.setNoDataText("没有数据")
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.setVisibleXRangeMaximum(5f)
        val description = Description()
        description.text = ""
        lineChart.description = description
        //设置字体
        val font: Typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        lineChart.setNoDataTextTypeface(font)
        lineChart.setNoDataTextColor(Color.parseColor("#0078D4"))
        //设置图例
        val legend = lineChart.legend
        legend.formSize = MainActivity.DimenUtils.dp2px(2.5f) // 图例的图形大小
        legend.textSize = MainActivity.DimenUtils.dp2px(2.5f)// 图例的文字大小
        legend.setDrawInside(false) // 设置图例在图中
        legend.form=Legend.LegendForm.CIRCLE
        legend.orientation = Legend.LegendOrientation.HORIZONTAL // 图例的方向为垂直
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT //显示位置，水平右对齐
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP// 显示位置，垂直上对齐

        // 设置x轴
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false) // 将此设置为true，绘制该轴的网格线。
        xAxis.labelCount = 7  // 设置x轴上的标签个数
        xAxis.setDrawLabels(false)
        xAxis.granularity=1f
        xAxis.textSize = MainActivity.DimenUtils.dp2px(2f)// x轴上标签的大小

        //Y轴
        val yaxisLeft = lineChart.axisLeft
        yaxisLeft.isEnabled = false
        val yaxisRight = lineChart.axisRight
        yaxisRight.isEnabled = false
    }

    fun loadData(entry: List<Entry>, entry2: List<Entry>): LineData {
        val dadaist = LineDataSet(entry, "本月支出")
        dadaist.color = Color.parseColor("#FF9100")
        dadaist.valueTextColor = Color.parseColor("#303030")
        dadaist.valueTextSize = MainActivity.DimenUtils.dp2px(4f)
        dadaist.setDrawCircleHole(false)
        dadaist.lineWidth=2f
        dadaist.isHighlightEnabled=false
        dadaist.setDrawCircles(true)
        dadaist.mode=LineDataSet.Mode.CUBIC_BEZIER
        dadaist.setCircleColor(Color.parseColor("#FF9100"))
        val dadaist2 = LineDataSet(entry2, "本月收入")
        dadaist2.color = Color.parseColor("#0078D4")
        dadaist2.lineWidth=2f
        dadaist2.valueTextColor = Color.parseColor("#303030")
        dadaist2.valueTextSize = MainActivity.DimenUtils.dp2px(4f)
        dadaist2.setDrawCircleHole(false)
        dadaist2.isHighlightEnabled=false
        dadaist2.setCircleColor(Color.parseColor("#0078D4"))
        dadaist2.setDrawCircles(true)
        dadaist2.mode=LineDataSet.Mode.CUBIC_BEZIER




        val data = LineData(dadaist)
        data.addDataSet(dadaist2)







        return data


    }
}