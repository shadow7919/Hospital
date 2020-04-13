package ir.ac.kntu;

import java.util.Objects;
import java.util.Scanner;

public class ShiftTimeClass {
    public Week week;
    public ShiftsTime shiftsTime;
    public PartKind partKind;
    public Doctor doctor;
    Scanner scanner = new Scanner(System.in);

    public ShiftTimeClass(Week week, ShiftsTime shiftsTime, PartKind partKind, Doctor doctor) {
        this.week = week;
        this.shiftsTime = shiftsTime;
        this.partKind = partKind;
        this.doctor = doctor;
    }

    public ShiftTimeClass() {
    }

    public void showDay() {
        System.out.println("1 --> " + Week.SATURDAY);
        System.out.println("2 --> " + Week.SUNDAY);
        System.out.println("3 --> " + Week.MONDAY);
        System.out.println("4 --> " + Week.TUESDAY);
        System.out.println("5 --> " + Week.WEDNESDAY);
        System.out.println("6 --> " + Week.THURSDAY);
        System.out.println("7 --> " + Week.FRIDAY);
    }

    public Week whichDay() {
        showDay();
        int choose;
        while (true) {
            choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    return Week.SATURDAY;
                case 2:
                    return Week.SUNDAY;
                case 3:
                    return Week.MONDAY;
                case 4:
                    return Week.TUESDAY;
                case 5:
                    return Week.WEDNESDAY;
                case 6:
                    return Week.THURSDAY;
                case 7:
                    return Week.FRIDAY;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public ShiftsTime chooseShift() {
        int option;
        while (true) {
            showShift();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    return ShiftsTime.MORNING;
                case 2:
                    return ShiftsTime.AFTER_NOON;
                case 3:
                    return ShiftsTime.NIGHT;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    public void showShift() {
        System.out.println("--------- pick a shift ---------");
        System.out.println("1 -> " + ShiftsTime.MORNING);
        System.out.println("2 -> " + ShiftsTime.AFTER_NOON);
        System.out.println("3 -> " + ShiftsTime.NIGHT);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftTimeClass that = (ShiftTimeClass) o;
        return week == that.week &&
                shiftsTime == that.shiftsTime &&
                partKind == that.partKind;
    }

    public int hashCode() {
        return Objects.hash(week, shiftsTime, partKind);
    }
}