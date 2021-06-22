package DBDAO;

import Beans.Category;
import Beans.Coupon;
import DAO.CouponDAO;
import DB.CommandBuilder;
import DB.DBUtils;
import DB.SQL;
import Exceptions.Exceptions.CouponExceptions;
import Exceptions.Notifications.Notifications;

import java.sql.Date;
import java.util.*;

/**
 *Data Access Object related to the Coupon object and Category object
 */
public class CouponsDBDAO extends ClientDBDAO implements CouponDAO {
    /**
     * sql command: return a coupon by id
     */
    public static final StringBuffer DOES_COUPON_EXIST_BY_ID = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_ID).build();

    /**
     * sql command: return a coupon by id and company_id
     */
    public static final StringBuffer DOES_COUPON_EXIST_BY_COUPON_ID_AND_COMPANY_ID = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_ID).and(SQL.coupon_COMPANY_ID).build();

    /**
     * sql command: return a coupon by title and company_id
     */
    public static final StringBuffer DOES_COUPON_EXIST_BY_COUPON_TITLE_AND_COMPANY_ID = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_TITLE).and(SQL.coupon_COMPANY_ID).build();

    /**
     * sql command: return coupons with end_date less than specified date
     */
    public static final StringBuffer DOES_COUPON_EXIST_BY_END_DATE = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_END_DATE, "<").limit().build();

    /**
     * sql command: return category with specified name
     */
    public static final StringBuffer IF_CATEGORY_EXISTS = new CommandBuilder()
            .select().from(SQL.CATEGORIES)
            .where(SQL.category_NAME).build();

    /**
     * sql command: return first found coupon with specified company_id
     */
    public static final StringBuffer IF_COMPANY_HAS_COUPONS = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_COMPANY_ID).limit().build();

    /**
     * sql command: return first found purchase with specified customer_id
     */
    public static final StringBuffer IF_CUSTOMER_HAS_COUPONS = new CommandBuilder()
            .select().from(SQL.CUSTOMERS_VS_COUPONS)
            .where(SQL.customers_vs_coupons_CUSTOMER_ID).limit().build();

    /**
     * sql command: delete purchase with specified customer_id and coupon_id
     */
    public static final StringBuffer DELETE_PURCHASE_BY_CUSTOMER_ID_AND_BY_COUPON_ID = new CommandBuilder()
            .delete().from(SQL.CUSTOMERS_VS_COUPONS)
            .where(SQL.customers_vs_coupons_CUSTOMER_ID).and(SQL.customers_vs_coupons_COUPON_ID).build();

    /**
     * sql command: delete coupon with specified id
     */
    public static final StringBuffer DELETE_COUPON = new CommandBuilder()
            .delete(SQL.COUPONS)
            .where(SQL.coupon_ID).build();

    /**
     * sql command: delete all coupons with end_date less than specified date
     */
    public static final StringBuffer DELETE_ALL_COUPON_BY_END_DATE = new CommandBuilder()
            .delete(SQL.COUPONS)
            .where(SQL.coupon_END_DATE, "<").build();

    /**
     * sql command: return all existing coupons
     */
    public static final StringBuffer GET_ALL_COUPONS = new CommandBuilder()
            .select().from(SQL.COUPONS).build();

    /**
     * sql command: return all existing coupons by specified company_id
     */
    public static final StringBuffer GET_ALL_COUPONS_BY_COMPANY = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_COMPANY_ID).build();

    /**
     * sql command: return all coupons purchased by specified customer
     */
    public static final StringBuffer GET_ALL_COUPONS_BY_CUSTOMER = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .innerJoin(SQL.CUSTOMERS_VS_COUPONS, SQL.coupon_ID, SQL.customers_vs_coupons_COUPON_ID)
            .where(SQL.customers_vs_coupons_CUSTOMER_ID).build();

    /**
     * sql command: return all coupons by specified company_id and with price less or equal to specified price
     */
    public static final StringBuffer GET_ALL_COUPONS_BY_COMPANY_ID_AND_PRICE = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_COMPANY_ID).and(SQL.coupon_PRICE, "<=").build();

    /**
     * sql command: return all coupons by specified company_id and category_id
     */
    public static final StringBuffer GET_ALL_COUPONS_BY_COMPANY_AND_CATEGORY = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_COMPANY_ID).and(SQL.coupon_CATEGORY_ID).build();

    /**
     * sql command: return all coupons by specified category_id purchased by specified customer
     */
    public static final StringBuffer GET_ALL_COUPONS_BY_CUSTOMER_AND_CATEGORY = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .innerJoin(SQL.CUSTOMERS_VS_COUPONS, SQL.coupon_ID, SQL.customers_vs_coupons_COUPON_ID)
            .innerJoin(SQL.CATEGORIES, SQL.category_ID, SQL.coupon_CATEGORY_ID)
            .where(SQL.customers_vs_coupons_CUSTOMER_ID).and(SQL.category_ID).build();

    /**
     * sql command: return all coupons with price less or equal to specified price and purchased by specified customer
     */
    public static final StringBuffer GET_ALL_COUPONS_BY_CUSTOMER_ID_AND_PRICE = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .innerJoin(SQL.CUSTOMERS_VS_COUPONS, SQL.coupon_ID, SQL.customers_vs_coupons_COUPON_ID)
            .where(SQL.customers_vs_coupons_CUSTOMER_ID).and(SQL.coupon_PRICE, "<=").build();

    /**
     * sql command: return all categories
     */
    public static final StringBuffer GET_ALL_CATEGORIES = new CommandBuilder()
            .select().from(SQL.CATEGORIES).build();

    /**
     * sql command: return a coupon by coupon_id
     */
    public static final StringBuffer GET_ONE_COUPON = new CommandBuilder()
            .select().from(SQL.COUPONS)
            .where(SQL.coupon_ID).build();

    /**
     * sql command: add a category with specified name
     */
    public static final StringBuffer ADD_CATEGORY = new CommandBuilder()
            .insert(SQL.CATEGORIES, new SQL[] {SQL.category_NAME}).build();

    /**
     * sql command: add a company with specified fields
     */
    public static final StringBuffer ADD_COUPON = new CommandBuilder()
            .insert(SQL.COUPONS, new SQL[] {SQL.coupon_COMPANY_ID, SQL.coupon_CATEGORY_ID, SQL.coupon_TITLE, SQL.coupon_DESCRIPTION,
                SQL.coupon_START_DATE, SQL.coupon_END_DATE, SQL.coupon_AMOUNT, SQL.coupon_PRICE, SQL.coupon_IMAGE}).build();

    /**
     * sql command: add purchase with specified fields
     */
    public static final StringBuffer ADD_PURCHASE = new CommandBuilder()
            .insert(SQL.CUSTOMERS_VS_COUPONS, new SQL[]{SQL.customers_vs_coupons_CUSTOMER_ID, SQL.customers_vs_coupons_COUPON_ID}).build();

    /**
     * sql command: update all fields of the specified by id coupon
     */
    public static final StringBuffer UPDATE_COUPON = new CommandBuilder()
            .update(SQL.COUPONS, new SQL[]{SQL.coupon_CATEGORY_ID, SQL.coupon_TITLE, SQL.coupon_DESCRIPTION,
                SQL.coupon_START_DATE, SQL.coupon_END_DATE, SQL.coupon_AMOUNT, SQL.coupon_PRICE, SQL.coupon_IMAGE})
            .where(SQL.coupon_ID).build();

    /**
     * sql command: update an amount of the specified by id coupon
     */
    public static final StringBuffer UPDATE_COUPON_AMOUNT = new CommandBuilder()
            .update(SQL.COUPONS, SQL.coupon_AMOUNT)
            .where(SQL.coupon_ID).build();

    /**
     * @param couponID coupon id
     * @return true if a coupon with such id exists
     */
    public boolean doesCouponExist(int couponID) {
        return dataExtraction(DBUtils.runQuery(getMap(couponID), DOES_COUPON_EXIST_BY_ID), mockBoolean);
    }

    /**
     * @param couponID coupon id
     * @param companyID company id
     * @return true if a coupon with such id and company_id exists
     */
    public boolean doesCouponExist(int couponID, int companyID) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{couponID, companyID}), DOES_COUPON_EXIST_BY_COUPON_ID_AND_COMPANY_ID), mockBoolean);
    }

    /**
     * @param couponTitle coupon title
     * @param companyID company id
     * @return true if a coupon with such title and company_id exists
     */
    public boolean doesCouponExist(String couponTitle, int companyID) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{couponTitle, companyID}), DOES_COUPON_EXIST_BY_COUPON_TITLE_AND_COMPANY_ID), mockBoolean);
    }

    /**
     * @param date coupon date
     * @return true if a coupon with end_date less than date exists
     */
    public boolean doesCouponExist(Date date) {
        return dataExtraction(DBUtils.runQuery(getMap(date), DOES_COUPON_EXIST_BY_END_DATE), mockBoolean);
    }

    /**
     * @param companyID company id
     * @return true if a company has any coupons
     */
    public boolean doesCompanyHaveCoupons(int companyID) {
        return dataExtraction(DBUtils.runQuery(getMap(companyID), IF_COMPANY_HAS_COUPONS), mockBoolean);
    }

    /**
     * @param customerID customer id
     * @return true if customer has any coupons
     */
    public boolean doesCustomerHaveCoupons(int customerID) {
        return dataExtraction(DBUtils.runQuery(getMap(customerID), IF_CUSTOMER_HAS_COUPONS), mockBoolean);
    }

    /**
     * @param coupon coupon id
     * adds a new coupon to Coupons table
     */
    @Override
    public void addCoupon(Coupon coupon) {
        check((DBUtils.runUpdate(getMap(
                new Object[]{coupon.getCompanyID(),
                    coupon.getCategory().getNumber(),
                    coupon.getTitle(),
                    coupon.getDescription(),
                    coupon.getStartDate(),
                    coupon.getEndDate(),
                    coupon.getAmount(),
                    coupon.getPrice(),
                    coupon.getImage()}),
                ADD_COUPON)),
                Notifications.ADD_COUPON_SUCCESS, Notifications.ADD_COUPON_FAIL);
    }

    /**
     * @param coupon coupon id
     * update fields of the specified coupon
     */
    @Override
    public void updateCoupon(Coupon coupon) {
        check((DBUtils.runUpdate(getMap(
                new Object[]{coupon.getCategory().getNumber(),
                    coupon.getTitle(),
                    coupon.getDescription(),
                    coupon.getStartDate(),
                    coupon.getEndDate(),
                    coupon.getAmount(),
                    coupon.getPrice(),
                    coupon.getImage(),
                    coupon.getId()}),
                UPDATE_COUPON)),
                Notifications.UPDATE_COUPON_SUCCESS, Notifications.UPDATE_COUPON_FAIL);
    }

    /**
     * @param coupon coupon id
     * reduces the amount of the specified coupon by 1
     */
    public void updateCouponAmount(Coupon coupon) {
        DBUtils.runUpdate(getMap(new Object[]{(coupon.getAmount() - 1), coupon.getId()}), UPDATE_COUPON_AMOUNT);
    }

    /**
     * @param couponID coupon id
     * deletes a specified coupon
     */
    @Override
    public void deleteCoupon(int couponID) {
        check((DBUtils.runUpdate(getMap(couponID), DELETE_COUPON)), Notifications.DELETE_COUPON_SUCCESS, Notifications.DELETE_COUPON_FAIL);
    }

    /**
     * @param date specified end date
     * deletes all coupons with end date less than provided date
     */
    public void deleteAllCouponsByEndDate(Date date) {
        DBUtils.runUpdate(getMap(date), DELETE_ALL_COUPON_BY_END_DATE);
    }

    /**
     * @return returns all existing coupons
     */
    @Override
    public List<Coupon> getAllCoupons() {
        return dataExtraction(DBUtils.runQuery(getMap(), GET_ALL_COUPONS), CouponExceptions.COUPONS_WERENT_FOUND, mockCouponList, mockCoupon);
    }

    /**
     * @param companyID company id
     * @return returns all coupons that belong to a specified company
     */
    public List<Coupon> getAllCoupons(int companyID) {
        return dataExtraction(DBUtils.runQuery(getMap(companyID), GET_ALL_COUPONS_BY_COMPANY), CouponExceptions.COUPONS_WERENT_FOUND, mockCouponList, mockCoupon);
    }

    /**
     * @param customerID customer id
     * @return returns all coupons that were bought by a specified customer
     */
    public List<Coupon> getAllCouponsByCustomer(int customerID) {
        return dataExtraction(DBUtils.runQuery(getMap(customerID), GET_ALL_COUPONS_BY_CUSTOMER), CouponExceptions.COUPONS_WERENT_FOUND, mockCouponList, mockCoupon);
    }

    /**
     * @param customerID customer id
     * @param maxPrice max coupon price
     * @return returns all coupons that cost less than maxPrice (included) and were bought by specified customer
     */
    public List<Coupon> getAllCouponsByCustomer(int customerID, double maxPrice) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{customerID, maxPrice}), GET_ALL_COUPONS_BY_CUSTOMER_ID_AND_PRICE),
                CouponExceptions.COUPONS_WERENT_FOUND, mockCouponList, mockCoupon);
    }

    /**
     * @param companyID company id
     * @param maxPrice max coupon price
     * @return returns all coupons that cost less than maxPrice (included) and were created by specified company
     */
    public List<Coupon> getAllCoupons(int companyID, double maxPrice) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{companyID, maxPrice}),
                GET_ALL_COUPONS_BY_COMPANY_ID_AND_PRICE), CouponExceptions.COUPONS_WERENT_FOUND, mockCouponList, mockCoupon);
    }

    /**
     * @param companyID company id
     * @param category coupon category
     * @return returns all coupons by specified category and that were created by specified company
     */
    public List<Coupon> getAllCoupons(int companyID, Category category) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{companyID, category.getNumber()}),
                GET_ALL_COUPONS_BY_COMPANY_AND_CATEGORY), CouponExceptions.COUPONS_WERENT_FOUND, mockCouponList, mockCoupon);
    }

    /**
     * @param customerID customer id
     * @param category coupon category
     * @return returns all coupons by specified category and that were bought by specified customer
     */
    public List<Coupon> getAllCouponsByCustomer(int customerID, Category category) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{customerID, category.getNumber()}),
                GET_ALL_COUPONS_BY_CUSTOMER_AND_CATEGORY), CouponExceptions.COUPONS_WERENT_FOUND, mockCouponList, mockCoupon);
    }

    /**
     * @param couponID coupon id
     * @return returns a coupon by specified id
     */
    @Override
    public Coupon getOneCoupon(int couponID) {
        return dataExtraction(DBUtils.runQuery(getMap(couponID), GET_ONE_COUPON), CouponExceptions.COUPON_DOESNT_EXIST, mockCoupon);
    }

    /**
     * @param customerID customer id
     * @param couponID coupon id
     * adds a purchase with specifies coupon and customer ids
     */
    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        check((DBUtils.runUpdate(getMap(new Object[]{customerID, couponID}), ADD_PURCHASE)), Notifications.PURCHASE_SUCCESS, Notifications.PURCHASE_FAIL);
    }

    /**
     * @param customerID customer id
     * @param couponID coupon id
     * deletes a purchase by customer and coupon ids
     */
    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        DBUtils.runUpdate(getMap(new Object[]{customerID, couponID}), DELETE_PURCHASE_BY_CUSTOMER_ID_AND_BY_COUPON_ID);
    }

    /**
     * @param category category name
     * @return returns true if the specified category exists
     * */
    public boolean checkCouponCategoryExistence(StringBuffer category) {
        return dataExtraction(DBUtils.runQuery(getMap(category.toString()), IF_CATEGORY_EXISTS), mockBoolean);
    }

    /**
     * @param category category name
     * adds a new category to th category table
     * */
    public void addCategory(StringBuffer category) {
        check((DBUtils.runUpdate(getMap(category.toString()), ADD_CATEGORY)), Notifications.ADD_CATEGORY_SUCCESS, Notifications.ADD_CATEGORY_FAIL);
    }

    /**
     * @param category category name
     * adds a new initial category to th category table
     * */
    public void addInitialCategory(StringBuffer category) {
        DBUtils.runUpdate(getMap(category.toString()), ADD_CATEGORY);
    }

    /**
     * @return list of all categories
     * */
    public List<Category> getAllCategories() {
        return dataExtraction(DBUtils.runQuery(getMap(), GET_ALL_CATEGORIES), CouponExceptions.CATEGORIES_WERENT_FOUND, mockCategoryList, mockCategory);
    }
}
