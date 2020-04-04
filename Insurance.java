package ir.ac.kntu;

public enum Insurance {
    socialInsurance(.9),
    armedForces(.5),
    healthService(.75);
    private double discount;

    Insurance(double num) {
        discount = num;
    }

    public double getDiscount() {
        return discount;
    }
}
