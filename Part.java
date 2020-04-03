package ir.ac.kntu;

import java.util.Scanner;

enum PartKind {
    EMERGENCY, NORMAL;
}

public class Part {
    Scanner scanner = new Scanner(System.in);
    private int normalRoomsNumber;
    private int emergencyRoomsNumber;
    private PartKind partKind;

    public PartKind getPartKind() {
        return partKind;
    }

    public void setPartKind(PartKind partKind) {
        this.partKind = partKind;
    }

    public void makeRooms(Hospital hospital) {
        System.out.println("Enter how many room do you need for " + PartKind.NORMAL + " Part ");
        normalRoomsNumber = scanner.nextInt();
        System.out.println("And also " + PartKind.EMERGENCY + " Part rooms?");
        emergencyRoomsNumber = scanner.nextInt();
        System.out.println("enter the default number of beds for all rooms");
        int defaultBedNumber = scanner.nextInt();
        while (!check(defaultBedNumber)) {
            System.out.println("The Max bed each room can have is 6");
            defaultBedNumber = scanner.nextInt();
        }
        System.out.println("and the price for the parts");
        System.out.print(PartKind.NORMAL + " :");
        int normalRoomPrice = scanner.nextInt();
        System.out.print(PartKind.EMERGENCY + " :");
        int emergencyRoomPrice = scanner.nextInt();
        for (int i = 1; i <= normalRoomsNumber; i++) {
            Room room = new Room(i, defaultBedNumber, normalRoomPrice);
            hospital.getNormalRooms().add(room);
        }
        for (int i = 1; i <= emergencyRoomsNumber; i++) {
            Room room = new Room(i, defaultBedNumber, emergencyRoomPrice);
            hospital.getEmergencyRooms().add(room);
        }
    }

    public boolean check(int defaultBedNumber) {
        if (defaultBedNumber > 0 && defaultBedNumber <= 6) {
            return true;
        }
        return false;
    }
}