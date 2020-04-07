package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    private static final int MAX_BED_NUMBER = 6;
    Scanner scanner = new Scanner(System.in);
    private int roomNumber;
    private int bedsNumber;
    private boolean isAvailable = true;
    private int price;
    private ArrayList<Patient> patients = new ArrayList<>();

    public Room(int roomNumber, int bedsNumber, int price) {
        this.roomNumber = roomNumber;
        this.bedsNumber = bedsNumber;
        this.price = price;
    }

    public Room() {
    }

    private void printMenu() {
        System.out.println("-------- ROOM ---------");
        System.out.println("1 --> Show ");
        System.out.println("2 --> Change");
        System.out.println("3 --> Add ");
        System.out.println("4 --> Back ");
        System.out.println("-------------------------");
    }

    public void menu() {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    RoomMenu();
                    break;
                case 2:
                    change();
                    break;
                case 3:
                    addRoom();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private void showRoomMenu() {
        System.out.println("------- Show -------");
        System.out.println("1 --> All Room");
        System.out.println("2 --> Patient");
        System.out.println("3 --> Back ");
    }

    private void RoomMenu() {
        int option;
        while (true) {
            showRoomMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showRoom(whichPart(null));
                    break;
                case 2:
                    patientsInRoom();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Wrong input ");
            }
        }
    }

    private void showChange() {
        System.out.println("------- CHANGE ------");
        System.out.println("1 --> Change Beds Number");
        System.out.println("2 --> Make unavailable");
        System.out.println("3 --> Back ");
    }

    public void change() {
        Room room = findRoom(null);
        int option;
        while (true) {
            showChange();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    changeBeds(room);
                    break;
                case 2:
                    roomUnavailable(room);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void roomUnavailable(Room room) {
        if (room.patients.size() > 0) {
            System.out.println("Can't make unavailable");
            return;
        }
        room.isAvailable = false;
        System.out.println(room.roomNumber + " is unavailable now");
    }

    private PartKind whichPart(Patient patient) {
        System.out.println("Pick which part ");
        System.out.println(PartKind.NORMAL + " OR " + PartKind.EMERGENCY);
        String choose;
        PartKind partKind;
        while (true) {
            choose = scanner.next();
            try {
                partKind = PartKind.valueOf(choose);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input ");
            }
        }
        if (patient != null) {
            patient.setPartKind(partKind);
        }
        return partKind;
    }

    public void discountForRoom(Room room) {
        int extraBeds = room.bedsNumber - 1;
        double discount = ((double) (extraBeds * 10)) / 100;
        if (discount != 0) {
            discount = 1 - discount;
            room.price *= discount;
        }
    }

    public void addRoom() {
        PartKind partKind = whichPart(null);
        int roomNumber;
        if (partKind == PartKind.NORMAL) {
            roomNumber = Hospital.getNormalRooms().get(Hospital.getNormalRooms().size() - 1).roomNumber + 1;
        } else {
            roomNumber = Hospital.getEmergencyRooms().get(Hospital.getEmergencyRooms().size() - 1).roomNumber + 1;
        }
        System.out.print("Beds Number : ");
        int bedsNumber = scanner.nextInt();
        while (check(bedsNumber, 0, MAX_BED_NUMBER)) {
            System.out.println("Max number for bed is " + MAX_BED_NUMBER);
            bedsNumber = scanner.nextInt();
        }
        Room room = new Room(roomNumber, bedsNumber, price);
        availableHandle(room);
        Hospital.getNormalRooms().add(room);
    }

    private void availableHandle(Room room) {
        YesOrNo yesOrNo;
        System.out.print("Available ?\t" + YesOrNo.YES + " OR " + YesOrNo.NO + " : ");
        String choose = scanner.next();
        while (true) {
            try {
                yesOrNo = YesOrNo.valueOf(choose);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input ");
            }
        }
        room.isAvailable = yesOrNo.inside;
    }

    public void pickRoom(Patient patient) {
        Room room;
        while (true) {
            room = findRoom(patient);
            if (room.isAvailable) {
                patient.setRoom(room);
                room.patients.add(patient);
                return;
            } else {
                System.out.println("Unavailable ! ");
            }
        }
    }

    public void showRoom(PartKind partKind) {
        if (partKind == PartKind.NORMAL) {
            showRoomHandle(Hospital.getNormalRooms());
        } else {
            showRoomHandle(Hospital.getEmergencyRooms());
        }
    }

    public void showRoomHandle(ArrayList<Room> roomArrayList) {
        System.out.println("-------- ROOMS --------");
        for (Room room : roomArrayList) {
            System.out.print("Room number : " + room.roomNumber);
            System.out.println("\tbeds number : " + room.bedsNumber);
        }
    }


    private void changeBeds(Room room) {
        System.out.println("Beds Number :");
        int bedsNumber = scanner.nextInt();
        while (check(bedsNumber, room.patients.size(), MAX_BED_NUMBER)) {
            System.out.println("Max Bed Number this room is" + MAX_BED_NUMBER + "and Min is " + room.patients.size());
            bedsNumber = scanner.nextInt();
        }
        room.setBedsNumber(bedsNumber);
    }

    public void patientsInRoom() {
        Room room;
        room = findRoom(null);
        if (room.patients.size() == 0) {
            System.out.println("No Patient");
            return;
        }
        for (Patient patient : room.patients) {
            System.out.println("Name : " + patient.getName() + "\tId : " + patient.getId());
        }
    }

    public Room findRoom(Patient patient) {
        PartKind partKind = whichPart(patient);
        Room foundRoom;
        if (partKind == PartKind.NORMAL) {
            foundRoom = findRoomHandle(partKind, Hospital.getNormalRooms());
        } else {
            foundRoom = findRoomHandle(partKind, Hospital.getEmergencyRooms());
        }
        return foundRoom;
    }

    private Room findRoomHandle(PartKind partKind, ArrayList<Room> partRooms) {
        int option;
        while (true) {
            showRoom(partKind);
            System.out.print("Enter Room number : ");
            option = scanner.nextInt();
            for (Room room : partRooms) {
                if (room.roomNumber == option) {
                    return room;
                }
            }
            System.out.println("Cant find room");
        }
    }

    private int defaultBedNumber(PartKind partKind) {
        System.out.print("Default Bed number for " + partKind + " Part : ");
        int defaultBedNumberRoom = scanner.nextInt();
        while (check(defaultBedNumberRoom, 0, MAX_BED_NUMBER)) {
            System.out.println("The Max bed each room can have is" + MAX_BED_NUMBER);
            defaultBedNumberRoom = scanner.nextInt();
        }
        return defaultBedNumberRoom;
    }

    public void makeRooms() {
        System.out.print("Room Number for " + PartKind.NORMAL + " Part : ");
        int normalRoomsNumber = scanner.nextInt();
        System.out.print("Room Number for " + PartKind.EMERGENCY + " Part : ");
        int emergencyRoomsNumber = scanner.nextInt();
        int defaultNormalBedNumber = defaultBedNumber(PartKind.NORMAL);
        int defaultEmergencyBedNumber = defaultBedNumber(PartKind.EMERGENCY);
        System.out.print("Price for " + PartKind.NORMAL + " : ");
        int normalRoomPrice = scanner.nextInt();
        System.out.print("Price for " + PartKind.EMERGENCY + " : ");
        int emergencyRoomPrice = scanner.nextInt();
        for (int i = 1; i <= normalRoomsNumber; i++) {
            Room myRoom = new Room(i, defaultNormalBedNumber, normalRoomPrice);
            Hospital.getNormalRooms().add(myRoom);
        }
        for (int i = 1; i <= emergencyRoomsNumber; i++) {
            Room myRoom = new Room(i, defaultEmergencyBedNumber, emergencyRoomPrice);
            Hospital.getEmergencyRooms().add(myRoom);
        }
    }

    public boolean check(int defaultBedNumber, int min, int max) {
        return defaultBedNumber <= min || defaultBedNumber > max;
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