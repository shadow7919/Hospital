package ir.ac.kntu;

import java.util.Date;
import java.util.Scanner;

public class Patient {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private int id;
    private Part part = new Part();
    private Room room = new Room();
    private Doctor doctor;
    //    private int age;
//    private int caseId;
    private MyDate entry = new MyDate();

    public void addPatient(Hospital hospital) {
        System.out.println("----------- Add Patient -----------");
        System.out.println("enter the id");
        int inputId = scanner.nextInt();
        if (checkId(hospital, inputId)) {
            id = inputId;
            System.out.println("enter the name entry date ( day / month / year ) ");
            name = scanner.nextLine();
            entry.setDay(scanner.nextInt());
            entry.setMonth(scanner.nextInt());
            entry.setYear(scanner.nextInt());
            choosePart();
        } else {
            System.out.println("id is already saved");
        }
    }

    private void choosePart() {
        System.out.println("Choose part --> \n1-->" + PartKind.NORMAL + "\n2-->" + PartKind.EMERGENY);
        int whichPart;
        do {
            whichPart = scanner.nextInt();
            switch (whichPart) {
                case 1:
                    part.setPartKind(PartKind.NORMAL);
                    break;
                case 2:
                    part.setPartKind(PartKind.EMERGENY);
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        } while (whichPart != 1 && whichPart != 2);
    }

    public void printMenu(Hospital hospital) {
        int option = 0;
        while (option != 5) {
            System.out.println("----------- Patient Menu -----------");
            System.out.println("1. Add Patient");
            System.out.println("2. Patient doctor ");
            System.out.println("3. Patient information");
            System.out.println("4. discharge Patient");
            System.out.println("5. Back to previous menu");
            System.out.println("---------------------------------------");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    addPatient(hospital);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public boolean checkId(Hospital hospital, int id) {
        for (int i = 0; i < hospital.getPatients().size(); i++) {
            if (id == hospital.getPatients().get(i).getId()) {
                return false;
            }
        }
        return true;
    }

    public int getId() {
        return id;
    }
}
/*
1--> add patient{
enter the name
enter the id
enter the room
enter gender
enter the age
enter entry date
pick doctor
}
 */