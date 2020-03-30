package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaklyShifts {
    private final String[] days = new String[]{
    "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private int[] daysShiftNumber = new int[]{0,0,0,0,0,0,0};
    private ArrayList<Map>SaturdayShift = new ArrayList<Map>();
    private ArrayList<Map>sundayShift = new ArrayList<Map>();
    private ArrayList<Map>mondayShift = new ArrayList<Map>();
    private ArrayList<Map>tuesdayShift = new ArrayList<Map>();
    private ArrayList<Map>wednesdayShift = new ArrayList<Map>();
    private ArrayList<Map>thursdayShift = new ArrayList<Map>();
    private ArrayList<Map>fridayShift = new ArrayList<Map>();
//    public List<List<String>> week = new ArrayList<List<String>>();
    private Shifts shifts;
//    {
//        for (int i = 0; i < 7; i++) {
//            week.add(new ArrayList<String>());
//        }
//    }

    public ArrayList<Map> getSaturdayShift() {
        return SaturdayShift;
    }

    public ArrayList<Map> getSundayShift() {
        return sundayShift;
    }

    public ArrayList<Map> getMondayShift() {
        return mondayShift;
    }

    public ArrayList<Map> getTuesdayShift() {
        return tuesdayShift;
    }

    public ArrayList<Map> getWednesdayShift() {
        return wednesdayShift;
    }

    public ArrayList<Map> getThursdayShift() {
        return thursdayShift;
    }

    public ArrayList<Map> getFridayShift() {
        return fridayShift;
    }
}
enum Shifts{
    Morning,afterNoon,night;
}