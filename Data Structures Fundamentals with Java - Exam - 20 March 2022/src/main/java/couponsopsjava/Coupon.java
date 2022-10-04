package couponsopsjava;

public class Coupon {

    public final String code;
    public final int discountPercentage;
    public final int validity;

    public Coupon(String code, int discountPercentage, int validity) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.validity = validity;
    }

    public String getCode() {
        return code;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public int getValidity() {
        return validity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon)) return false;

        Coupon coupon = (Coupon) o;

        return getCode().equals(coupon.getCode());
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }
}
