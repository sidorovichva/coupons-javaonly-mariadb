package DAO;

import Beans.Coupon;

import java.util.List;

/**
 *Data Access Object interface with method signatures related to the Coupon object
 */
public interface CouponDAO {
    void addCoupon(Coupon coupon);
    void updateCoupon(Coupon coupon);
    void deleteCoupon(int couponID);
    List<Coupon> getAllCoupons();
    Coupon getOneCoupon(int couponID);
    void addCouponPurchase(int customerID, int couponID);
    void deleteCouponPurchase(int customerID, int couponID);
}
