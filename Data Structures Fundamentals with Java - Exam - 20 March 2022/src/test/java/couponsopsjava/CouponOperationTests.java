package couponsopsjava;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class CouponOperationTests {

    private ICouponOperation couponOperations;
    private final Website w1 = new Website("a", 1);
    private final Website w2 = new Website("b", 2);
    private final Website w3 = new Website("c", 2);
    private final Website w4 = new Website("d", 4);
    private final Website w5 = new Website("e", 5);
    private final Website w6 = new Website("f", 6);
    private final Coupon c1 = new Coupon("a", 1, 3);
    private final Coupon c2 = new Coupon("b", 1, 2);
    private final Coupon c3 = new Coupon("c", 1, 1);
    private final Coupon c4 = new Coupon("d", 2, 3);
    private final Coupon c5 = new Coupon("e", 0, 3);

    @Before
    public void Setup() {
        this.couponOperations = new CouponOperation();
    }

    // 1
    @Test
    public void TestRegisterWebsite() {
        this.couponOperations.registerSite(w1);

        assertTrue(this.couponOperations.exist(w1));
    }

    // 2
    @Test(expected = IllegalArgumentException.class)
    public void TestRegisteringWebsiteTwiceThrowException() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.registerSite(w1);
    }

    // 3
    @Test
    public void TestRegisteringManyWebsites() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.registerSite(w2);
        this.couponOperations.registerSite(w3);

        assertEquals(3, this.couponOperations.getSites().size());
    }

    // 4
    @Test
    public void TestAddingCoupon() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);

        assertTrue(this.couponOperations.exist(c1));
    }

    // 5
    @Test(expected = IllegalArgumentException.class)
    public void TestAddingCouponTwice() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);
        this.couponOperations.addCoupon(w1, c1);
    }

    // 7
    @Test(expected = IllegalArgumentException.class)
    public void TestAddingCouponForNonExistentSite() {
        this.couponOperations.registerSite(w2);
        this.couponOperations.addCoupon(w1, c1);
    }

    // Performance
    @Test
    public void RegisterSitePerf() {
        for (int i = 0; i < 10000; i++) {
            this.couponOperations.registerSite(new Website(i + "", i));
        }

        long start = System.currentTimeMillis();
        this.couponOperations.registerSite(new Website("test", 1));
        long stop = System.currentTimeMillis();
        assertTrue(stop - start <= 20);
    }

    @Test
    public void TestRemoveWebsiteShouldReturnCorrect() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);

        Website website = this.couponOperations.removeWebsite(w1.getDomain());
        assertFalse(this.couponOperations.exist(w1));
        assertFalse(this.couponOperations.exist(c1));
        assertEquals(website, w1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRemoveWebsiteShouldThrowExceptionWhenWebsiteDoesNotExists() {
        this.couponOperations.removeWebsite(w1.getDomain());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRemoveCouponShouldThrowExceptionWhenCouponDoesNotExists() {
        this.couponOperations.removeCoupon(c1.getCode());
    }

    @Test
    public void TestRemoveCouponShouldReturnCorrect() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);
        this.couponOperations.addCoupon(w1, c2);
        this.couponOperations.addCoupon(w1, c3);
        Coupon coupon = this.couponOperations.removeCoupon(c2.getCode());
        assertEquals(coupon, c2);
        assertFalse(this.couponOperations.exist(c2));
    }

    @Test
    public void TestExistsWebsiteShouldReturnTrue() {
        this.couponOperations.registerSite(w1);
        boolean actual = this.couponOperations.exist(w1);
        assertTrue(actual);
    }

    @Test
    public void TestExistsWebsiteShouldReturnFalse() {
        boolean actual = this.couponOperations.exist(w1);
        assertFalse(actual);
    }

    @Test
    public void TestExistsCouponShouldReturnTrue() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);
        boolean actual = this.couponOperations.exist(c1);
        assertTrue(actual);
    }

    @Test
    public void TestExistsCouponShouldReturnFalse() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);
        boolean actual = this.couponOperations.exist(c2);
        assertFalse(actual);
    }

    @Test
    public void TestGetSitesShouldReturnCorrect() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.registerSite(w2);
        String[] expected = {w1.getDomain(), w2.getDomain()};
        Collection<Website> sites = this.couponOperations.getSites();
        assertEquals(2, sites.size());
        int i = 0;
        for (Website site : sites) {
            assertEquals(site.getDomain(), expected[i++]);
        }
    }

    @Test
    public void TestGetCouponsForWebsiteShouldReturnCorrectOnEmptyStorage() {
        this.couponOperations.registerSite(w1);
        Collection<Coupon> coupons = this.couponOperations.getCouponsForWebsite(w1);
        assertEquals(0, coupons.size());
    }

    @Test
    public void TestGetCouponsForWebsiteShouldReturnCorrect() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);
        this.couponOperations.addCoupon(w1, c2);
        Collection<Coupon> coupons = this.couponOperations.getCouponsForWebsite(w1);
        assertEquals(2, coupons.size());
        String[] expected = {"a", "b"};
        int i = 0;
        for (Coupon coupon : coupons) {
            assertEquals(coupon.getCode(), expected[i++]);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestGetCouponsForWebsiteShouldThrowExceptionWhenWebsiteDoesNotExists() {
        this.couponOperations.getCouponsForWebsite(w2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestUseCouponShouldThrowExceptionWhenWebsiteDoesNotExists() {
        this.couponOperations.useCoupon(w1, c1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestUseCouponShouldThrowExceptionWhenCouponDoesNotExists() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.useCoupon(w1, c1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestUseCouponShouldThrowExceptionWhenCouponDoesNotBelongsToWebsite() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.registerSite(w2);
        this.couponOperations.addCoupon(w2, c1);
        this.couponOperations.useCoupon(w1, c1);
    }

    @Test
    public void TestUseCouponShouldReturnCorrect() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);
        this.couponOperations.addCoupon(w1, c2);
        this.couponOperations.useCoupon(w1, c1);
        boolean actual = this.couponOperations.exist(c1);
        assertFalse(actual);
        actual = this.couponOperations.exist(c2);
        assertTrue(actual);
        Collection<Coupon> coupons = this.couponOperations.getCouponsForWebsite(w1);
        assertEquals(1, coupons.size());
    }

    @Test
    public void TestGetCouponsOrderedByValidityDescAndDiscountPercentageDescShouldReturnCorrectOnEmptyStorage() {
        this.couponOperations.registerSite(w1);
        Collection<Coupon> orderedCoupons = this.couponOperations.getCouponsOrderedByValidityDescAndDiscountPercentageDesc();
        assertEquals(0, orderedCoupons.size());
        Collection<Coupon> couponsForWebsite = this.couponOperations.getCouponsForWebsite(w1);
        assertEquals(orderedCoupons, couponsForWebsite);
    }

    @Test
    public void TestGetCouponsOrderedByValidityDescAndDiscountPercentageDescShouldReturnCorrect() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.addCoupon(w1, c1);
        this.couponOperations.addCoupon(w1, c2);
        this.couponOperations.addCoupon(w1, c3);
        this.couponOperations.addCoupon(w1, c4);
        this.couponOperations.addCoupon(w1, c5);
        Collection<Coupon> orderedCoupons = this.couponOperations.getCouponsOrderedByValidityDescAndDiscountPercentageDesc();
        assertEquals(5, orderedCoupons.size());
        String[] expected = {"d", "a", "e", "b", "c"};
        int i = 0;
        for (Coupon coupon : orderedCoupons) {
            assertEquals(coupon.getCode(), expected[i++]);
        }
    }

    @Test
    public void TestGetWebsitesOrderedByUserCountAndCouponsCountDescShouldReturnCorrectOnEmptyStorage() {
        Collection<Website> orderedWebsites = this.couponOperations.getWebsitesOrderedByUserCountAndCouponsCountDesc();
        assertEquals(0, orderedWebsites.size());
    }

    @Test
    public void TestGetWebsitesOrderedByUserCountAndCouponsCountDescShouldReturnCorrect() {
        this.couponOperations.registerSite(w1);
        this.couponOperations.registerSite(w2);
        this.couponOperations.registerSite(w3);
        this.couponOperations.registerSite(w4);
        this.couponOperations.registerSite(w5);
        this.couponOperations.registerSite(w6);

        this.couponOperations.addCoupon(w2, c1);
        this.couponOperations.addCoupon(w3, c2);
        this.couponOperations.addCoupon(w3, c3);
        Collection<Website> orderedWebsites = this.couponOperations.getWebsitesOrderedByUserCountAndCouponsCountDesc();
        assertEquals(6, orderedWebsites.size());
        Collection<Coupon> couponsForWebsiteW2 = this.couponOperations.getCouponsForWebsite(w2);
        Collection<Coupon> couponsForWebsiteW3 = this.couponOperations.getCouponsForWebsite(w3);
        assertEquals(1, couponsForWebsiteW2.size());
        assertEquals(2, couponsForWebsiteW3.size());
        String[] expected = {"a", "c", "b", "d", "e", "f"};
        assertEquals(expected.length, orderedWebsites.size());
        int i = 0;
        for (Website website : orderedWebsites) {
            assertEquals(website.getDomain(), expected[i++]);
        }
    }

}

