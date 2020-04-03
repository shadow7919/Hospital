package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();
        Address address = new Address();
        Part part = new Part();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        WeaklyShifts weaklyShifts = new WeaklyShifts();
        Nurse nurse = new Nurse();
//        welcome(scanner, hospital, address, part);
        chooseMenu(scanner, doctor, hospital, weaklyShifts, patient,nurse);
    }

    public static void welcome(Scanner scanner, Hospital hospital, Address address, Part part) {
        System.out.println("welcome\nlet's begin ...");
        System.out.println("please give us name ");
        hospital.setName(scanner.nextLine());
        System.out.println("What's the address ? ( city , street , others ... )");
        address.setCity(scanner.nextLine());
        address.setStreet(scanner.nextLine());
        address.setOther(scanner.nextLine());
        hospital.setAddress(address);
        System.out.println("--------- Rooms and Price ---------");
        part.makeRooms(hospital);
        System.out.println("------------------------------------");
    }

    public static void menu() {
        System.out.println("choose what to do");
        System.out.println("---> " + MenuOptions.DOCTOR);
        System.out.println("---> "+ MenuOptions.NURSE);
        System.out.println("---> " + MenuOptions.PATIENT);
        System.out.println("---> " + MenuOptions.ROOM);
        System.out.println("---> " + MenuOptions.HOSPITAL);
        System.out.println("---> " + MenuOptions.QUITE);
    }

    public static void chooseMenu(Scanner scanner, Doctor doctor, Hospital hospital, WeaklyShifts weaklyShifts, Patient patient,Nurse nurse) {
        MenuOptions menuOptions = MenuOptions.DOCTOR;
        String choose;
        while (true) {
            menu();
            choose = scanner.nextLine();
            try {
                menuOptions = menuOptions.valueOf(choose);
                switch (menuOptions) {
                    case DOCTOR:
                        doctor.doctorMenu(scanner, doctor, hospital, weaklyShifts,nurse);
                        break;
                    case NURSE:
                        nurse.NurseMenu(hospital);
                        break;
                    case PATIENT:
                        patient.printMenu(hospital);
                        break;
                    case ROOM:
                        weaklyShifts.showShift();
                        break;
                    case HOSPITAL:
                        hospital.hospitalMenu();
                    case QUITE:
                        return;
                    default:
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input");
            }
        }
    }

    enum MenuOptions {
        DOCTOR, PATIENT,NURSE,ROOM, HOSPITAL, QUITE;
    }
}