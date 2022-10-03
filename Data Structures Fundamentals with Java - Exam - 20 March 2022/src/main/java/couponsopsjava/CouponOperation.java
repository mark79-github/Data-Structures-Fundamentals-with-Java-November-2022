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
        if (this.websites.containsKey(w)) {
            throw new IllegalArgumentException();
        }
        this.websites.put(w, new ArrayList<>());
    }

    public void addCoupon(Website w, Coupon c) {
        if (!this.websites.containsKey(w)) {
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
        for (Coupon coupon : this.websites.get(website)) {
            this.coupons.remove(coupon);
        }
        this.websites.remove(website);
        return website;
    }

    public Coupon removeCoupon(String code) {
        Coupon coupon = null;
        for (Coupon current : this.coupons) {
            if (current.getCode().equals(code)) {
                coupon = current;
                break;
            }
        }
        if (coupon == null) {
            throw new IllegalArgumentException();
        }
        this.coupons.remove(coupon);
        for (Map.Entry<Website, List<Coupon>> entry : this.websites.entrySet()) {
            if (entry.getValue().contains(coupon)) {
                entry.getValue().remove(coupon);
                break;
            }
        }
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
        if (!this.websites.containsKey(w)) {
            throw new IllegalArgumentException();
        }
        return new ArrayList<>(this.websites.get(w));
    }

    public void useCoupon(Website w, Coupon c) {
        if (!this.websites.containsKey(w)) {
            throw new IllegalArgumentException();
        }
        boolean contains = this.websites.get(w).contains(c);
        if (!contains) {
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
