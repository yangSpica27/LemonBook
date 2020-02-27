package com.spica27.accountbook.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class DisplayTypedValueUtil {
    private DisplayTypedValueUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px( int dipValue) {

        int scale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().getDisplayMetrics());
        return scale;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px( int spValue) {
        int fontScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().getDisplayMetrics());
        return fontScale;
    }

}
