package ir. ac.kntu;

import java.nio.charset.IllegalCharsetNameException;
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
        HashMap<Integer,Integer> rooms= new HashMap();
        HashMap <Integer,Integer> tempRoom = new HashMap();
        if(partKind == PartKind.NORMAL){
//            hospital.getNormalRooms().put();
        }else{
//            hospital.getEmergencyRooms().put();
        }
    }

    public void setBedsNumber(int bedsNumber){
        this.bedsNumber = bedsNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void pickRoom(Hospital hospital,PartKind partKind){
        if(partKind == PartKind.NORMAL){
            showRoom(hospital,partKind);
        }
    }
    public void showRoom(Hospital hospital,PartKind partKind){
        if(partKind == PartKind.NORMAL){
            for (Map.Entry<Integer, Integer> i : hospital.getNormalRooms().entrySet()) {
                System.out.print("Room number :"+i.getKey());
                System.out.println("  and it's beds number "+i.getValue());
            }
        }else{
            for (Map.Entry<Integer, Integer> j :hospital.getEmergencyRooms().entrySet()) {
                System.out.print("Room number :"+j.getKey());
                System.out.println("  and it's beds number "+j.getValue());
            }
        }
        System.out.println(hospital.getNormalRooms());
    }
    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

    public void setEmergencyPrice(int emergencyPrice) {
        this.emergencyPrice = emergencyPrice;
    }
}