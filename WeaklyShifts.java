package ir.ac.kntu;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Scanner;

public class WeaklyShifts {
    Scanner scanner = new Scanner(System.in);
    private ArrayList<Doctor> saturdayShift = new ArrayList<>();
    private ArrayList<Doctor> sundayShift = new ArrayList<>();
    private ArrayList<Doctor> mondayShift = new ArrayList<>();
    private ArrayList<Doctor> tuesdayShift = new ArrayList<>();
    private ArrayList<Doctor> wednesdayShift = new ArrayList<>();
    private ArrayList<Doctor> thursdayShift = new ArrayList<>();
    private ArrayList<Doctor> fridayShift = new ArrayList<>();


    public void showShift() {
        System.out.println("Pick a day ");
        Doctor.showDay();
        Week week;
        String whatDay = scanner.nextLine();
        try {
            week = Week.valueOf(whatDay);
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

    private void printDayShift(ArrayList<Doctor> day) {
        for (Doctor doctor : day) {
            System.out.print(doctor.getDoctorShift().iterator().next().shiftsTime + " of ");
            System.out.println(doctor.getDoctorShift().iterator().next().week);
        }
    }

    public boolean doctorHaveShiftThisDay(Doctor doctor, ShiftsTime shiftsTime, ArrayList<Doctor> dayShifts) {
        for (Doctor myDoctor : dayShifts) {
            if (doctor.getId() == myDoctor.getId()) {
                for (ShiftTimeClass shiftTimeClass : doctor.getDoctorShift()) {
                    if (shiftTimeClass.shiftsTime.equals(shiftsTime)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Doctor> getSaturdayShift() {
        return saturdayShift;
    }

    public ArrayList<Doctor> getSundayShift() {
        return sundayShift;
    }

    public ArrayList<Doctor> getMondayShift() {
        return mondayShift;
    }

    public ArrayList<Doctor> getTuesdayShift() {
        return tuesdayShift;
    }

    public ArrayList<Doctor> getWednesdayShift() {
        return wednesdayShift;
    }

    public ArrayList<Doctor> getThursdayShift() {
        return thursdayShift;
    }

    public ArrayList<Doctor> getFridayShift() {
        return fridayShift;
    }
}