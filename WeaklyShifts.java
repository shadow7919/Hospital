package ir.ac.kntu;

import java.util.HashMap;
import java.util.Scanner;

enum Week {
    SATURDAY, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;
}

public class WeaklyShifts {
    Scanner scanner = new Scanner(System.in);
    private HashMap<Doctor, Nurse> saturdayShift = new HashMap<>();
    private HashMap<Doctor, Nurse> sundayShift = new HashMap<>();
    private HashMap<Doctor, Nurse> mondayShift = new HashMap<>();
    private HashMap<Doctor, Nurse> tuesdayShift = new HashMap<>();
    private HashMap<Doctor, Nurse> wednesdayShift = new HashMap<>();
    private HashMap<Doctor, Nurse> thursdayShift = new HashMap<>();
    private HashMap<Doctor, Nurse> fridayShift = new HashMap<>();

    public HashMap<Doctor, Nurse> getSaturdayShift() {
        return saturdayShift;
    }

    public void showShift() {
        System.out.println("Pick a day ");
        Doctor.showDay();
        Week week = Week.WEDNESDAY;
        String whatDay = scanner.nextLine();
        try {
            week = week.valueOf(whatDay);
            switch (week) {
                case SATURDAY:
                    printDayShift(saturdayShift);
                    break;
                case SUNDAY:
                    printDayShift(sundayShift);
                    break;
                case MONDAY:
                    printDayShift(mondayShift);
                    break;
                case TUESDAY:
                    printDayShift(tuesdayShift);
                    break;
                case WEDNESDAY:
                    printDayShift(wednesdayShift);
                    break;
                case THURSDAY:
                    printDayShift(thursdayShift);
                    break;
                case FRIDAY:
                    printDayShift(fridayShift);
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong day");
        }
    }

    private void printDayShift(HashMap<Doctor, Nurse> day) {
        for (Doctor doctor : day.keySet()) {
            System.out.print(doctor.getDoctorShift().iterator().next().shiftsTime + " of ");
            System.out.println(doctor.getDoctorShift().iterator().next().week);
        }
    }

    public boolean doctorHaveShiftThisDay(Doctor doctor, ShiftsTime shiftsTime, HashMap<Doctor, Nurse> dayShifts) {
        for (Doctor myDoctor : dayShifts.keySet()) {
            if (doctor.getId() == myDoctor.getId()) {
                for (ShiftTimeClass shiftTimeClass1 : doctor.getDoctorShift()) {
                    if (shiftTimeClass1.shiftsTime.equals(shiftsTime)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public HashMap<Doctor, Nurse> getSundayShift() {
        return sundayShift;
    }

    public HashMap<Doctor, Nurse> getMondayShift() {
        return mondayShift;
    }

    public HashMap<Doctor, Nurse> getTuesdayShift() {
        return tuesdayShift;
    }

    public HashMap<Doctor, Nurse> getWednesdayShift() {
        return wednesdayShift;
    }

    public HashMap<Doctor, Nurse> getThursdayShift() {
        return thursdayShift;
    }

    public HashMap<Doctor, Nurse> getFridayShift() {
        return fridayShift;
    }

}