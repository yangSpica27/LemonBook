package com.spica27.accountbook.view

class ItemViewInfo(
    var top: Int,
    var scaleXY: Float,
    var positionOffset: Float,
    var layoutPercent: Float
) {
    private var mIsBottom = false
    fun setIsBottom(): ItemViewInfo {
        mIsBottom = true
        return this
    }

}