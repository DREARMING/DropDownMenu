package com.mvcoder.filter.typeview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baiiu.filter.R;
import com.mvcoder.filter.adapter.BaseBaseAdapter;
import com.mvcoder.filter.adapter.SimpleTextAdapter;
import com.mvcoder.filter.util.CommonUtil;

import java.util.List;

/**
 * Created by baiiu on 15/12/17.
 * 双列ListView
 */
public class TripleListView<LEFTD, CENTERD, RIGHTD> extends LinearLayout implements AdapterView.OnItemClickListener {

    private BaseBaseAdapter<LEFTD> mLeftAdapter;
    private BaseBaseAdapter<RIGHTD> mRightAdapter;
    private BaseBaseAdapter<CENTERD> mCenterAdapter;
    private ListView lv_left;
    private ListView lv_right;
    private ListView lv_center;

    public TripleListView(Context context) {
        this(context, null);
    }

    public TripleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TripleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.dropdownmneu_triple_list, this);
        setBackgroundColor(Color.WHITE);
        lv_left = (ListView) findViewById(R.id.dropdown_triple_lv_left);
        lv_left.setBackgroundColor(Color.WHITE);
        lv_center = (ListView) findViewById(R.id.dropdown_triple_lv_center);
        lv_right = (ListView) findViewById(R.id.dropdown_triple_lv_right);
        lv_left.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv_center.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv_right.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lv_left.setBackgroundColor(Color.WHITE);
        lv_center.setBackgroundColor(Color.WHITE);
        lv_right.setBackgroundColor(Color.WHITE);

        lv_left.setOnItemClickListener(this);
        lv_center.setOnItemClickListener(this);
        lv_right.setOnItemClickListener(this);
    }


    public TripleListView<LEFTD, CENTERD, RIGHTD> leftAdapter(SimpleTextAdapter<LEFTD> leftAdapter) {
        mLeftAdapter = leftAdapter;
        lv_left.setAdapter(leftAdapter);
        return this;
    }

    public TripleListView<LEFTD, CENTERD, RIGHTD> rightAdapter(SimpleTextAdapter<RIGHTD> rightAdapter) {
        mRightAdapter = rightAdapter;
        lv_right.setAdapter(rightAdapter);
        return this;
    }

    public TripleListView<LEFTD, CENTERD, RIGHTD> centerAdapter(SimpleTextAdapter<CENTERD> centerAdapter) {
        mCenterAdapter = centerAdapter;
        lv_center.setAdapter(centerAdapter);
        return this;
    }

    public void setLeftList(List<LEFTD> list, int checkedPosition) {
        mLeftAdapter.setList(list);

        if (checkedPosition != -1) {
//            lv_left.performItemClick(mLeftAdapter.getView(checkedPositoin, null, null), checkedPositoin, mLeftAdapter.getItemId(checkedPositoin));//调用此方法相当于点击.第一次进来时会触发重复加载.
            lv_left.setItemChecked(checkedPosition, true);
        }
    }

    public void setRightList(List<RIGHTD> list, int checkedPosition) {
        mRightAdapter.setList(list);
        if (checkedPosition != -1) {
            lv_right.setItemChecked(checkedPosition, true);
        }
    }

    public void setCenterList(List<CENTERD> list, int checkedPosition) {
        mCenterAdapter.setList(list);
        if (checkedPosition != -1) {
            lv_center.setItemChecked(checkedPosition, true);
        }
    }


    private OnLeftItemClickListener<LEFTD, CENTERD> mOnLeftItemClickListener;
    private OnRightItemClickListener<CENTERD, RIGHTD> mOnRightItemClickListener;
    private OnCenterItemClickListener<CENTERD, RIGHTD> mOnCenterItemClickListener;


    public interface OnLeftItemClickListener<LEFTD, RIGHTD> {
        List<RIGHTD> onLeftItemClick(LEFTD leftAdapter, int position);
    }

    public interface OnRightItemClickListener<CENTERD, RIGHTD> {
        void onRightItemClick(CENTERD item, RIGHTD childItem, int position);
    }

    public interface OnCenterItemClickListener<CENTERD, RIGHTD> {
        List<RIGHTD> onCenterItemClick(CENTERD item, int position);
    }

    public TripleListView<LEFTD, CENTERD, RIGHTD> onLeftItemClickListener(OnLeftItemClickListener<LEFTD,CENTERD> onLeftItemClickListener) {
        this.mOnLeftItemClickListener = onLeftItemClickListener;
        return this;
    }

    public TripleListView<LEFTD, CENTERD, RIGHTD> onCenterItemClickListener(OnCenterItemClickListener<CENTERD,RIGHTD> onCenterItemClickListener) {
        this.mOnCenterItemClickListener = onCenterItemClickListener;
        return this;
    }

    public TripleListView<LEFTD, CENTERD, RIGHTD> onRightItemClickListener(OnRightItemClickListener<CENTERD, RIGHTD> onRightItemClickListener) {
        this.mOnRightItemClickListener = onRightItemClickListener;
        return this;
    }

    public ListView getLeftListView() {
        return lv_left;
    }

    public ListView getRightListView() {
        return lv_right;
    }

    public ListView getCenterListView() {
        return lv_center;
    }

    //========================点击事件===================================
    private int mRightLastChecked = -1;
    private int mLeftLastPosition = -1;
    private int mLeftLastCheckedPosition;

    private int mCenterLastPosition = -1;
    private int mCenterLastCheckedPosition;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (CommonUtil.isFastDoubleClick()) {
            return;
        }

        if (mLeftAdapter == null || mRightAdapter == null || mCenterAdapter == null) {
            return;
        }

        if (parent == lv_left) {
            //if(mLeftLastPosition == position) return;
            mCenterLastPosition = 0;
            mRightLastChecked = 0;
            mLeftLastPosition = position;
            if (mOnLeftItemClickListener != null) {
                LEFTD item = mLeftAdapter.getItem(position);

                List<CENTERD> centerdList = mOnLeftItemClickListener.onLeftItemClick(item, position);
                mCenterAdapter.setList(centerdList);
                lv_center.startLayoutAnimation();
                if (CommonUtil.isEmpty(centerdList)) {
                    //当前点的就是这个条目
                    mLeftLastCheckedPosition = -1;
                }else{
                    if(lv_right.getVisibility() == VISIBLE){
                        lv_right.setVisibility(INVISIBLE);
                    }
                }
            }

            lv_left.setItemChecked(position, true);
            lv_center.setItemChecked(0, true);
            //lv_center.setItemChecked(mCenterLastPosition, mLeftLastCheckedPosition == position);
        } else if(parent == lv_center) {
           // if(mCenterLastPosition == position) return;
            mRightLastChecked = 0;
            mCenterLastPosition = position;
            mLeftLastCheckedPosition = mLeftLastPosition;

            if(mOnCenterItemClickListener != null){
                CENTERD item = mCenterAdapter.getItem(position);
                List<RIGHTD> rightdList = mOnCenterItemClickListener.onCenterItemClick(item, position);
                mRightAdapter.setList(rightdList);
                lv_right.startLayoutAnimation();
                if(CommonUtil.isEmpty(rightdList)){
                    mCenterLastCheckedPosition = -1;
                }else{
                    if(lv_right.getVisibility() == INVISIBLE){
                        lv_right.setVisibility(VISIBLE);
                    }
                }
            }
            lv_right.setItemChecked(0, true);
            //lv_right.setItemChecked(mRightLastChecked, mCenterLastCheckedPosition == position);
        }else {
            //mCenterLastCheckedPosition = mCenterLastPosition;
            mRightLastChecked = position;

            if (mOnRightItemClickListener != null) {
                mOnRightItemClickListener.onRightItemClick(mCenterAdapter.getItem(mCenterLastPosition), mRightAdapter.getItem(mRightLastChecked), mRightLastChecked);
            }
        }
    }

    public int getmLeftLastPosition(){
        return mLeftLastPosition;
    }

    public int getmCenterLastPosition(){
        return mCenterLastPosition;
    }

    public int getmRightLastChecked(){
        return mRightLastChecked;
    }

}
