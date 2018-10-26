package com.github.boybeak.design;

import android.content.Context;

class Utils {
    public static int dpToPixel(Context context, float dp) {
        return (int)(context.getResources().getDisplayMetrics().density * dp);
    }
}
