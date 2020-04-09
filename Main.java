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
        chooseMenu(scanner, doctor, patient, nurse, room, hospital);
    }

    public static void welcome(Scanner scanner, Hospital hospital, Room room) {
        System.out.println("------------ WELCOME ------------");
        System.out.println("Go step by step First doctor then Nurse and at the end Patient");
        System.out.print("Hospital name : ");
        hospital.setName(scanner.nextLine());
        System.out.println("--------- Rooms and Price ---------");
        room.makeRooms();
        System.out.println("------------------------------------");
    }

    public static void menu() {
        System.out.println("choose what to do");
        System.out.println("1 --> " + MenuOptions.DOCTOR);
        System.out.println("2 --> " + MenuOptions.NURSE);
        System.out.println("3 --> " + MenuOptions.PATIENT);
        System.out.println("4 --> " + MenuOptions.ROOM);
        System.out.println("5 --> " + MenuOptions.HOSPITAL);
        System.out.println("6 --> " + MenuOptions.SEARCH);
        System.out.println("7 --> " + MenuOptions.QUITE);
    }

    public static MenuOptions choose(Scanner scanner) {
        int option;
        while (true) {
            menu();
            option = scanner.nextInt();
            MenuOptions[] menuOptions = MenuOptions.values();
            if (option <= menuOptions.length) {
                return menuOptions[option - 1];
            }
            System.out.println("Wrong input");
        }
    }

    public static void chooseMenu(Scanner scanner, Doctor doctor, Patient patient, Nurse nurse, Room room, Hospital hospital) {
        while (true) {
            switch (choose(scanner)) {
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
                    break;
                case HOSPITAL:
                    hospital.showMenu();
                    break;
                case SEARCH:
                    hospital.searchMenu();
                    break;
                case QUITE:
                    return;
            }
        }
    }
}