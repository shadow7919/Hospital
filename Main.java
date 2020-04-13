package ir.ac.kntu;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Nurse nurse = new Nurse();
        Room room = new Room();
        welcome(hospital);
        chooseMenu(doctor, patient, nurse, room, hospital);
    }

    public static void welcome(Hospital hospital) {
        System.out.println("------------ WELCOME ------------");
        System.out.print("Hospital name : ");
        hospital.setName(scanner.nextLine());
        Room room = new Room();
        room.makeRooms(hospital);
        System.out.println(" ----------------- ");
    }

    public static void menu() {
        System.out.println("1 --> " + MenuOptions.DOCTOR);
        System.out.println("2 --> " + MenuOptions.NURSE);
        System.out.println("3 --> " + MenuOptions.PATIENT);
        System.out.println("4 --> " + MenuOptions.ROOM);
        System.out.println("5 --> " + MenuOptions.HOSPITAL);
        System.out.println("6 --> " + MenuOptions.SEARCH);
        System.out.println("7 --> " + MenuOptions.QUITE);
        System.out.println(" ----------------- ");
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

    public static void chooseMenu(Doctor doctor, Patient patient, Nurse nurse, Room room, Hospital hospital) {
        while (true) {
            switch (choose(scanner)) {
                case DOCTOR:
                    doctor.doctorMenu(hospital);
                    break;
                case NURSE:
                    nurse.nurseMenu(hospital);
                    break;
                case PATIENT:
                    patient.menu(hospital);
                    break;
                case ROOM:
                    room.menu(hospital);
                    break;
                case HOSPITAL:
                    hospital.showMenu();
                    break;
                case SEARCH:
                    hospital.searchMenu();
                    break;
                case QUITE:
                    System.out.println("Hope Never see you in " + hospital.getName());
                    return;
            }
        }
    }
}