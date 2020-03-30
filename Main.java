package ir.ac.kntu;

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
        WeaklyShifts weaklyShifts = new WeaklyShifts();
//        welcome(scanner, hospital, address, part);
        chooseMenu(scanner, doctor, hospital,weaklyShifts);
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
        System.out.println("hospital beds number");
        hospital.setBeds(scanner.nextInt());
        System.out.println("Which of this hospital the patient is take care of ?");
        scanner.nextLine();
        while (true) {
            String tempPartKind = scanner.nextLine();
            if (tempPartKind.equals(PartKind.main.name())) {
                part.setPartKind(PartKind.main);
                hospital.setPart(part);
                break;
            } else if (tempPartKind.equals(PartKind.emergency.name())) {
                part.setPartKind(PartKind.emergency);
                hospital.setPart(part);
                break;
            } else {
                System.out.println("Try again emergency or main");
            }
        }
    }

    public static void menu() {
        System.out.println("choose what to do");
        System.out.println("1 --> Doctor");
        System.out.println("2 --> Patient");
        System.out.println("3 --> Room");
        System.out.println("4 --> Hospital");
        System.out.println("5 --> Quite");
    }

    public static void chooseMenu(Scanner scanner, Doctor doctor, Hospital hospital, WeaklyShifts weaklyShifts) {
        String choose = "";
        MenuOptions menuOptions;
        while (MenuOptions.Quite.name() != choose) {
            try {
                menu();
                choose = scanner.nextLine();
                menuOptions = MenuOptions.valueOf(choose);
                switch (menuOptions.name()) {
                    case "Doctor":
                    case "doctor":
                        doctorMenu(scanner, doctor, hospital,weaklyShifts);
                        break;
                    case "Patient":
                    case "patient":
                        weaklyShifts.showShift(scanner);
                        break;
                    case "Room":
                    case "room":
                        break;
                    case "Hospital":
                    case "hospital":
                        break;
                    case "Quite":
                    case "quite":
                        return;
                    default:
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input");
            }
        }
    }

    public static void doctorMenu(Scanner scanner, Doctor doctor, Hospital hospital,WeaklyShifts weaklyShifts) {
        int option = 0;
        while (option != 4) {
            System.out.println("----------- Doctor's Menu -----------");
            System.out.println("1. Add doctor");
            System.out.println("2. Choose doctor's shift");
            System.out.println("3. Doctor information");
            System.out.println("4. Back to previous menu");
            System.out.println("---------------------------------------");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    doctor.addDoctor(hospital);
                    break;
                case 2:
                    doctor.addShift(scanner,hospital,weaklyShifts);
                    break;
                case 3:
                    doctor.showDoctor(scanner, hospital,weaklyShifts);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }
    enum MenuOptions {
        Doctor, Patient, Rooms, Hospital, Quite;
    }
}