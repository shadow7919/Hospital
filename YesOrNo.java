package ir.ac.kntu;

public enum YesOrNo {
    YES(true), NO(false);
    public boolean inside;

    YesOrNo(boolean inside) {
        this.inside = inside;
    }

    public boolean isInside() {
        return inside;
    }
}
