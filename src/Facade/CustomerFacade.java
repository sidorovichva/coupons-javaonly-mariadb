package Facade;

import Beans.Customer;
import Exceptions.Exceptions.CheckExceptionsAble;
import Beans.Category;
import Beans.Coupon;
import Exceptions.Exceptions.CouponExceptions;
import Login.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 *facade for the client type Customer
 */
public class CustomerFacade extends ClientFacade implements CheckExceptionsAble {
    /**
     * id of the customer that logged in
     */
    private int customerID;

    /**
     * starts initial Company menu
     */
    public CustomerFacade(int customerID) {
        this.customerID = customerID;
        initialMenu();
    }

    /**
     * creates a menu with initial customer options
     */
    @Override
    void initialMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.BUY_COUPON);
        this.menu.add(MenuOptions.GET_CUSTOMER_COUPONS);
        this.menu.add(MenuOptions.GET_CUSTOMER_DETAILS);
        this.menu.add(MenuOptions.LOG_OUT);
        this.menu.add(MenuOptions.EXIT);
        createMenu();
        menuSwitcher(menuChoice(this.menu.size()));
    }

    /**
     * creates a menu with options related to coupons of the logged in customer
     */
    private void couponsMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.GET_CUSTOMER_COUPONS_ALL);
        this.menu.add(MenuOptions.GET_CUSTOMER_COUPONS_CATEGORY);
        this.menu.add(MenuOptions.GET_CUSTOMER_COUPONS_MAX);
        this.menu.add(MenuOptions.BACK);
        this.menu.add(MenuOptions.EXIT);
        createMenu();
        menuSwitcher(menuChoice(this.menu.size()));
    }

    /**
     * @param choice receives user's choice
     * directs user to the chosen menu option
     */
    @Override
    void menuSwitcher(int choice) {
        switch (this.menu.get(choice - 1)) {
            case BUY_COUPON -> {
                this.couponDBDAO.getAllCoupons().stream().forEach(p-> System.out.println(p));
                int couponID = ID(BeanType.COUPON);
                if (check(couponID, this.couponDBDAO.doesCouponExist(couponID), CouponExceptions.COUPON_DOESNT_EXIST)) {
                    Coupon coupon = this.couponDBDAO.getOneCoupon(couponID);
                    if (check(coupon.getAmount() >= 1, CouponExceptions.INSUFFICIENT_AMOUNT))
                        if (check(coupon.getEndDate().getTime() > System.currentTimeMillis(), CouponExceptions.TIME_IS_OVER))
                            if (check(coupon.getStartDate().getTime() < System.currentTimeMillis(), CouponExceptions.TOO_EARLY))
                                if (check(!this.customersDBDAO.doesPurchaseExist(this.customerID, coupon.getId()), CouponExceptions.PURCHASE_EXISTS)) {
                                    this.couponDBDAO.addCouponPurchase(this.customerID, coupon.getId());
                                    this.couponDBDAO.updateCouponAmount(coupon);
                                }
                }
                initialMenu();
            }
            case GET_CUSTOMER_COUPONS -> {
                couponsMenu();
            }
            case GET_CUSTOMER_COUPONS_ALL -> {
                this.couponDBDAO.getAllCouponsByCustomer(this.customerID)
                        .stream().forEach(p-> System.out.println(p));
                couponsMenu();
            }
            case GET_CUSTOMER_COUPONS_CATEGORY -> {
                this.couponDBDAO.getAllCouponsByCustomer(this.customerID, new Category())
                        .stream().forEach(p -> System.out.println(p));
                couponsMenu();
            }
            case GET_CUSTOMER_COUPONS_MAX -> {
                this.couponDBDAO.getAllCouponsByCustomer(this.customerID, doubleInput("Please, enter max coupon price to show"))
                        .stream().forEach(p -> System.out.println(p));
                couponsMenu();
            }
            case GET_CUSTOMER_DETAILS -> {
                System.out.println(this.customersDBDAO.getOneCustomer(this.customerID));
                initialMenu();
            }
            case BACK -> { initialMenu(); }
            case LOG_OUT -> { LoginManager.getInstance(); }
            case EXIT -> { exit(); }
            default -> { initialMenu(); }
        }
    }

// TEST ================================================================================================================
// TEST ================================================================================================================
// TEST ================================================================================================================

    /**
     * overloaded constructor for test purposes only
     */
    public CustomerFacade(int customerID, boolean isTest) {
        this.customerID = customerID;
    }

    /**
     * @param coupon coupon object
     * checks if the amount of desired coupon is more than 0,
     * checks that start date of the coupon is in the past
     * checks that end date of the coupon is in the future
     * checks if customer already bought this coupon
     * adds purchase if all prerequisites are correct
     * >>>for Test purposes only<<<
     */
    public void purchaseCoupon(Coupon coupon) {
        if (check(coupon.getAmount() >= 1, CouponExceptions.INSUFFICIENT_AMOUNT))
            if (check(coupon.getEndDate().getTime() > System.currentTimeMillis(), CouponExceptions.TIME_IS_OVER))
                if (check(coupon.getStartDate().getTime() < System.currentTimeMillis(), CouponExceptions.TOO_EARLY))
                    if (check(!this.customersDBDAO.doesPurchaseExist(this.customerID, coupon.getId()), CouponExceptions.PURCHASE_EXISTS)) {
                        this.couponDBDAO.addCouponPurchase(this.customerID, coupon.getId());
                        this.couponDBDAO.updateCouponAmount(coupon);
                    }
    }

    /**
     * @return all existing coupons of this customer
     * >>>for Test purposes only<<<
     */
    public List<Coupon> getCustomerCoupons() {
        return this.couponDBDAO.getAllCouponsByCustomer(this.customerID);
    }

    /**
     * @param category specified category
     * @return all existing coupons of this customer by specific category
     * >>>for Test purposes only<<<
     */
    public List<Coupon> getCompanyCoupons(Category category) {
        return this.couponDBDAO.getAllCouponsByCustomer(this.customerID, category);
    }

    /**
     * @param maxPrice only coupons with this price or less will be returned
     * @return all existing coupons of this customer with the price less or equal to maxPrice
     * >>>for Test purposes only<<<
     */
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return this.couponDBDAO.getAllCouponsByCustomer(this.customerID, maxPrice);
    }

    /**
     * @return all field of this company will be returned
     * >>>for Test purposes only<<<
     */
    public Customer getCompanyDetails() {
        Customer customer = this.customersDBDAO.getOneCustomer(this.customerID);
        customer.setCoupons((ArrayList<Coupon>) this.couponDBDAO.getAllCouponsByCustomer(this.customerID));
        return customer;
    }

}


