package ir.ac.kntu;

public enum Insurance {
    SOCIAL_INSURANCE(.9),
    ARMED_FORCES(.5),
    HEALTH_SERVICES(.75),
    NON(1);

    private double discount;

    Insurance(double num) {
        discount = num;
    }

    public double getDiscount() {
        return discount;
    }
}
