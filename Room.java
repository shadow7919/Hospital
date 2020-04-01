package ir. ac.kntu;

import java.util.*;

public class Room {
    private int roomNumber;
    private int bedsNumber ;
    private boolean isAvailable;
    private int normalPrice;
    private int emergencyPrice;

    public void addRoom(PartKind partKind,Hospital hospital){
        System.out.println("Enter the room number and beds Number");
        System.out.println("What's the main price of room");
        ArrayList<Map> rooms= new ArrayList<>();
        Map <Integer,Integer> tempRoom = new HashMap();
        if(partKind == PartKind.NORMAL){
            hospital.getNormalRooms().add(tempRoom);
        }else{
            hospital.getEmergencyRooms().add(tempRoom);
        }
    }

    public int getBedsNumber() {
        return roomNumber;
    }
    public void setBedsNumber(int bedsNumber){
        this.bedsNumber = bedsNumber;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

    public void setEmergencyPrice(int emergencyPrice) {
        this.emergencyPrice = emergencyPrice;
    }
}