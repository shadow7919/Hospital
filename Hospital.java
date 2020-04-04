package ir.ac.kntu;

import java.util.ArrayList;

public class Hospital {
    private String name;
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Room> normalRooms = new ArrayList<>();
    private ArrayList<Room> emergencyRooms = new ArrayList<>();
    private ArrayList<Nurse> allNurses = new ArrayList<>();


    public boolean sameId(Doctor doctor) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId() == doctor.getId()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Doctor doctor) {
        this.doctors.add(doctor);
    }

    public ArrayList<Nurse> getAllNurses() {
        return allNurses;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Room> getNormalRooms() {
        return normalRooms;
    }

    public ArrayList<Room> getEmergencyRooms() {
        return emergencyRooms;
    }

    public void hospitalMenu() {
        System.out.println("---------- Hospital ----------");
    }
}