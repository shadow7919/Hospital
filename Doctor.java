package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Doctor {
    private final static int MAX_NURSES = 2;
    private final static int MAX_SHIFTS = 5;
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private ArrayList<Nurse> nurses = new ArrayList<>();
    private ArrayList<ShiftTimeClass> doctorShift = new ArrayList<>();
    private Week daysShift;
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Doctor> chosenDay = new ArrayList<>();

    public static void showDay() {
        System.out.println("1 --> " + Week.SATURDAY);
        System.out.println("2 --> " + Week.SUNDAY);
        System.out.println("3 --> " + Week.MONDAY);
        System.out.println("4 --> " + Week.TUESDAY);
        System.out.println("5 --> " + Week.WEDNESDAY);
        System.out.println("6 --> " + Week.THURSDAY);
        System.out.println("7 --> " + Week.FRIDAY);
    }

    private void showMenu() {
        System.out.println("----------- Doctor's Menu -----------");
        System.out.println("1--> Add doctor");
        System.out.println("2--> Choose doctor's shift");
        System.out.println("3--> Doctor information");
        System.out.println("4--> change information");
        System.out.println("5. Back to previous menu");
        System.out.println("---------------------------------------");
    }

    public void doctorMenu(WeaklyShifts weaklyShifts, Nurse nurse, Patient patient) {
        int option;
        while (true) {
            showMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addDoctor(patient, nurse);
                    break;
                case 2:
                    addShift(weaklyShifts);
                    break;
                case 3:
                    showDoctor();
                    break;
                case 4:
                    change(weaklyShifts, patient, nurse);
                    break;
                case 5:
                    scanner.nextLine();
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void change(WeaklyShifts weaklyShifts, Patient patient, Nurse nurse) {
        Doctor doctor;
        System.out.print("Enter the id : ");
        doctor = findDoctor(scanner.nextInt());
        if (doctor == null) {
            System.out.println("No doctor with this id");
        } else {
            System.out.println("------- CHANGE --------");
            System.out.println("1 --> Change Name");
            System.out.println("2 --> remove Shift ");
            System.out.println("3 --> remove Doctor");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Enter name : ");
                    doctor.name = scanner.nextLine();
                    break;
                case 2:
                    removeDoctorShift(doctor, weaklyShifts);
                    break;
                case 3:
                    remove(doctor, patient, nurse);
                    break;
                default:
                    System.out.println("Wrong input");
            }
            System.out.println("-----------------------");
        }
    }

    public void removeDoctorShift(Doctor doctor, WeaklyShifts weaklyShifts) {
        showDoctorShifts(doctor);
        Week week = chooseDay(weaklyShifts);
        if (week == null) {
            return;
        }
        ShiftsTime shiftsTime = chooseShift();
        if (shiftsTime == null) {
            return;
        }
        ShiftTimeClass chosenShiftTimeClass = new ShiftTimeClass(week, shiftsTime);
        System.out.println(chosenShiftTimeClass.shiftsTime + " of " + chosenShiftTimeClass.week);
        for (ShiftTimeClass shiftTimeClass1 : doctor.doctorShift) {
            if (chosenShiftTimeClass.shiftsTime == shiftTimeClass1.shiftsTime && chosenShiftTimeClass.week == shiftTimeClass1.week) {
                doctor.doctorShift.remove(shiftTimeClass1);
                chosenDay.removeIf(myDoctor -> myDoctor.id == doctor.id);
                return;
            }
        }
        System.out.println("There is no shift");
    }

    private ShiftsTime chooseShift() {
        showShift();
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                return ShiftsTime.MORNING;
            case 2:
                return ShiftsTime.AFTER_NOON;
            case 3:
                return ShiftsTime.NIGHT;
            default:
                System.out.println("Wrong input ");
                return null;
        }
    }

    private Week chooseDay(WeaklyShifts weaklyShifts) {
        System.out.println("Which day");
        showDay();
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                chosenDay = weaklyShifts.getSaturdayShift();
                return Week.SATURDAY;
            case 2:
                chosenDay = weaklyShifts.getSundayShift();
                return Week.SUNDAY;
            case 3:
                chosenDay = weaklyShifts.getMondayShift();
                return Week.MONDAY;
            case 4:
                chosenDay = weaklyShifts.getTuesdayShift();
                return Week.TUESDAY;
            case 5:
                chosenDay = weaklyShifts.getWednesdayShift();
                return Week.WEDNESDAY;
            case 6:
                chosenDay = weaklyShifts.getThursdayShift();
                return Week.THURSDAY;
            case 7:
                chosenDay = weaklyShifts.getFridayShift();
                return Week.FRIDAY;
            default:
                System.out.println("Wrong input ");
                return null;
        }
    }

    public void addDoctor(Patient patient, Nurse nurse) {
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        while (findDoctor(inputId) != null) {
            System.out.println("Same id registered");
            inputId = scanner.nextInt();
        }
        scanner.nextLine();
        Doctor doctor = new Doctor();
        doctor.id = inputId;
        System.out.print("Enter name : ");
        doctor.name = scanner.nextLine();
        doctor.addNurse(nurse);
        doctor.pickPatient(patient);
        Hospital.getDoctors().add(doctor);
    }

    private void pickPatient(Patient patient) {
        for (Patient myPatient : Hospital.getPatients()) {
            if (myPatient.getDoctor() == null) {
                patient.setDoctor(this);
                this.patients.add(myPatient);
                return;
            }
        }
    }

    private void addNurse(Nurse nurse) {
        for (Nurse myNurse : Hospital.getAllNurses()) {
            if (nurses.size() == MAX_NURSES) {
                break;
            }
            if (!myNurse.isPartSource() && myNurse.getDoctors().size() < MAX_NURSES) {
                this.nurses.add(myNurse);
                nurse.getDoctors().add(this);
            }
        }
    }

    public void remove(Doctor doctor, Patient patient, Nurse nurse) {
        if (doctor == null) {
            System.out.println("Cant find any doctor ");
            return;
        }
        Hospital.getDoctors().remove(doctor);
        nurse.getDoctors().remove(doctor);
        patient.setDoctor(null);
        System.out.println("Can't find doctor with this Id");
    }

    public void showDoctor() {
        System.out.print("Enter the id : ");
        int inputId = scanner.nextInt();
        Doctor doctor = findDoctor(inputId);
        if (doctor != null) {
            System.out.println("Name : " + doctor.name + "   Id : " + doctor.id);
            doctorInfo(doctor);
        } else {
            System.out.println("Can't find doctor with this Id");
        }
    }

    private void doctorInfo(Doctor doctor) {
        System.out.println("1 --> Nurses");
        System.out.println("2 --> Patients");
        System.out.println("3 --> Shifts");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                showDoctorNurses(doctor);
                break;
            case 2:
                showDoctorPatients(doctor);
                break;
            case 3:
                showDoctorShifts(doctor);
                break;
            default:
                System.out.println("Wrong input");
        }
    }

    public void showDoctorPatients(Doctor doctor) {
        if (doctor.patients.size() == 0) {
            System.out.println("No patient is registered");
            return;
        }
        System.out.println("------- Patient -------");
        for (Patient patient : doctor.getPatients()) {
            System.out.println("ID : " + patient.getId() + "\tName : " + patient.getName());
        }
    }

    private void showDoctorNurses(Doctor doctor) {
        if (doctor.nurses.size() == 0) {
            System.out.println("No nurse is registered");
            return;
        }
        System.out.println("------- Nurse -------");
        for (Nurse nurse : doctor.nurses) {
            System.out.println("ID : " + nurse.getId() + "\tName : " + nurse.getName());
        }
    }

    private void showDoctorShifts(Doctor doctor) {
        if (doctor.doctorShift.size() == 0) {
            System.out.println("No shift is added ");
            return;
        }
        for (ShiftTimeClass shift : doctor.doctorShift) {
            System.out.println(shift.shiftsTime + " of " + shift.week);
        }
    }

    private Doctor findDoctor(int inputId) {
        for (Doctor doctor : Hospital.getDoctors()) {
            if (inputId == doctor.id) {
                return doctor;
            }
        }
        return null;
    }

    public void addShift(WeaklyShifts weaklyShifts) {
        int inputId;
        ArrayList<Doctor> chosenDay;
        System.out.print("Enter the ID : ");
        inputId = scanner.nextInt();
        Doctor doctor = findDoctor(inputId);
        if (doctor != null) {
            if (checkDoctorShiftsNumber(doctor)) {
                System.out.println("Pick a day ");
                chosenDay = whichDay(weaklyShifts, doctor);
                handleShift(doctor, chosenDay);
            }
        } else {
            System.out.println("can't find this ID");
        }
        System.out.println("--------------------------------------");
    }

    private boolean checkDoctorShiftsNumber(Doctor doctor) {
        if (doctor.doctorShift.size() == MAX_SHIFTS) {
            System.out.println(doctor.name + " already have " + MAX_SHIFTS + " shifts");
            return false;
        } else {
            return true;
        }
    }

    private void showShift() {
        System.out.println("--------- pick a shift ---------");
        System.out.println("1 -> " + ShiftsTime.MORNING);
        System.out.println("2 -> " + ShiftsTime.AFTER_NOON);
        System.out.println("3 -> " + ShiftsTime.NIGHT);
    }

    private void handleShift(Doctor doctor, ArrayList<Doctor> chosenDay) {
        showShift();
        int choose = scanner.nextInt();
        switch (choose) {
            case 1:
                if (isNotTaken(ShiftsTime.MORNING, doctor)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.MORNING);
                    Hospital.getShiftsTimes().add(shiftTimeClass);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.add(doctor);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            case 2:
                if (isNotTaken(ShiftsTime.AFTER_NOON, doctor)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.AFTER_NOON);
                    Hospital.getShiftsTimes().add(shiftTimeClass);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.add(doctor);
                } else {
                    System.out.println("This shift is already taken");
                }
                break;
            case 3:
                if (isNotTaken(ShiftsTime.NIGHT, doctor)) {
                    ShiftTimeClass shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.NIGHT);
                    Hospital.getShiftsTimes().add(shiftTimeClass);
                    doctor.doctorShift.add(shiftTimeClass);
                    chosenDay.add(doctor);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            default:
                System.out.println("Wrong input ");
        }
    }

    private ArrayList<Doctor> whichDay(WeaklyShifts weaklyShifts, Doctor doctor) {
        showDay();
        int choose;
        while (true) {
            choose = scanner.nextInt();
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

    private boolean isNotTaken(ShiftsTime shiftsTime, Doctor doctor) {
        for (ShiftTimeClass shiftTimeClass : Hospital.getShiftsTimes()) {
            if (shiftsTime == shiftTimeClass.shiftsTime && shiftTimeClass.week == doctor.daysShift) {
                return false;
            }
        }
        return true;
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