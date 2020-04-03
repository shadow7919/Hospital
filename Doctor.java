package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

enum ShiftsTime {
    MORNING, AFTER_NOON, NIGHT;
}

public class Doctor {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private ArrayList<Nurse> nurses = new ArrayList<>();
    private ArrayList<ShiftTimeClass> doctorShift = new ArrayList<>();
    private Week daysShift;
    private ArrayList<Patient> patients = new ArrayList<>();

    public static void showDay() {
        System.out.println("1 --> " + Week.SATURDAY);
        System.out.println("2 --> " + Week.SUNDAY);
        System.out.println("3 --> " + Week.MONDAY);
        System.out.println("4 --> " + Week.TUESDAY);
        System.out.println("5 --> " + Week.WEDNESDAY);
        System.out.println("6 --> " + Week.THURSDAY);
        System.out.println("7 --> " + Week.FRIDAY);
    }

    public static void doctorMenu(Scanner scanner, Doctor doctor, Hospital hospital, WeaklyShifts weaklyShifts, Nurse nurse) {
        int option = 0;
        while (true) {
            System.out.println("----------- Doctor's Menu -----------");
            System.out.println("1. Add doctor");
            System.out.println("2. Choose doctor's shift");
            System.out.println("3. Doctor information");
            System.out.println("4. Remove doctor");
            System.out.println("5. Back to previous menu");
            System.out.println("---------------------------------------");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    doctor.addDoctor(hospital);
                    break;
                case 2:
                    doctor.addShift(hospital, weaklyShifts, nurse);
                    break;
                case 3:
                    doctor.showDoctor(hospital);
                    break;
                case 4:
                    doctor.remove(hospital);
                    break;
                case 5:
                    scanner.nextLine();
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void addDoctor(Hospital hospital) {
        Doctor tempDoctor = new Doctor();
        System.out.println("Enter the name and id");
        tempDoctor.name = scanner.next();
        tempDoctor.id = scanner.nextInt();
        if (hospital.sameId(tempDoctor)) {
            System.out.println("We have the same Id added");
            return;
        }
        hospital.setDoctors(tempDoctor);
//        chooseNurse();
    }

    public void remove(Hospital hospital) {
        System.out.println("Enter the id");
        int inputId = scanner.nextInt();
        for (int i = 0; i < hospital.getDoctors().size(); i++) {
            if (inputId == hospital.getDoctors().get(i).id) {
                hospital.getDoctors().remove(i);
                return;
            }
        }
        System.out.println("Can't find doctor with this Id");
    }

    public void showDoctor(Hospital hospital) {
        System.out.println("Enter the id");
        int inputId = scanner.nextInt();
        if (findDoctor(hospital, inputId) != null) {
            doctorInfo(findDoctor(hospital, inputId));
        } else {
            System.out.println("Can't find doctor with this Id");
        }
    }

    private Doctor findDoctor(Hospital hospital, int inputId) {
        for (Doctor doctor : hospital.getDoctors()) {
            if (inputId == doctor.id) {
                return doctor;
            }
        }
        return null;
    }

    private void doctorInfo(Doctor doctor) {
        System.out.println("Name : " + doctor.name + "   Id : " + doctor.id);
        if (doctor.doctorShift.size() != 0) {
            for (ShiftTimeClass shift : doctor.doctorShift) {
                System.out.println(shift.shiftsTime + " of " + shift.week);
            }
        } else {
            System.out.println("No shift is added ");
        }
    }

    public void addShift(Hospital hospital, WeaklyShifts weaklyShifts, Nurse nurse) {
        int inputId;
        HashMap<Doctor, Nurse> chosenDay;
        System.out.println("Enter the doctor ID");
        inputId = scanner.nextInt();
        if (findDoctor(hospital, inputId) != null) {
            if (checkDoctorShift(findDoctor(hospital, inputId))) {
                System.out.println("Pick a day ");
                chosenDay = whichDay(weaklyShifts, findDoctor(hospital, inputId));
                handleSHift(findDoctor(hospital, inputId), chosenDay, weaklyShifts, nurse);
            }
        } else {
            System.out.println("can't find this ID");
        }
        System.out.println("--------------------------------------");
    }

    private boolean checkDoctorShift(Doctor doctor) {
        if (doctor.doctorShift.size() == 5) {
            System.out.println(doctor.name +" already have 5 shifts");
            return false;
        } else {
            return true;
        }
    }

    private void handleSHift(Doctor doctor, HashMap<Doctor, Nurse> chosenDay, WeaklyShifts weaklyShifts, Nurse nurse) {
        System.out.println("--------- pick a shift ---------");
        System.out.println("1 -> " + ShiftsTime.MORNING);
        System.out.println("2 -> " + ShiftsTime.AFTER_NOON);
        System.out.println("3 -> " + ShiftsTime.NIGHT);
        int choose = scanner.nextInt();
        switch (choose) {
            case 1:
                if (!isTaken(ShiftsTime.MORNING, doctor, weaklyShifts)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.MORNING);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.put(doctor, nurse);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            case 2:
                if (!isTaken(ShiftsTime.AFTER_NOON, doctor, weaklyShifts)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.AFTER_NOON);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.put(doctor, nurse);
                } else {
                    System.out.println("This shift is already taken");
                }
                break;
            case 3:
                if (!isTaken(ShiftsTime.NIGHT, doctor, weaklyShifts)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.NIGHT);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.put(doctor, nurse);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            default:
                break;
        }
    }

    private HashMap<Doctor, Nurse> whichDay(WeaklyShifts weaklyShifts, Doctor doctor) {
        showDay();
        int choose = scanner.nextInt();
        while (true) {
            switch (choose) {
                case 1:
                    doctor.daysShift = Week.SATURDAY;
                    return weaklyShifts.getSaturdayShift();
                case 2:
                    doctor.daysShift = Week.SUNDAY;
                    return weaklyShifts.getSundayShift();
                case 3:
                    doctor.daysShift = Week.MONDAY;
                    return weaklyShifts.getMondayShift();
                case 4:
                    doctor.daysShift = Week.TUESDAY;
                    return weaklyShifts.getTuesdayShift();
                case 5:
                    doctor.daysShift = Week.WEDNESDAY;
                    return weaklyShifts.getWednesdayShift();
                case 6:
                    doctor.daysShift = Week.THURSDAY;
                    return weaklyShifts.getThursdayShift();
                case 7:
                    doctor.daysShift = Week.FRIDAY;
                    return weaklyShifts.getFridayShift();
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private boolean isTaken(ShiftsTime shiftsTime, Doctor doctor, WeaklyShifts weaklyShifts) {
        switch (doctor.daysShift) {
            case SATURDAY:
                if (weaklyShifts.doctorHaveShiftThisDay(doctor, shiftsTime, weaklyShifts.getSaturdayShift()))
                    return true;
            case SUNDAY:
                if (weaklyShifts.doctorHaveShiftThisDay(doctor, shiftsTime, weaklyShifts.getSundayShift()))
                    return true;
            case MONDAY:
                if (weaklyShifts.doctorHaveShiftThisDay(doctor, shiftsTime, weaklyShifts.getMondayShift()))
                    return true;
            case TUESDAY:
                if (weaklyShifts.doctorHaveShiftThisDay(doctor, shiftsTime, weaklyShifts.getTuesdayShift()))
                    return true;
            case WEDNESDAY:
                if (weaklyShifts.doctorHaveShiftThisDay(doctor, shiftsTime, weaklyShifts.getWednesdayShift()))
                    return true;
            case THURSDAY:
                if (weaklyShifts.doctorHaveShiftThisDay(doctor, shiftsTime, weaklyShifts.getThursdayShift()))
                    return true;
            case FRIDAY:
                if (weaklyShifts.doctorHaveShiftThisDay(doctor, shiftsTime, weaklyShifts.getFridayShift()))
                    return true;
            default:
        }
        return false;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<ShiftTimeClass> getDoctorShift() {
        return doctorShift;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Week getDaysShift() {
        return daysShift;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }
}