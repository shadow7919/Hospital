package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Doctor {
    private final static int MAX_NURSES = 2;
    private final static int MAX_SHIFTS = 3;
    private final ArrayList<Nurse> nurses = new ArrayList<>();
    private final ArrayList<ShiftTimeClass> doctorShift = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private Week daysShift;

    public static void showDay() {
        System.out.println("1 --> " + Week.SATURDAY);
        System.out.println("2 --> " + Week.SUNDAY);
        System.out.println("3 --> " + Week.MONDAY);
        System.out.println("4 --> " + Week.TUESDAY);
        System.out.println("5 --> " + Week.WEDNESDAY);
        System.out.println("6 --> " + Week.THURSDAY);
        System.out.println("7 --> " + Week.FRIDAY);
    }

    public static Doctor findDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        for (Doctor doctor : Hospital.getDoctors()) {
            if (inputId == doctor.id) {
                return doctor;
            }
        }
        System.out.println("Can't find ");
        return null;
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

    public void doctorMenu() {
        int option;
        while (true) {
            showMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    addShift();
                    break;
                case 3:
                    showDoctor(null);
                    break;
                case 4:
                    change();
                    break;
                case 5:
                    scanner.nextLine();
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void change() {
        Doctor doctor;
        doctor = findDoctor();
        if (doctor != null) {
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
                    removeDoctorShift(doctor);
                    break;
                case 3:
                    remove(doctor);
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
        System.out.println("-----------------------");
    }

    public void removeDoctorShift(Doctor doctor) {
        showDoctorShifts(doctor);
        System.out.println("------- CHOOSE ------");
        PartKind partKind = Room.whichPart();
        Week week = whichDay();
        ShiftsTime shiftsTime = chooseShift();
        ShiftTimeClass chosenShiftTimeClass = new ShiftTimeClass(week, shiftsTime, partKind, doctor);
        for (ShiftTimeClass shiftTimeClass : doctor.doctorShift) {
            if (chosenShiftTimeClass.equals(shiftTimeClass) && chosenShiftTimeClass.doctor.id == shiftTimeClass.doctor.id) {
                doctor.doctorShift.remove(shiftTimeClass);
                for (Nurse nurse : doctor.getNurses()) {
                    nurse.getNurseShift().remove(shiftTimeClass);
                }
                Hospital.getShiftsTimes().remove(shiftTimeClass);
                return;
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

    public boolean sameId(int inputId) {
        for (Doctor doctor : Hospital.getDoctors()) {
            if (doctor.id == inputId) {
                return true;
            }
        }
        return false;
    }

    public void addDoctor() {
        System.out.print("Enter id : ");
        int inputId = scanner.nextInt();
        while (sameId(inputId)) {
            System.out.println("Same id registered");
            inputId = scanner.nextInt();
        }
        scanner.nextLine();
        Doctor doctor = new Doctor();
        doctor.id = inputId;
        System.out.print("Enter name : ");
        doctor.name = scanner.nextLine();
        doctor.addNurse(doctor);
        doctor.pickPatient(doctor);
        Hospital.getDoctors().add(doctor);
    }

    private void pickPatient(Doctor doctor) {
        for (Patient myPatient : Hospital.getPatients()) {
            if (myPatient.getDoctor() == null) {
                myPatient.setDoctor(doctor);
                doctor.patients.add(myPatient);
                return;
            }
        }
    }

    private void addNurse(Doctor doctor) {
        for (Nurse myNurse : Hospital.getNurses()) {
            if (doctor.nurses.size() == MAX_NURSES) {
                break;
            }
            if (!myNurse.isPartSource() && myNurse.getDoctors().size() < MAX_NURSES) {
                doctor.nurses.add(myNurse);
                myNurse.getDoctors().add(doctor);
            }
        }
    }

    public void remove(Doctor doctor) {
        Hospital.getDoctors().remove(doctor);
        for (Nurse nurse : doctor.nurses) {
            nurse.getDoctors().remove(doctor);
        }
        for (ShiftTimeClass shiftTimeClass1 : doctor.doctorShift) {
            for (Nurse nurse : doctor.getNurses()) {
                nurse.getNurseShift().remove(shiftTimeClass1);
            }
        }
        for (Patient patient : doctor.patients) {
            patient.setDoctor(null);
        }
    }

    public void showDoctor(Doctor doctor) {
        if (doctor == null) {
            doctor = findDoctor();
        }
        if (doctor != null) {
            System.out.println("Name : " + doctor.name + "   Id : " + doctor.id);
            doctorInfo(doctor);
        }
    }

    private void doctorInfo(Doctor doctor) {
        while (true) {
            System.out.println("1 --> Nurses");
            System.out.println("2 --> Patients");
            System.out.println("3 --> Shifts");
            System.out.println("4 --> Back");
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
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
            System.out.println("-----------------------");
        }
    }

    public void showDoctorPatients(Doctor doctor) {
        if (doctor.patients.size() == 0) {
            System.out.println("No patient is registered");
            return;
        }
        System.out.println("------- Patient -------");
        for (Patient patient : doctor.patients) {
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

    public void showDoctorShifts(Doctor doctor) {
        if (doctor.doctorShift.size() == 0) {
            System.out.println("No shift is added ");
            return;
        }
        PartKind partKind = Room.whichPart();
        for (ShiftTimeClass shift : doctor.doctorShift) {
            if (shift.partKind.equals(partKind) && shift.doctor.id == doctor.id) {
                System.out.println(shift.shiftsTime + " --> " + shift.week);
            }
        }
    }

    public void addShift() {
        Doctor doctor = findDoctor();
        PartKind partKind;
        if (doctor != null) {
            partKind = Room.whichPart();
            if (checkDoctorShiftsNumber(doctor)) {
                System.out.println("Pick a day ");
                doctor.daysShift = whichDay();
                handleShift(doctor, partKind);
            }
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

    private void addShiftsToEveryWhere(Doctor doctor, ShiftTimeClass shiftTimeClass) {
        Hospital.getShiftsTimes().add(shiftTimeClass);
        doctor.doctorShift.add(shiftTimeClass);
        for (Nurse nurse : doctor.getNurses()) {
            nurse.getNurseShift().add(shiftTimeClass);
        }
    }

    private void handleShift(Doctor doctor, PartKind partKind) {
        ShiftTimeClass shiftTimeClass;
        showShift();
        int choose = scanner.nextInt();
        switch (choose) {
            case 1:
                shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.MORNING, partKind, doctor);
                if (isNotTaken(shiftTimeClass)) {
                    addShiftsToEveryWhere(doctor, shiftTimeClass);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            case 2:
                shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.AFTER_NOON, partKind, doctor);
                if (isNotTaken(shiftTimeClass)) {
                    addShiftsToEveryWhere(doctor, shiftTimeClass);
                } else {
                    System.out.println("This shift is already taken");
                }
                break;
            case 3:
                shiftTimeClass = new ShiftTimeClass(doctor.daysShift, ShiftsTime.NIGHT, partKind, doctor);
                if (isNotTaken(shiftTimeClass)) {
                    addShiftsToEveryWhere(doctor, shiftTimeClass);
                } else {
                    System.out.println("This shift is already taken ");
                }
                break;
            default:
                System.out.println("Wrong input ");
        }
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

    private boolean isNotTaken(ShiftTimeClass shiftTimeClass) {
        for (ShiftTimeClass registeredTimeClass : Hospital.getShiftsTimes()) {
            if (shiftTimeClass.equals(registeredTimeClass) && registeredTimeClass.doctor.id == shiftTimeClass.doctor.id) {
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