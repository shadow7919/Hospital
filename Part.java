package ir.ac.kntu;

public class Part {
    private int roomsNumber;
    private PartKind partKind;

    public void setPartKind(PartKind partKind) {
        this.partKind = partKind;
    }

    public PartKind getPartKind() {
        return partKind;
    }
}
enum PartKind{
    emergency,main;
}
