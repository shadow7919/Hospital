package ir.ac.kntu;

import java.util.ArrayList;

public class Hospital {
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static ArrayList<Patient> patients = new ArrayList<>();
    private static ArrayList<Room> normalRooms = new ArrayList<>();
    private static ArrayList<Room> emergencyRooms = new ArrayList<>();
    private static ArrayList<Nurse> allNurses = new ArrayList<>();
    private String name;

    public static ArrayList<Doctor> getDoctors() {
        return doctors;
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

    public static ArrayList<Nurse> getAllNurses() {
        return allNurses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void hospitalMenu() {
        System.out.println("---------- Hospital ----------");
    }
}