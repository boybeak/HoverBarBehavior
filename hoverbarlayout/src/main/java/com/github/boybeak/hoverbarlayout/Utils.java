package com.github.boybeak.hoverbarlayout;

import android.content.Context;

class Utils {
    public static int dpToPixel(Context context, float dp) {
        return (int)(context.getResources().getDisplayMetrics().density * dp);
    }
}
