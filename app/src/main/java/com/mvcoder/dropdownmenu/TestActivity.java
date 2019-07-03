package com.mvcoder.dropdownmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mvcoder.dropdownmenu.views.MyDropDownMenu;
import com.mvcoder.filter.interfaces.OnFilterDoneListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity implements OnFilterDoneListener{

    private MyDropDownMenu menu;

    private String[] titles = new String[]{"全部","地点"};
    private String[] flavours = new String[]{"全部","甜品","粤菜","西餐"};
    private String[] areas = new String[]{"附近","天河区","越秀区","白云区","黄埔区"};
    private String[] sorts = new String[]{"智能排序","离我最近","好评优先","人气最高"};
    private Map<Integer,List<String>> map = new HashMap<>(titles.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        menu = findViewById(R.id.dropDownMenu2);
    }

    private void initData() {

        menu.setMenuAdapter(new MyDropMenuAdapter(this, titles, this));


    }

    @Override
    public void onFilterDone(int position, String positionTitle, Object urlValue, int selectedIndex) {
        menu.setPositionIndicatorText(position, positionTitle);
        showToast(urlValue.toString());
        if(menu.isShowing())
            menu.close();
    }

    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
