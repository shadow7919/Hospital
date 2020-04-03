package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    Scanner scanner = new Scanner(System.in);
    private int roomNumber;
    private int bedsNumber;
    private boolean isAvailable = true;
    private int price;

    public Room(int roomNumber, int bedsNumber, int price) {
        this.roomNumber = roomNumber;
        this.bedsNumber = bedsNumber;
        this.price = price;
    }

    public Room() {
    }

    public void discountForRoom(Room room) {
        int extraBeds = room.bedsNumber - 1;
        double discount = ((double) (extraBeds * 10)) / 100;
        if (discount != 0) {
            discount = 1 - discount;
            room.price *= discount;
        }
    }

    public void addRoom(Hospital hospital) {
        System.out.println("Enter the room number and beds Number");
        int roomNumber = scanner.nextInt();
        int bedsNumber = scanner.nextInt();
        Room room = new Room(roomNumber, bedsNumber, 0);
        hospital.getNormalRooms().add(room);
    }

    public void pickRoom(Hospital hospital, PartKind partKind, Patient patient, Part part) {
        showRoom(hospital, partKind);
        System.out.println("If you don't want to change room's bed press 0 else press any number ");
        int input = scanner.nextInt();
        while (input != 0) {
            changeRoomsBeds(hospital, partKind, part);
            showRoom(hospital, partKind);
            System.out.println("don't want to change press 0");
            input = scanner.nextInt();
        }
        System.out.print("Ok which room you want to pick : ");
        input = scanner.nextInt();
        if (partKind == PartKind.NORMAL) {
            roomHandle(hospital.getNormalRooms(), input, patient);
        } else {
            roomHandle(hospital.getEmergencyRooms(), input, patient);
        }
    }

    public void showRoom(Hospital hospital, PartKind partKind) {
        if (partKind == PartKind.NORMAL) {
            showRoomHandle(hospital.getNormalRooms());
        } else {
            showRoomHandle(hospital.getEmergencyRooms());
        }
    }

    public void showRoomHandle(ArrayList<Room> roomArrayList) {
        System.out.println("-------- ROOMS --------");
        for (Room room : roomArrayList) {
            System.out.print("Room number : " + room.roomNumber);
            System.out.println("  and it's beds number " + room.bedsNumber);
        }
    }

    public void changeRoomsBeds(Hospital hospital, PartKind partKind, Part part) {
        System.out.print("Which room you want to change ? ");
        int inputNumber = scanner.nextInt();
        if (partKind == PartKind.NORMAL) {
            changeRoomHandle(hospital.getNormalRooms(), inputNumber, part);
        }
        if (partKind == PartKind.EMERGENCY) {
            changeRoomHandle(hospital.getEmergencyRooms(), inputNumber, part);
        }
    }

    private void changeRoomHandle(ArrayList<Room> roomArrayList, int inputNumber, Part part) {
        for (Room room : roomArrayList) {
            if (room.roomNumber == inputNumber) {
                System.out.println("how many beds you want ?");
                int bedsNumber = scanner.nextInt();
                while (!part.check(bedsNumber)) {
                    System.out.println("The max number of beds in a room is 6");
                }
                room.setBedsNumber(bedsNumber);
                return;
            }
        }
        System.out.println("Cant find this room");
    }

    private void roomHandle(ArrayList<Room> roomArrayList, int input, Patient patient) {
        for (Room room : roomArrayList) {
            if (input == room.roomNumber && room.isAvailable) {
                patient.setRoom(room);
                return;
            }
        }
        System.out.println("Can't find that room");
    }

    public void setBedsNumber(int bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getPrice() {
        return price;
    }
}