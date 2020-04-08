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
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    public void patientShow() {
        Room room = new Room();
        System.out.println("-------  Patients -------");
        int option;
        while (true) {
            System.out.println("1 --> Patient in Part ");
            System.out.println("2 --> Get Id of Patient doctor");
            System.out.println("3 --> Patients hospitalized period");
            System.out.println("4 --> Patients in room");
            System.out.println("5 --> Back");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showPatientsPart();
                    break;
                case 2:
                    showPatientDoctor();
                    break;
                case 3:
                    hospitalizedPatient();
                    break;
                case 4:
                    showPatientRoom(room);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void hospitalizedPatient() {
        MyDate first;
        MyDate second;
        while (true) {
            System.out.println("Enter first period : ");
            first = MyDate.dateSet(false);
            System.out.println("Enter second period : ");
            second = MyDate.dateSet(false);
            if (MyDate.howLong(first, second) > 0) {
                break;
            }
            System.out.println("wrong Dates");
        }
        for (Patient patient : patients) {
            if (MyDate.howLong(first, patient.getEntry()) >= 0 && MyDate.howLong(patient.getEntry(), second) >= 0) {
                showPatientMainProperty(patient);
                System.out.print("From : ");
                MyDate.showDate(patient.getEntry());
                if (patient.isDischarge()) {
                    System.out.println("To :");
                    MyDate.showDate(patient.getDeparture());
                }
            }
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
            System.out.println("No doctor registered");
        }
    }

    public void showPatientsPart() {
        System.out.println("Part");
        PartKind partKind = Room.whichPart();
        for (Patient patient : Hospital.getPatients()) {
            if (patient.getPartKind() == partKind) {
                showPatientMainProperty(patient);
            }
        }
    }

    public void notUse() {
        System.out.println("1 --> All Doctors");
        System.out.println("2 --> All Shifts");
        System.out.println("3 --> Undischarged Patients");//with part
        System.out.println("4 --> Discharged Patients");// with part
        System.out.println("5 --> All Nurses");// in partSource
        System.out.println("6 --> Show Rooms ");// available , part
        System.out.println("7 --> Show patient's doctor");
        System.out.println("8 --> Show doctor's patients");
        System.out.println("9 --> All hospital income");
        System.out.println("10 --> Patients in Room ");
        System.out.println("11 --> tarikhche patient bastry");
    }

    public void printMenu() {
        System.out.println("1 --> Patient");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void hospitalMenu() {
        System.out.println("---------- Hospital ----------");
    }
}