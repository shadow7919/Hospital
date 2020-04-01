package ir.ac.kntu;

import java.util.*;

public class Part {
    Scanner scanner = new Scanner(System.in);
    private int normalRoomsNumber;
    private int emergencyRoomsNumber;
    private PartKind partKind;
    Room room = new Room();

    public void setPartKind(PartKind partKind) {
        this.partKind = partKind;
    }

    public PartKind getPartKind() {
        return partKind;
    }

    public void makeRooms(Hospital hospital) {
        System.out.println("Enter how many room do you need for " + PartKind.NORMAL + " Part ");
        normalRoomsNumber = scanner.nextInt();
        System.out.println("And also " + PartKind.EMERGENCY + " Part rooms?");
        emergencyRoomsNumber = scanner.nextInt();
        System.out.println("enter the default number of beds for all rooms");
        int defaultBedNumber = scanner.nextInt();
        while (!check(defaultBedNumber)) {
            if (check(defaultBedNumber)) {
                room.setBedsNumber(defaultBedNumber);
            } else {
                System.out.println("The Max bed each room can have is 6");
            }
            defaultBedNumber = scanner.nextInt();
        }
        for (int i = 1; i <= normalRoomsNumber; i++) {
            hospital.getNormalRooms().put(i,defaultBedNumber);
        }
        for (int i = 1; i <= emergencyRoomsNumber; i++) {
            hospital.getEmergencyRooms().put(i,defaultBedNumber);
        }
        System.out.println("and the price for the parts");
        System.out.print(PartKind.NORMAL + " :");
        room.setNormalPrice(scanner.nextInt());
        System.out.print(PartKind.EMERGENCY + " :");
        room.setEmergencyPrice(scanner.nextInt());
    }
    public void changeRoomsBedNumbers(){
    }
    private boolean check(int defaultBedNumber) {
        if (defaultBedNumber > 0 && defaultBedNumber <= 6) {
            return true;
        }
        return false;
    }
}

enum PartKind {
    EMERGENCY, NORMAL;
}