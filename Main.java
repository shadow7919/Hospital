package ir.ac.kntu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Nurse nurse = new Nurse();
        Room room = new Room();
        welcome(scanner, hospital, room);
        chooseMenu(scanner, doctor, patient, nurse, room);
    }

    public static void welcome(Scanner scanner, Hospital hospital, Room room) {
        System.out.println("------------ WELCOME ------------");
        System.out.println("Go step by step First doctor then Nurse and at the end Patient");
        System.out.print("Hospital name :");
        hospital.setName(scanner.nextLine());
        System.out.println("--------- Rooms and Price ---------");
        room.makeRooms(hospital, room);
        System.out.println("------------------------------------");
    }

    public static void menu() {
        System.out.println("choose what to do");
        System.out.println("---> " + MenuOptions.DOCTOR);
        System.out.println("---> " + MenuOptions.NURSE);
        System.out.println("---> " + MenuOptions.PATIENT);
        System.out.println("---> " + MenuOptions.ROOM);
        System.out.println("---> " + MenuOptions.HOSPITAL);
        System.out.println("---> " + MenuOptions.QUITE);
    }

    public static void chooseMenu(Scanner scanner, Doctor doctor, Patient patient, Nurse nurse, Room room) {
        MenuOptions menuOptions;
        String choose;
        while (true) {
            menu();
            choose = scanner.nextLine();
            try {
                menuOptions = MenuOptions.valueOf(choose);
                switch (menuOptions) {
                    case DOCTOR:
                        doctor.doctorMenu();
                        break;
                    case NURSE:
                        nurse.nurseMenu();
                        break;
                    case PATIENT:
                        patient.menu();
                        break;
                    case ROOM:
                        room.menu();
//                        weaklyShifts.showShift();
                        break;
                    case HOSPITAL:
//                        Hospital.hospitalMenu();
                    case QUITE:
                        return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input");
            }
        }
    }
}