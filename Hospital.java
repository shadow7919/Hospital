package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Hospital {
    private static final ArrayList<Doctor> doctors = new ArrayList<>();
    private static final ArrayList<Patient> patients = new ArrayList<>();
    private static final ArrayList<Room> normalRooms = new ArrayList<>();
    private static final ArrayList<Room> emergencyRooms = new ArrayList<>();
    private static final ArrayList<Nurse> nurses = new ArrayList<>();
    private static final ArrayList<ShiftTimeClass> shiftsTimes = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private String name;
    private ArrayList<MyDate> period = new ArrayList<>();

    public static ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public static ArrayList<ShiftTimeClass> getShiftsTimes() {
        return shiftsTimes;
    }

    public static ArrayList<Patient> getPatients() {
        return patients;
    }

    public static ArrayList<Room> getNormalRooms() {
        return normalRooms;
    }

    public static ArrayList<Room> getEmergencyRooms() {
        return emergencyRooms;
    }

    public static ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public void menu() {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    patientShow();
                    break;
                case 2:
                    doctorPatients();
                    break;
                case 3:
                    income();
                    break;
                case 4:
                    shiftOfWorker();
                    break;
                case 5:
                    ShiftsOfPart();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    public void printMenu() {
        System.out.println("---------- Hospital ----------");
        System.out.println("1 --> Patient");
        System.out.println("2 --> Doctor Patients");
        System.out.println("3 --> Hospital income ");
        System.out.println("4 --> Shifts Workers");
        System.out.println("5 --> Shifts Part");
        System.out.println("6 --> Back");
    }

    public void ShiftsOfPart() {
        boolean entered = false;
        PartKind partKind = Room.whichPart();
        for (ShiftTimeClass shiftTimeClass : Hospital.getShiftsTimes()) {
            if (shiftTimeClass.partKind == partKind) {
                entered = true;
                System.out.println(shiftTimeClass.shiftsTime + " --> " + shiftTimeClass.week);
            }
        }
        if (Hospital.getShiftsTimes().size() == 0) {
            System.out.println("No shift registered");
            return;
        }
        if (!entered) {
            System.out.println("No shift registered in part");
        }
    }

    public void shiftOfWorker() {
        int option;
        while (true) {
            System.out.println("1 --> Doctor");
            System.out.println("2 --> Nurse");
            System.out.println("3 --> Back");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    Doctor doctor = Doctor.findDoctor();
                    if (doctor != null) {
                        doctor.showDoctorShifts(doctor);
                    }
                    break;
                case 2:
                    Nurse nurse = new Nurse();
                    nurse = nurse.findNurse();
                    if (nurse != null) {
                        nurse.showShifts(nurse);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void income() {
        int totalIncome = 0;
        inputPeriod();
        for (Patient patient : Hospital.getPatients()) {
            if (MyDate.howLong(period.get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), period.get(1)) >= 0) {
                System.out.println(patient.getTotalPrice() + " from " + patient.getId());
                totalIncome += patient.getTotalPrice();
            }
        }
        period.clear();
        if (totalIncome == 0) {
            System.out.println("No income yet");
        } else {
            System.out.println(totalIncome);
        }
    }

    public void doctorPatients() {
        boolean entered = false;
        System.out.println("Enter id ");
        Doctor doctor = Doctor.findDoctor();
        if (doctor != null) {
            inputPeriod();
            for (Patient patient : doctor.getPatients()) {
                if (MyDate.howLong(period.get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), period.get(1)) >= 0) {
                    showPatientMainProperty(patient);
                    entered = true;
                }
            }
            period.clear();
            if (!entered) {
                System.out.println("No patient in period");
            }
        }
    }

    public void printPatientMenu() {
        System.out.println("-------  Patients -------");
        System.out.println("1 --> Patient in Part ");
        System.out.println("2 --> Patient Doctor");
        System.out.println("3 --> Patient Nurse ");
        System.out.println("4 --> Patients Hospitalized Period");
        System.out.println("5 --> Patients In Room");
        System.out.println("6 --> Discharged Patients");
        System.out.println("7 --> Back");
    }

    public void patientShow() {
        Room room = new Room();
        int option;
        while (true) {
            printPatientMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showPatientsPart();
                    break;
                case 2:
                    showPatientDoctor();
                    break;
                case 3:
                    showPatientNurse();
                    break;
                case 4:
                    hospitalizedPatient();
                    break;
                case 5:
                    showPatientRoom(room);
                    break;
                case 6:
                    showDischargedPatients();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void showDischargedPatients() {
        boolean entered = false;
        for (Patient patient : Hospital.getPatients()) {
            if (patient.isDischarge()) {
                entered = true;
                showPatientMainProperty(patient);
            }
        }
        if (Hospital.getPatients().size() == 0) {
            System.out.println("No Patient is registered");
            return;
        }
        if (!entered) {
            System.out.println("No patient is discharged");
        }
    }

    public void inputPeriod() {
        while (true) {
            System.out.println("Enter first period : ");
            period.add(MyDate.dateSet(false));
            System.out.println("Enter second period : ");
            period.add(MyDate.dateSet(false));
            if (MyDate.howLong(period.get(0), period.get(1)) > 0) {
                break;
            }
            System.out.println("wrong Dates");
        }
    }

    public void hospitalizedPatient() {
        boolean entered = false;
        inputPeriod();
        for (Patient patient : patients) {
            if (MyDate.howLong(period.get(0), patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), period.get(1)) >= 0) {
                entered = true;
                showPatientMainProperty(patient);
                System.out.print("From : ");
                MyDate.showDate(patient.getEntry());
                if (patient.isDischarge()) {
                    System.out.println("To :");
                    MyDate.showDate(patient.getDeparture());
                }
            }
        }
        period.clear();
        if (!entered) {
            System.out.println("No Patient in period");
        }
    }

    public void showPatientMainProperty(Patient patient) {
        System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
        System.out.println("Part : " + patient.getPartKind() + "\tRoom : " + patient.getRoom().getRoomNumber());
    }

    public void showPatientRoom(Room room) {
        PartKind partKind = Room.whichPart();
        Room myRoom = room.findRoom(partKind);
        for (Patient patient : myRoom.getPatients()) {
            showPatientMainProperty(patient);
        }
        if (myRoom.getPatients().size() == 0) {
            System.out.println("No patient in room");
        }
    }

    public void showPatientDoctor() {
        Patient patient = new Patient();
        patient = patient.checkId();
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        if (patient.getDoctor() != null) {
            patient.getDoctor().showDoctor(patient.getDoctor());
        } else {
            System.out.println("No doctor registered for this patient ");
        }
    }

    public void showPatientNurse() {
        Patient patient = new Patient();
        patient = patient.checkId();
        if (patient == null) {
            System.out.println("No patient Registered with this id");
            return;
        }
        if (patient.getNurses().size() == 0) {
            System.out.println("No Nurse registered for this patient ");
            return;
        }
        for (Nurse nurse : patient.getNurses()) {
            System.out.println("ID : " + nurse.getId() + "Name " + nurse.getName());
        }
    }

    public void showPatientsPart() {
        boolean entered = false;
        PartKind partKind = Room.whichPart();
        for (Patient patient : Hospital.getPatients()) {
            if (patient.getPartKind() == partKind) {
                entered = true;
                showPatientMainProperty(patient);
            }
        }
        if (!entered) {
            System.out.println("No patient in this part ");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

}