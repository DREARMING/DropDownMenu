package com.mvcoder.dropdownmenu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mvcoder.dropdownmenu.bean.ClassBuilding;
import com.mvcoder.dropdownmenu.bean.Floor;
import com.mvcoder.dropdownmenu.bean.Room;
import com.mvcoder.filter.adapter.MenuAdapter;
import com.mvcoder.filter.adapter.SimpleTextAdapter;
import com.mvcoder.filter.interfaces.OnFilterDoneListener;
import com.mvcoder.filter.interfaces.OnFilterItemClickListener;
import com.mvcoder.filter.typeview.SingleGridView;
import com.mvcoder.filter.typeview.SingleListView;
import com.mvcoder.filter.typeview.TripleListView;
import com.mvcoder.filter.util.UIUtil;
import com.mvcoder.filter.view.FilterCheckedTextView;

import java.util.ArrayList;
import java.util.HashMap;
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
        switch (position) {
            case 0:
                view = createSingleListView(position,list);
                break;
            case 1:
                view = createTripleListView();
                break;
            case 2:
                view = createGridView();
                break;
            case 3:
                view = createGridView();
                break;
        }

        return view;
    }

    private View createGridView(){
        final Map<Long, ClassBuilding> map = new HashMap<>();
        final SingleGridView<ClassBuilding> gridView = new SingleGridView<ClassBuilding>(mContext);
        gridView.adapter(new SimpleTextAdapter<ClassBuilding>(null, mContext) {
                    @Override
                    public String provideText(ClassBuilding classBuilding) {
                        return classBuilding.getBuildingName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        //super.initCheckedTextView(checkedTextView);
                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }

                    @Override
                    protected void onBindViewHolder(int position, ClassBuilding item, FilterItemHolder holder) {
                        super.onBindViewHolder(position, item, holder);
                    }
                }).onItemClick(new OnFilterItemClickListener<ClassBuilding>() {
                    @Override
                    public void onItemClick(ClassBuilding classBuilding, int i) {
                        long buildingId = classBuilding.getId();
                        if(map.get(buildingId) == null){
                            map.put(buildingId, classBuilding);
                            //gridView.setItemChecked(i, true);
                        }else{
                            map.remove(buildingId);
                            //gridView.setItemChecked(i, false);
                        }
                        System.out.println("touch classBuilding : " + classBuilding.getBuildingName());
                    }
                });
        gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        gridView.setList(getBuildingList(), -1);

        Button button = new Button(mContext);
        button.setText("确定");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                for(Long key : map.keySet()){
                    builder.append(map.get(key).getBuildingName());
                    builder.append(",");
                }
                System.out.println("select : " + builder.toString());
                Toast.makeText(mContext, "queding", Toast.LENGTH_SHORT).show();
            }
        });

        int padding = UIUtil.dp(mContext, 15);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.BLUE);
        System.out.println("padding : " + padding);
        LinearLayout.LayoutParams buttonLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLP.setMargins(padding,padding,padding,padding);
        button.setLayoutParams(buttonLP);

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(gridView);
        linearLayout.addView(button);
        return linearLayout;
    }

 /*   private int mLeftSelectPos = 0;
    private int mCenterSelectPos = 0;
    private int mRightSelectPos = 0;*/

    private View createTripleListView() {
        List<ClassBuilding> buildingList = getBuildingList();
        TripleListView<ClassBuilding,Floor,Room> tripleListView = new TripleListView<ClassBuilding,Floor,Room>(mContext)
                .leftAdapter(new SimpleTextAdapter<ClassBuilding>(buildingList,mContext) {
                    @Override
                    public String provideText(ClassBuilding classBuilding) {
                        return classBuilding.getBuildingName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setBackground(null);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }

                    @Override
                    protected void onBindViewHolder(int position, ClassBuilding item, FilterItemHolder holder) {
                        super.onBindViewHolder(position, item, holder);
                    }
                }).centerAdapter(new SimpleTextAdapter<Floor>(null, mContext) {
                    @Override
                    public String provideText(Floor floor) {

                        return floor.getFloorName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setBackground(null);
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }

                }).rightAdapter(new SimpleTextAdapter<Room>(null, mContext) {
                    @Override
                    public String provideText(Room room) {
                        return room.getRoomName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setBackground(null);
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }

                }).onLeftItemClickListener(new TripleListView.OnLeftItemClickListener<ClassBuilding, Floor>() {
                    @Override
                    public List<Floor> onLeftItemClick(ClassBuilding leftAdapter, int position) {
                        List<Floor> floorList = leftAdapter.getFloorList();
                        if(floorList == null || floorList.size() == 0){
                            onFilterDone(1, leftAdapter.getBuildingName(),leftAdapter, -1);
                        }
                        return floorList;
                    }
                }).onCenterItemClickListener(new TripleListView.OnCenterItemClickListener<Floor, Room>() {
                    @Override
                    public List<Room> onCenterItemClick(Floor item, int position) {
                        List<Room> roomList = item.getRoomList();
                        if(roomList == null || roomList.size() == 0){
                            onFilterDone(1, item.getBuilding().getBuildingName(), item, -1);
                        }
                        return item.getRoomList();
                    }
                }).onRightItemClickListener(new TripleListView.OnRightItemClickListener<Floor, Room>() {
                    @Override
                    public void onRightItemClick(Floor item, Room childItem, int position) {
                        if(childItem == null || childItem.getRoomId()<= 0) {
                            onFilterDone(1, item.getFloorName(), item, -1);
                            return;
                        }
                        onFilterDone(1,childItem.getRoomName(), childItem, position);
                    }
                });
        tripleListView.setLeftList(buildingList, -1);
        return tripleListView;
    }

    private List<ClassBuilding> getBuildingList(){
        int buildingNum = 2;
        int floorNum = 4;
        int roomNum = 8;
        int floorId = 1;
        int roomId = 1;
        List<ClassBuilding> buildingList = new ArrayList<>();
        for(int i = 0 ; i < buildingNum; i++){
            ClassBuilding building = new ClassBuilding();
            building.setId(i+1);
            building.setBuildingName(building.getId() + "教");
            List<Floor> floorList = new ArrayList<>();
            boolean floorFlag = true;
            for(int j = 0; j <= floorNum; j++){
                Floor floor = new Floor();
                floor.setBuilding(building);
                if(floorFlag){
                    floor.setFloorId(-1);
                    floor.setFloorName("all");
                    floorFlag = false;
                }else {
                    floor.setFloorId(floorId++);
                    floor.setFloorName(j + "层");
                    boolean roomFlag = true;
                    List<Room> roomList = new ArrayList<>();
                    for (int k = 0; k <= roomNum; k++) {
                        Room room = new Room();
                        room.setFloor(floor);
                        if(roomFlag){
                            roomFlag = false;
                            room.setRoomId(-1);
                            room.setRoomName("all");
                        }else {
                            room.setRoomId(roomId++);
                            room.setRoomName(j + "0" + k);
                        }
                        roomList.add(room);
                    }
                    floor.setRoomList(roomList);
                }
                floorList.add(floor);
            }
            building.setFloorList(floorList);
            buildingList.add(building);
        }
        return buildingList;
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


    private <T> void onFilterDone(int position, String title, T item, int index) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(position, title, item, index);
        }
    }

}
