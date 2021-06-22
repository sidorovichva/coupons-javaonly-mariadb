package DBDAO;

import Beans.Customer;
import DAO.CustomersDAO;
import DB.CommandBuilder;
import DB.DBUtils;
import DB.SQL;
import Exceptions.Exceptions.CouponExceptions;
import Exceptions.Notifications.Notifications;

import java.util.List;

/**
 *Data Access Object related to the Customer object
 */
public class CustomersDBDAO extends ClientDBDAO implements CustomersDAO {
    /**
     * sql command: return a customer by e-mail and password
     */
    private static final StringBuffer IF_CUSTOMER_EXISTS_BY_MAIL_AND_PASS = new CommandBuilder()
            .select().from(SQL.CUSTOMERS)
            .where(SQL.customer_EMAIL).and(SQL.customer_PASSWORD).build();

    /**
     * sql command: return a customer by id
     */
    private static final StringBuffer IF_CUSTOMER_EXISTS_BY_ID = new CommandBuilder()
            .select().from(SQL.CUSTOMERS).where(SQL.customer_ID).build();

    /**
     * sql command: return a purchase by coupon id and customer id
     */
    private static final StringBuffer IF_PURCHASE_EXISTS = new CommandBuilder()
            .select().from(SQL.CUSTOMERS_VS_COUPONS)
            .where(SQL.customers_vs_coupons_CUSTOMER_ID).and(SQL.customers_vs_coupons_COUPON_ID).build();

    /**
     * sql command: add a customer with specified fields
     */
    private static final StringBuffer ADD_CUSTOMER = new CommandBuilder()
            .insert(SQL.CUSTOMERS, new SQL[]{SQL.customer_FIRST_NAME, SQL.customer_LAST_NAME, SQL.customer_EMAIL, SQL.customer_PASSWORD}).build();

    /**
     * sql command: update specified by id customer with specified fields
     */
    private static final StringBuffer UPDATE_CUSTOMER = new CommandBuilder()
            .update(SQL.CUSTOMERS, new SQL[]{SQL.customer_FIRST_NAME, SQL.customer_LAST_NAME, SQL.customer_EMAIL, SQL.customer_PASSWORD})
            .where(SQL.customer_ID).build();

    /**
     * sql command: delete specified by id customer
     */
    private static final StringBuffer DELETE_CUSTOMER = new CommandBuilder()
            .delete(SQL.CUSTOMERS)
            .where(SQL.customer_ID).build();

    /**
     * sql command: return all exiting customers
     */
    private static final StringBuffer GET_ALL_CUSTOMERS = new CommandBuilder()
            .select().from(SQL.CUSTOMERS).build();

    /**
     * @param email customer email
     * @param password customer password
     * @return true if a customer with such e-mail and password exists
     */
    @Override
    public boolean isCustomerExists(String email, String password) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{email, password}), IF_CUSTOMER_EXISTS_BY_MAIL_AND_PASS), mockBoolean);
    }

    /**
     * @param customerID customer id
     * @return true if a customer with such id exists
     */
    public boolean isCustomerExist(int customerID) {
        return dataExtraction(DBUtils.runQuery(getMap(customerID), IF_CUSTOMER_EXISTS_BY_ID), mockBoolean);
    }

    /**
     * @param customerID customer id
     * @param couponID coupon id
     * @return true if a purchase with such customer id and coupon id exists
     */
    public boolean doesPurchaseExist(int customerID, int couponID) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{customerID, couponID}), IF_PURCHASE_EXISTS), mockBoolean);
    }

    /**
     * @param customer customer object
     * adds a new customer with specified fields to the customer table
     * */
    @Override
    public void addCustomer(Customer customer) {
        check((DBUtils.runUpdate(getMap(
                new Object[]{customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getPassword()}),
                ADD_CUSTOMER)), Notifications.ADD_CUSTOMER_SUCCESS, Notifications.ADD_CUSTOMER_FAIL);
    }

    /**
     * @param customer customer object
     * updates all customer's fields
     * */
    @Override
    public void updateCustomer(Customer customer) {
        check((DBUtils.runUpdate(getMap(
                new Object[]{customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getPassword(),
                        customer.getId()}),
                UPDATE_CUSTOMER)), Notifications.UPDATE_CUSTOMER_SUCCESS, Notifications.UPDATE_CUSTOMER_FAIL);
    }

    /**
     * @param customerID customer id
     * deletes customer by specified id
     * */
    @Override
    public void deleteCustomer(int customerID) {
        //map.put(1, customerID);
        check((DBUtils.runUpdate(getMap(customerID), DELETE_CUSTOMER)), Notifications.DELETE_CUSTOMER_SUCCESS, Notifications.DELETE_CUSTOMER_FAIL);
    }

    /**
     * @return a list of all customers
     * */
    @Override
    public List<Customer> getAllCustomers() {
        return dataExtraction(DBUtils.runQuery(getMap(),
                GET_ALL_CUSTOMERS), CouponExceptions.CUSTOMERS_WERENT_FOUND, mockCustomerList, mockCustomer);
    }

    /**
     * @param customerID customer id
     * @return a customer with the specified id
     * */
    @Override
    public Customer getOneCustomer(int customerID) {
        return dataExtraction(DBUtils.runQuery(getMap(customerID),
                IF_CUSTOMER_EXISTS_BY_ID), CouponExceptions.CUSTOMER_DOESNT_EXIST, mockCustomer);
    }

    /**
     * @param email customer e-mail
     * @param password customer password
     * @return a customer with the specified email and password
     * */
    public Customer getOneCustomerByMailAndPass(String email, String password) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{email, password}),
                IF_CUSTOMER_EXISTS_BY_MAIL_AND_PASS), CouponExceptions.CUSTOMER_DOESNT_EXIST, mockCustomer);
    }
}
