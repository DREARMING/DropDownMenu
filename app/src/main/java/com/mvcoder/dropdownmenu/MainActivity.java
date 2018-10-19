package com.mvcoder.dropdownmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mvcoder.dropdownmenu.bean.ClassBuilding;
import com.mvcoder.dropdownmenu.bean.Floor;
import com.mvcoder.dropdownmenu.bean.Room;
import com.mvcoder.filter.DropDownMenu;
import com.mvcoder.filter.interfaces.OnFilterDoneListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnFilterDoneListener {

    private String[] titles = new String[]{"全部","地点","排序"};
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
    public void onFilterDone(int position, String positionTitle, Object urlValue, int selectedIndex) {
        menu.setPositionIndicatorText(position, positionTitle);
        if(position == 0){
            String item = (String) urlValue;
            Toast.makeText(this, item,Toast.LENGTH_SHORT).show();
        }else if(position == 1){
            String selectItem = null;
            if(urlValue instanceof ClassBuilding){
                selectItem = ((ClassBuilding) urlValue).getBuildingName();
            }else if(urlValue instanceof Floor){
                selectItem = ((Floor) urlValue).getFloorName();
            }else if(urlValue instanceof Room){
                selectItem = ((Room) urlValue).getRoomName();
            }
            showToast(selectItem);
        }

        if(menu.isShowing())
            menu.close();
    }

    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
