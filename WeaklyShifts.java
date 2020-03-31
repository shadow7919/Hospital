package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WeaklyShifts {
    private ArrayList<Map> saturdayShift = new ArrayList<Map>();
    private ArrayList<Map> sundayShift = new ArrayList<Map>();
    private ArrayList<Map> mondayShift = new ArrayList<Map>();
    private ArrayList<Map> tuesdayShift = new ArrayList<Map>();
    private ArrayList<Map> wednesdayShift = new ArrayList<Map>();
    private ArrayList<Map> thursdayShift = new ArrayList<Map>();
    private ArrayList<Map> fridayShift = new ArrayList<Map>();

    public ArrayList<Map> getSaturdayShift() {
        return saturdayShift;
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

    public void showShift(Scanner scanner) {
        System.out.println("Pick a day ");
        Doctor.showDay();
        Week week = Week.WEDNESDAY;
        String whatDay = scanner.nextLine();
        if (week.valueOf(whatDay) != null) {
            week = week.valueOf(whatDay);
            switch (week) {
                case SATURDAY:
                    printDayShift(saturdayShift);
                    System.out.println("Saturday");
                    break;
                case SUNDAY:
                    printDayShift(sundayShift);
                    System.out.println("Sunday");
                    break;
                case MONDAY:
                    printDayShift(mondayShift);
                    System.out.println("Monday");
                    break;
                case TUESDAY:
                    printDayShift(tuesdayShift);
                    System.out.println("Tuesday");
                    break;
                case WEDNESDAY:
                    printDayShift(wednesdayShift);
                    System.out.println("Wednesday");
                    break;
                case THURSDAY:
                    printDayShift(thursdayShift);
                    System.out.println("Thursday");
                    break;
                case FRIDAY:
                    printDayShift(fridayShift);
                    System.out.println("Friday");
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Wrong day");
        }
    }

    private void printDayShift(ArrayList<Map> day) {
        for (int i = 0; i < day.size(); i++) {
            System.out.print(day.get(i).keySet() + " and");
        }
        System.out.print("\b\b\b Of");
    }

    public boolean doctorHaveShiftThisDay(Doctor doctor, ArrayList<Map> getDayShift, ShiftsTime shiftsTime) {
        ArrayList<Set<Map.Entry<String, Integer>>> temp = new ArrayList<>();
        for (int i = 0; i < getDayShift.size(); i++) {
            temp.add(getDayShift.get(i).entrySet());
        }
        for (int i = 0; i < getDayShift.size(); i++) {
            if (doctor.getId() == temp.get(i).stream().iterator().next().getValue() &&
                    shiftsTime.name() == temp.get(i).stream().iterator().next().getKey()) {
                return true;
            }
        }
        return false;
    }

}

enum Week {
    SATURDAY, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;
}