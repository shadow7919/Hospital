package ir.ac.kntu;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();
        Address address = new Address();
        Part part = new Part();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        WeaklyShifts weaklyShifts = new WeaklyShifts();
//        welcome(scanner, hospital, address, part);
        chooseMenu(scanner, doctor, hospital, weaklyShifts, patient);
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
            if (tempPartKind.equals(PartKind.NORMAL.name())) {
                part.setPartKind(PartKind.NORMAL);
                hospital.setPart(part);
                break;
            } else if (tempPartKind.equals(PartKind.EMERGENY.name())) {
                part.setPartKind(PartKind.EMERGENY);
                hospital.setPart(part);
                break;
            } else {
                System.out.println("Try again emergency or main");
            }
        }
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
            if (menuOptions.valueOf(choose) != null) {
                menuOptions = menuOptions.valueOf(choose);
                switch (menuOptions) {
                    case DOCTOR:
                        doctorMenu(scanner, doctor, hospital, weaklyShifts);
                        break;
                    case PATIENT:
                        patient.printMenu(hospital);
                        break;
                    case ROOM:
                        weaklyShifts.showShift();
                        break;
                    case HOSPITAL:
                        break;
                    case QUITE:
                        return;
                    default:
                }
            } else {
                System.out.println("Wrong input ");
            }
        }
    }

    public static void doctorMenu(Scanner scanner, Doctor doctor, Hospital hospital, WeaklyShifts weaklyShifts) {
        int option = 0;
        while (option != 5) {
            System.out.println("----------- Doctor's Menu -----------");
            System.out.println("1. Add doctor");
            System.out.println("2. Choose doctor's shift");
            System.out.println("3. Doctor information");
            System.out.println("4. Remove doctor");
            System.out.println("5. Back to previous menu");
            System.out.println("---------------------------------------");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    doctor.addDoctor(hospital);
                    break;
                case 2:
                    doctor.addShift(hospital, weaklyShifts);
                    break;
                case 3:
                    doctor.showDoctor(hospital, weaklyShifts);
                    break;
                case 4:
                    doctor.remove(hospital);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    enum MenuOptions {
        DOCTOR, PATIENT, ROOM, HOSPITAL, QUITE;
    }
}