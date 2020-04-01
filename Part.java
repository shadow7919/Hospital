package ir.ac.kntu;

import java.util.*;

public class Part {
    Scanner scanner = new Scanner(System.in);
    private int normalRoomsNumber;
    private int emergencyRoomsNumber;
    private PartKind partKind;
    Hospital hospital = new Hospital();
    Room room = new Room();
    public void setPartKind(PartKind partKind) {
        this.partKind = partKind;
    }

    public PartKind getPartKind() {
        return partKind;
    }
    public void makeRooms(){
        System.out.println("Enter how many room do you need for " + PartKind.NORMAL + "part ");
        normalRoomsNumber = scanner.nextInt();
        System.out.println("And also" + PartKind.EMERGENCY + "part rooms?");
        emergencyRoomsNumber = scanner.nextInt();
        System.out.println("enter the default number of beds for all rooms");
        room.setBedsNumber(scanner.nextInt());
        Map <Integer,Integer> temp = new HashMap();
        hospital.getNormalRooms().add(temp);
        for (int i = 1; i <= normalRoomsNumber; i++) {
            temp.put(i,room.getBedsNumber());
            hospital.getNormalRooms().add(temp);
        }
        for (int i = 1; i <= normalRoomsNumber; i++) {
            temp.put(i,room.getBedsNumber());
            hospital.getEmergencyRooms().add(temp);
        }
        System.out.println("and the price for the parts");
        System.out.print(partKind.NORMAL+ " :");
        room.setNormalPrice(scanner.nextInt());
        System.out.print(partKind.EMERGENCY+ " :");
        room.setEmergencyPrice(scanner.nextInt());
    }
}
enum PartKind{
    EMERGENCY,NORMAL;
}
