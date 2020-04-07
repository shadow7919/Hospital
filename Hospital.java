package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Hospital {
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static ArrayList<Patient> patients = new ArrayList<>();
    private static ArrayList<Room> normalRooms = new ArrayList<>();
    private static ArrayList<Room> emergencyRooms = new ArrayList<>();
    private static ArrayList<Nurse> nurses = new ArrayList<>();
    private static ArrayList<ShiftTimeClass> shiftsTimes = new ArrayList<>();
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

    public void menu(Patient patient) {
        int option;
        while (true) {
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    patientShow(patient);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    public void patientShow(Patient patient) {
        System.out.println("-------  Patients -------");
        int option;
        while (true) {
            System.out.println("1 --> Patient in Part ");
            System.out.println("2 --> Get Id of Patient doctor");
            System.out.println("3 --> Patients hospitalized");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showPatientsPart();
                    break;
                case 2:
                    showPatientDoctor(patient);
                    break;
                case 3:
                    hospitalizedPatient();
            }
        }
    }

    public void hospitalizedPatient() {
        System.out.println("Enter time period");
        MyDate now = MyDate.dateSet();
        MyDate after = MyDate.dateSet();
        //complete this tomorrow
    }

    public void showPatientDoctor(Patient patient) {
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
                System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
            }
        }
    }

    public void printMenu() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void hospitalMenu() {
        System.out.println("---------- Hospital ----------");
    }
}