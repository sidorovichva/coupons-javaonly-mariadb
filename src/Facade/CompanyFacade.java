package Facade;

import Beans.Company;
import Exceptions.Exceptions.CheckExceptionsAble;
import Beans.Category;
import Beans.Coupon;
import Exceptions.Exceptions.CouponExceptions;
import Login.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 *facade for the client type Company
 */
public class CompanyFacade extends ClientFacade implements CheckExceptionsAble {
    /**
     * id of the company that logged in
     */
    private int companyID;

    /**
     * starts initial Company menu
     */
    public CompanyFacade(int companyID) {
        this.companyID = companyID;
        initialMenu();
    }

    /**
     * creates a menu with initial company options
     */
    @Override
    protected void initialMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.ADD_COUPON);
        this.menu.add(MenuOptions.UPDATE_COUPON);
        this.menu.add(MenuOptions.DELETE_COUPON);
        this.menu.add(MenuOptions.GET_COMPANY_COUPONS);
        this.menu.add(MenuOptions.GET_COMPANY_DETAILS);
        this.menu.add(MenuOptions.LOG_OUT);
        this.menu.add(MenuOptions.EXIT);
        createMenu();
        menuSwitcher(menuChoice(this.menu.size()));
    }

    /**
     * creates a menu with options related to coupons of the logged in company
     */
    private void couponsMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.GET_COMPANY_COUPONS_ALL);
        this.menu.add(MenuOptions.GET_COMPANY_COUPONS_CATEGORY);
        this.menu.add(MenuOptions.GET_COMPANY_COUPONS_MAX);
        this.menu.add(MenuOptions.GET_ALL_SOLD_COUPONS_BY_COMPANY);
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
    protected void menuSwitcher(int choice) {
        switch (this.menu.get(choice - 1)) {
            case BACK -> { initialMenu(); }
            case LOG_OUT -> { LoginManager.getInstance(); }
            case EXIT -> { exit(); }
            case ADD_COUPON -> {
                Coupon coupon = new Coupon(this.companyID);
                if (check(!this.couponDBDAO.doesCouponExist(coupon.getTitle(), this.companyID), CouponExceptions.COUPON_ALREADY_EXISTS))
                    this.couponDBDAO.addCoupon(coupon);
                initialMenu();
            }
            case UPDATE_COUPON -> {
                int couponID = ID(BeanType.COUPON);
                if (check(couponID, this.couponDBDAO.doesCouponExist(couponID, this.companyID), CouponExceptions.COUPON_NOT_AVAILABLE))
                    this.couponDBDAO.updateCoupon(new Coupon(this.couponDBDAO.getOneCoupon(couponID)));
                initialMenu();
            }
            case DELETE_COUPON -> {
                int couponID = ID(BeanType.COUPON);
                if (check(couponID, this.couponDBDAO.doesCouponExist(couponID, this.companyID), CouponExceptions.COUPON_NOT_AVAILABLE))
                    this.couponDBDAO.deleteCoupon(couponID);
                initialMenu();
            }
            case GET_COMPANY_COUPONS -> { couponsMenu(); }
            case GET_COMPANY_COUPONS_ALL -> {
                this.couponDBDAO.getAllCoupons(this.companyID).stream().forEach(p-> System.out.println("\t" + p));
                couponsMenu();
            }
            case GET_COMPANY_COUPONS_CATEGORY -> {
                this.couponDBDAO.getAllCoupons(this.companyID, new Category()).stream().forEach(p-> System.out.println("\t" + p));
                couponsMenu();
            }
            case GET_COMPANY_COUPONS_MAX -> {
                double maxPrice = doubleInput("Please, enter max coupon price to show");
                this.couponDBDAO.getAllCoupons(this.companyID, maxPrice).stream().forEach(p-> System.out.println("\t" + p));
                couponsMenu();
            }
            case GET_ALL_SOLD_COUPONS_BY_COMPANY -> {
                this.reportsDBDAO.getNumberOfSoldCouponsByCouponByCompany(this.companyID);
                couponsMenu();
            }
            case GET_COMPANY_DETAILS -> {
                System.out.println(this.companiesDBDAO.getOneCompany(this.companyID));
                this.couponDBDAO.getAllCoupons(this.companyID).stream().forEach(p -> System.out.println(p));
                initialMenu();
            }
            default -> { initialMenu(); }
        }
    }

// TEST ================================================================================================================
// TEST ================================================================================================================
// TEST ================================================================================================================
    /**
     * overloaded constructor for test purposes only
     */
    public CompanyFacade(int companyID, boolean isTest) {
        this.companyID = companyID;
    }

    /**
     * @param coupon coupon object
     * creates a new coupon in the coupon table of the DB
     * >>>for Test purposes only<<<
     */
    public void addCoupon(Coupon coupon) {
        this.couponDBDAO.addCoupon(coupon);
    }

    /**
     * @param coupon coupon object
     * updates an existing coupon in the coupon table of the DB
     * >>>for Test purposes only<<<
     */
    public void updateCoupon(Coupon coupon) {
        this.couponDBDAO.updateCoupon(coupon);
    }

    /**
     * @param couponID coupon object
     * deletes an existing coupon in the coupon table of the DB
     * >>>for Test purposes only<<<
     */
    public void deleteCoupon(int couponID) {
        this.couponDBDAO.deleteCoupon(couponID);
    }

    /**
     * @return all existing coupons of this company
     * >>>for Test purposes only<<<
     */
    public List<Coupon> getCompanyCoupons() {
        return this.couponDBDAO.getAllCoupons(this.companyID);
    }

    /**
     * @param  category only coupons from this category will be returned
     * @return all existing coupons of this company by specific category
     * >>>for Test purposes only<<<
     */
    public List<Coupon> getCompanyCoupons(Category category) {
        return this.couponDBDAO.getAllCoupons(this.companyID, category);
    }

    /**
     * @param maxPrice only coupons with this price or less will be returned
     * @return all existing coupons of this company with the price less or equal to maxPrice
     * >>>for Test purposes only<<<
     */
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return this.couponDBDAO.getAllCoupons(this.companyID, maxPrice);
    }

    /**
     * @return all field of this company will be returned
     * >>>for Test purposes only<<<
     */
    public Company getCompanyDetails() {
        Company company = this.companiesDBDAO.getOneCompany(this.companyID);
        company.setCoupons((ArrayList<Coupon>) this.couponDBDAO.getAllCoupons(this.companyID));
        return company;
    }
}
