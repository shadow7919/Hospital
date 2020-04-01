package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    private String name;
    private Address address;
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private HashMap<Integer, Integer> normalRooms = new HashMap();
    private HashMap<Integer, Integer> emergencyRooms = new HashMap();


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
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public String toString() {
        return name + " " + address;
    }
    public ArrayList<Patient> getPatients() {
        return patients;
    }
    public HashMap<Integer, Integer> getNormalRooms() {
        return normalRooms;
    }
    public HashMap<Integer, Integer> getEmergencyRooms() {
        return emergencyRooms;
    }
    public void hospitalMenu(){
        System.out.println("---------- Hospital ----------");
    }
}

class Address {
    private String city;
    private String street;
    private String other;

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public void setOther(String other) {
        this.other = other;
    }


    public String toString() {
        return city + " " + street + " " + other;
    }
}