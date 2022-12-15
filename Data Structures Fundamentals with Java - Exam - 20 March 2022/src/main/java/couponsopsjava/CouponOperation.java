package couponsopsjava;

import java.util.*;
import java.util.stream.Collectors;


public class CouponOperation implements ICouponOperation {

    private final Map<Website, List<Coupon>> websites;
    private final List<Coupon> coupons;

    public CouponOperation() {
        this.websites = new HashMap<>();
        this.coupons = new ArrayList<>();
    }

    public void registerSite(Website w) {
        if (this.exist(w)) {
            throw new IllegalArgumentException();
        }
        this.websites.put(w, new ArrayList<>());
    }

    public void addCoupon(Website w, Coupon c) {
        if (!this.exist(w)) {
            throw new IllegalArgumentException();
        }
        if (this.websites.get(w).contains(c)) {
            throw new IllegalArgumentException();
        }
        this.websites.get(w).add(c);
        this.coupons.add(c);
    }

    public Website removeWebsite(String domain) {
        Website website = this.websites.keySet()
                .stream()
                .filter(w -> w.getDomain().equals(domain))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        this.websites.get(website).forEach(this.coupons::remove);
        this.websites.remove(website);
        return website;
    }

    public Coupon removeCoupon(String code) {
        Coupon coupon = this.coupons
                .stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        this.websites.forEach((key, value) -> value.remove(coupon));
        this.coupons.remove(coupon);
        return coupon;
    }

    public boolean exist(Website w) {
        return this.websites.containsKey(w);
    }

    public boolean exist(Coupon c) {
        return this.coupons.contains(c);
    }

    public Collection<Website> getSites() {
        return new ArrayList<>(this.websites.keySet());
    }

    public Collection<Coupon> getCouponsForWebsite(Website w) {
        if (!this.exist(w)) {
            throw new IllegalArgumentException();
        }
        return new ArrayList<>(this.websites.get(w));
    }

    public void useCoupon(Website w, Coupon c) {
        if (!this.exist(w)) {
            throw new IllegalArgumentException();
        }
        if (!this.websites.get(w).contains(c)) {
            throw new IllegalArgumentException();
        }
        this.websites.get(w).remove(c);
        this.coupons.remove(c);
    }

    public Collection<Coupon> getCouponsOrderedByValidityDescAndDiscountPercentageDesc() {
        Comparator<Coupon> comparator = Comparator.comparingInt(Coupon::getValidity).thenComparingInt(Coupon::getDiscountPercentage).reversed();
        return this.coupons
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Collection<Website> getWebsitesOrderedByUserCountAndCouponsCountDesc() {
        return this.websites
                .entrySet()
                .stream()
                .sorted((f, s) -> {
                            if (f.getKey().getUsersCount() - s.getKey().getUsersCount() == 0) {
                                return s.getValue().size() - f.getValue().size();
                            }
                            return f.getKey().getUsersCount() - s.getKey().getUsersCount();
                        }
                ).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
