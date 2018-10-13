package com.mvcoder.dropdownmenu;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.mvcoder.filter.adapter.MenuAdapter;
import com.mvcoder.filter.adapter.SimpleTextAdapter;
import com.mvcoder.filter.interfaces.OnFilterDoneListener;
import com.mvcoder.filter.interfaces.OnFilterItemClickListener;
import com.mvcoder.filter.typeview.SingleListView;
import com.mvcoder.filter.util.UIUtil;
import com.mvcoder.filter.view.FilterCheckedTextView;

import java.util.List;
import java.util.Map;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private Map<Integer,List<String>> listMap;
    private int[] defaultIndexs;

    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    public DropMenuAdapter(Context context, String[] titles, Map<Integer,List<String>> listMap , OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.listMap = listMap;
        if(listMap == null || listMap.size() != titles.length){
            throw new IllegalArgumentException("title的条目必须跟listMap的大小一致");
        }
        defaultIndexs = new int[titles.length];
        this.onFilterDoneListener = onFilterDoneListener;
    }

    public DropMenuAdapter(Context context, String[] titles, Map<Integer,List<String>> listMap , int[] indexs, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.listMap = listMap;
        if(listMap == null || listMap.size() != titles.length){
            throw new IllegalArgumentException("title的条目必须跟listMap的大小一致");
        }
        if(indexs == null || indexs.length != titles.length){
            throw new IllegalArgumentException("选择下标数组长度必须跟listMap的大小一致");
        }
        this.defaultIndexs = indexs;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);
        if(listMap == null){
            throw new IllegalArgumentException("list map must not be null");
        }
        List<String> list = listMap.get(position);
        if(list == null || list.size() <= 0){
            throw new IllegalArgumentException("Position title is :" + position+ ", the list in this title must not be null and list's size must more than 0");
        }
        view = createSingleListView(position,list);
       /* switch (position) {
            case 0:
                view = createSingleListView(position,list);
                break;
            case 1:
                //view = createDoubleListView();
                List<String> roomList = new ArrayList<>();
                roomList.add("不限");
                roomList.add("房间A");
                roomList.add("会议室");
                view = createSingleListView(1,list);
                break;
            case 2:
                String[] publisherList = mContext.getResources().getStringArray(R.array.meeting_publish);
                view = createSingleListView(2,Arrays.asList(publisherList));
                break;
        }*/

        return view;
    }

    @Override
    public void setItemListMap(Map<Integer, List<String>> listMap) {
        if(listMap == null || listMap.size() != titles.length){
            throw new IllegalArgumentException("title的条目必须跟listMap的大小一致");
        }
        this.listMap = listMap;
    }

    private View createSingleListView(final int position, List<String> stringList) {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item ,int index) {
                        FilterUrl.instance().singleListPosition = item;
                        FilterUrl.instance().position = position;
                        FilterUrl.instance().positionTitle = item;
                        FilterUrl.instance().indexInList = index;

                        onFilterDone(position,item,item, index);
                    }
                });

        /*List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            list.add("" + i);
        }*/
        //String[] dateList = mContext.getResources().getStringArray(arrayId);
        singleListView.setList(stringList, defaultIndexs[position]);
        return singleListView;
    }


    private void onFilterDone(int position, String title, String item, int index) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(position, title, item, index);
        }
    }

}
