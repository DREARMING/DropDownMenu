package com.mvcoder.filter.interfaces;

/**
 * author: baiiu
 * date: on 16/1/21 23:30
 * description:
 */
public interface OnFilterDoneListener<T> {
    void onFilterDone(int position, String positionTitle, T urlValue, int selectedIndex);
}