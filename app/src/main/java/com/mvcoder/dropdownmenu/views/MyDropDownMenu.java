package com.mvcoder.dropdownmenu.views;

import android.content.Context;
import android.util.AttributeSet;

import com.mvcoder.dropdownmenu.R;
import com.mvcoder.filter.DropDownMenu;
import com.mvcoder.filter.view.FixedTabIndicator;

public class MyDropDownMenu extends DropDownMenu {

    public MyDropDownMenu(Context context) {
        super(context);
    }

    public MyDropDownMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void init(Context context) {
        setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark2));
    }

    @Override
    protected FixedTabIndicator getFixedTabIndicator() {
        return new MyFixedTabIndicator(getContext());
    }
}
