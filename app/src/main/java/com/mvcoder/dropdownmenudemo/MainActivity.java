package com.mvcoder.dropdownmenudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mvcoder.filter.DropDownMenu;
import com.mvcoder.filter.interfaces.OnFilterDoneListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnFilterDoneListener {

    private String[] titles = new String[]{"全部","附近","排序"};
    private String[] flavours = new String[]{"全部","甜品","粤菜","西餐"};
    private String[] areas = new String[]{"附近","天河区","越秀区","白云区","黄埔区"};
    private String[] sorts = new String[]{"智能排序","离我最近","好评优先","人气最高"};
    private Map<Integer,List<String>> map = new HashMap<>(titles.length);

    private DropDownMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.dropDownMenu);
        initData();
    }

    private void initData() {
        List<String> flavourList = Arrays.asList(flavours);
        map.put(0, flavourList);

        List<String> areasList = Arrays.asList(areas);
        map.put(1, areasList);

        List<String> sortList = Arrays.asList(sorts);
        map.put(2, sortList);


        menu.setMenuAdapter(new DropMenuAdapter(this, titles, map, this));


    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue, int selectedIndex) {
        List<String> list = map.get(position);
        String selectString = list.get(selectedIndex);
        Toast.makeText(this, selectString,Toast.LENGTH_SHORT).show();
        menu.setPositionIndicatorText(position, selectString);
        if(menu.isShowing())
            menu.close();
    }


}
