package com.mvcoder.dropdownmenu.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.mvcoder.dropdownmenu.R;
import com.mvcoder.filter.view.FixedTabIndicator;

public class MyFixedTabIndicator extends FixedTabIndicator {

    public MyFixedTabIndicator(Context context) {
        super(context);
    }

    public MyFixedTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFixedTabIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void init() {
        super.init();
        setBackgroundColor(Color.TRANSPARENT);
        mTabDefaultColor = Color.WHITE;
        mTabSelectedColor = getResources().getColor(R.color.textSelectColor);
        drawableDividerLines = false;
        mLinePaint.setColor(Color.WHITE);
        mTabTextSize = 16;
    }


    @Override
    protected TextView getTextView() {
        TextView tv = new TextView(getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTabTextSize);
        tv.setTextColor(mTabDefaultColor);
        tv.setSingleLine();
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setMaxEms(10);//限制4个字符
        Drawable drawable = getResources().getDrawable(R.drawable.level_filter2);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        tv.setCompoundDrawablePadding(drawableRight);

        return tv;
    }
}
