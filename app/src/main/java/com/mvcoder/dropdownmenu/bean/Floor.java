package com.mvcoder.dropdownmenu.bean;

import java.util.List;

public class Floor {
    private int floorId;
    private String floorName;
    private ClassBuilding building;

    List<Room> roomList;

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public ClassBuilding getBuilding() {
        return building;
    }

    public void setBuilding(ClassBuilding building) {
        this.building = building;
    }
}
