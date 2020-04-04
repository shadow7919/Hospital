package ir.ac.kntu;
// availabe room , patients in room
import java.util.ArrayList;
import java.util.Scanner;

public class Room {
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
        System.out.println("1 --> Show rooms ");
        System.out.println("2 --> Change rooms");
        System.out.println("3 --> Make room unavailable");
        System.out.println("4 --> Patient in rooms");
        System.out.println("5 --> Add Room");
        System.out.println("6 --> Back to previous menu");
        System.out.println("-------------------------");
    }

    public void menu(Hospital hospital) {
        int option;
        while (true) {
            printMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showRoom(hospital,whichPart());
                    break;
                case 2:
                    changeRoomsBeds(hospital);
                    break;
                case 3:
                    roomUnavailable(hospital);
                    break;
                case 4:
//                    patientInRoom();
                    break;
                case 5:
                    addRoom(hospital);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    public void roomUnavailable(Hospital hospital){
        Room room = new Room();
        System.out.println("Make this Room unavailable ");
        room = findRoom(hospital);
        room.isAvailable = false;
        System.out.println(room.roomNumber + " is unavailable now");
    }
    private PartKind whichPart() {
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

    public void addRoom(Hospital hospital) {
        System.out.println("Enter the room number and beds Number");
        int roomNumber = scanner.nextInt();
        int bedsNumber = scanner.nextInt();
        Room room = new Room(roomNumber, bedsNumber, price);
        hospital.getNormalRooms().add(room);
    }

    public void pickRoom(Hospital hospital, PartKind partKind, Patient patient) {
        showRoom(hospital, partKind);
        int input;
        System.out.print("which room you want to pick : ");
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

    public void changeRoomsBeds(Hospital hospital) {
        Room room;
        System.out.println("Which room you want to change ");
        room = findRoom(hospital);
        if(room != null) {
            changeRoomHandle(room);
        }
    }

    private void changeRoomHandle(Room room) {
                System.out.println("how many beds you want ?");
                int bedsNumber = scanner.nextInt();
                while (check(bedsNumber)) {
                    System.out.println("The max number of beds in a room is 6");
                    bedsNumber = scanner.nextInt();
                }
                room.setBedsNumber(bedsNumber);
    }

    public boolean check(int defaultBedNumber) {
        return defaultBedNumber <= 0 || defaultBedNumber > 6;
    }

    private void roomHandle(ArrayList<Room> roomArrayList, int input, Patient patient) {
        for (Room room : roomArrayList) {
            if (input == room.roomNumber && room.isAvailable) {
                patient.setRoom(room);
                room.patients.add(patient);
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

    public void patientsInRoom() {
        PartKind partKind = whichPart();
        if (partKind == PartKind.NORMAL) {
            
        } else {

        }
        for (Patient patient : patients) {
            System.out.println("ID : " + patient.getId() + "\t" + "Name : " + patient.getName());
        }
    }

    public Room findRoom(Hospital hospital) {
        PartKind partKind = whichPart();
        Room foundRoom = null;
        if (partKind == PartKind.NORMAL) {
            foundRoom = findRoomHandle(hospital, partKind, hospital.getNormalRooms());
            if(foundRoom == null){
                System.out.println("Can't find this room");
            }
        } else {
            foundRoom = findRoomHandle(hospital, partKind, hospital.getEmergencyRooms());
            if(foundRoom ==null){
                System.out.println("Cant find that Room");
            }
        }
        return foundRoom;
    }

    private Room findRoomHandle(Hospital hospital, PartKind partKind, ArrayList<Room> partRooms) {
        showRoom(hospital, partKind);
        System.out.print("Enter Room number :");
        int option = scanner.nextInt();
        for (Room room : partRooms) {
            if (room.roomNumber == option) {
                return room;
            }
        }
        return null;
    }
}