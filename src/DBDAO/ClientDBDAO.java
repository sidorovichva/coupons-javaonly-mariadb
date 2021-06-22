package DBDAO;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.Exceptions.CheckExceptionsAble;
import Auxiliaries.MapAble;
import Beans.Company;
import Exceptions.Exceptions.CouponExceptions;
import Exceptions.Notifications.CheckNotificationsAble;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  abstract DBDAO class that provides actual DBDAO classes with all mutual data and methods
 */
public abstract class ClientDBDAO implements CheckExceptionsAble, CheckNotificationsAble, MapAble {
    /**
     * mock lists and objects to overload existing methods
     */
    protected static List<Company> mockCompanyList = new ArrayList<>();
    protected static List<Coupon> mockCouponList = new ArrayList<>();
    protected static List<Category> mockCategoryList = new ArrayList<>();
    protected static List<Customer> mockCustomerList = new ArrayList<>();
    protected static boolean mockBoolean = false;
    protected static Company mockCompany = null;
    protected static Coupon mockCoupon = null;
    protected static Category mockCategory = null;
    protected static Customer mockCustomer = null;

    /**
     * @param set result set received from the SQL DB
     * @param exception exception to throw in case ResultSet contains nothing
     * @param company mock variable to overload existing method
     * @return a Company object
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected Company dataExtraction(ResultSet set, CouponExceptions exception, Company company) {
        company = null;
        try {
            if (check(set.next(), exception)) {
                company = new Company(set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return company;
    }

    /**
     * @param set result set received from the SQL DB
     * @param exception exception to throw in case ResultSet contains nothing
     * @param coupon mock variable to overload existing method
     * @return a Coupon object
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected Coupon dataExtraction(ResultSet set, CouponExceptions exception, Coupon coupon) {
        coupon = null;
        try {
            if (check(set.next(), exception)) {
                coupon = new Coupon(set.getInt(1),
                        set.getInt(2),
                        set.getInt(3),
                        set.getString(4),
                        set.getString(5),
                        set.getDate(6),
                        set.getDate(7),
                        set.getInt(8),
                        set.getDouble(9),
                        set.getString(10));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return coupon;
    }

    /**
     * @param set result set received from the SQL DB
     * @param exception exception to throw in case ResultSet contains nothing
     * @param customer mock variable to overload existing method
     * @return a Customer object
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected Customer dataExtraction(ResultSet set, CouponExceptions exception, Customer customer) {
        customer = null;
        try {
            if (check(set.next(), exception)) {
                customer = new Customer(set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    /**
     * @param set result set received from the SQL DB
     * @param exception exception to throw in case ResultSet contains nothing
     * @param companyList mock list to overload existing method
     * @param company mock variable to overload existing method
     * @return a list of companies
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected List<Company> dataExtraction(ResultSet set, CouponExceptions exception, List<Company> companyList, Company company) {
        companyList.clear();
        try {
            if (check(set.isBeforeFirst(), exception)) {
                while (set.next()) {
                    companyList.add(new Company(set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getString(4)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return companyList;
    }

    /**
     * @param set result set received from the SQL DB
     * @param exception exception to throw in case ResultSet contains nothing
     * @param couponList mock list to overload existing method
     * @param coupon mock variable to overload existing method
     * @return a list of coupons
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected List<Coupon> dataExtraction(ResultSet set, CouponExceptions exception, List<Coupon> couponList, Coupon coupon) {
        couponList.clear();
        try {
            if (check(set.isBeforeFirst(), exception)) {
                while (set.next()) {
                    couponList.add(new Coupon(set.getInt(1),
                            set.getInt(2),
                            set.getInt(3),
                            set.getString(4),
                            set.getString(5),
                            set.getDate(6),
                            set.getDate(7),
                            set.getInt(8),
                            set.getDouble(9),
                            set.getString(10)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return couponList;
    }

    /**
     * @param set result set received from the SQL DB
     * @param exception exception to throw in case ResultSet contains nothing
     * @param categoryList mock list to overload existing method
     * @param category mock variable to overload existing method
     * @return a list of categories
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected List<Category> dataExtraction(ResultSet set, CouponExceptions exception, List<Category> categoryList, Category category) {
        categoryList.clear();
        try {
            if (check(set.isBeforeFirst(), exception)) {
                while (set.next()) {
                    categoryList.add(new Category(new StringBuffer(set.getString(2)),
                            set.getInt(1)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categoryList;
    }

    /**
     * @param set result set received from the SQL DB
     * @param exception exception to throw in case ResultSet contains nothing
     * @param customerList mock list to overload existing method
     * @param customer mock variable to overload existing method
     * @return a list of customers
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected List<Customer> dataExtraction(ResultSet set, CouponExceptions exception, List<Customer> customerList, Customer customer) {
        customerList.clear();
        try {
            if (check(set.isBeforeFirst(), exception)) {
                while (set.next()) {
                    customerList.add(new Customer(set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getString(4),
                            set.getString(5)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * @param set result set received from the SQL DB
     * @param mockBoolean mock boolean to overload existing method
     * @return a boolean
     * extracts data from the ResultSet and throws exception in case the set is null;
     */
    protected boolean dataExtraction(ResultSet set, boolean mockBoolean) {
        try {
            if (set.next()) {
                return true;
            }
        } catch (NullPointerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
