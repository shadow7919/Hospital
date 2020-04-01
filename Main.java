package ir.ac.kntu;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();
        Address address = new Address();
        Part part = new Part();
        Doctor doctor = new Doctor();
        Room room = new Room();
        Patient patient = new Patient();
        WeaklyShifts weaklyShifts = new WeaklyShifts();
        HashMap<Integer, Integer> rooms = new HashMap();
//        welcome(scanner, hospital, address, part);
//        chooseMenu(scanner, doctor, hospital, weaklyShifts, patient);
        part.makeRooms(hospital);
//        System.out.println(hospital.getNormalRooms());
//        System.out.println(hospital.getEmergencyRooms());
        room.pickRoom(hospital,PartKind.NORMAL);
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
        System.out.println("1 --> " + MenuOptions.DOCTOR);
        System.out.println("2 --> " + MenuOptions.PATIENT);
        System.out.println("3 --> " + MenuOptions.ROOM);
        System.out.println("4 --> " + MenuOptions.HOSPITAL);
        System.out.println("5 --> " + MenuOptions.QUITE);
    }

    public static void chooseMenu(Scanner scanner, Doctor doctor, Hospital hospital, WeaklyShifts weaklyShifts, Patient patient) {
        MenuOptions menuOptions = MenuOptions.DOCTOR;
        String choose;
        while (true) {
            menu();
            choose = scanner.nextLine();
            try {
                menuOptions = menuOptions.valueOf(choose);
                switch (menuOptions) {
                    case DOCTOR:
                        doctor.doctorMenu(scanner, doctor, hospital, weaklyShifts);
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
                System.out.println("Wrong input ");
            }
        }
    }


    enum MenuOptions {
        DOCTOR, PATIENT, ROOM, HOSPITAL, QUITE;
    }
}