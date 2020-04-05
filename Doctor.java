package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void doctorMenu(Doctor doctor, Hospital hospital, WeaklyShifts weaklyShifts, Nurse nurse) {
        int option;
        while (true) {
            System.out.println("----------- Doctor's Menu -----------");
            System.out.println("1--> Add doctor");
            System.out.println("2--> Choose doctor's shift");
            System.out.println("3--> Doctor information");
            System.out.println("4--> Remove doctor");
            System.out.println("5--> change information");
            System.out.println("6. Back to previous menu");
            System.out.println("---------------------------------------");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addDoctor(hospital);
                    break;
                case 2:
                    addShift(hospital, weaklyShifts, nurse);
                    break;
                case 3:
                    showDoctor(hospital);
                    break;
                case 4:
                    remove(hospital);
                    break;
                case 5:
                    change(hospital);
                    break;
                case 6:
                    scanner.nextLine();
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void change(Hospital hospital) {
        Doctor doctor;
        System.out.print("Enter the id : ");
        doctor = findDoctor(hospital, scanner.nextInt());
        if (doctor == null) {
            System.out.println("No doctor with this id");
        } else {
            System.out.println("------- CHANGE --------");
            System.out.println("1 --> Name");
            System.out.println("2 --> Shift ");
            System.out.println("-----------------------");
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
        ArrayList<Doctor> chosenDay;
        System.out.println("Enter the doctor ID");
        inputId = scanner.nextInt();
        Doctor doctor = findDoctor(hospital, inputId);
        if (doctor != null) {
            if (checkDoctorShift(doctor)) {
                System.out.println("Pick a day ");
                chosenDay = whichDay(weaklyShifts, doctor);
                handleSHift(doctor, chosenDay, weaklyShifts, nurse);
            }
        } else {
            System.out.println("can't find this ID");
        }
        System.out.println("--------------------------------------");
    }

    private boolean checkDoctorShift(Doctor doctor) {
        if (doctor.doctorShift.size() == 5) {
            System.out.println(doctor.name + " already have 5 shifts");
            return false;
        } else {
            return true;
        }
    }

    private void handleSHift(Doctor doctor, ArrayList<Doctor> chosenDay, WeaklyShifts weaklyShifts, Nurse nurse) {
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
                    chosenDay.add(doctor);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            case 2:
                if (!isTaken(ShiftsTime.AFTER_NOON, doctor, weaklyShifts)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.AFTER_NOON);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.add(doctor);
                } else {
                    System.out.println("This shift is already taken");
                }
                break;
            case 3:
                if (!isTaken(ShiftsTime.NIGHT, doctor, weaklyShifts)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.NIGHT);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.add(doctor);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            default:
                System.out.println("Wrong input ");
                break;
        }
    }

    private ArrayList<Doctor> whichDay(WeaklyShifts weaklyShifts, Doctor doctor) {
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

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }
}