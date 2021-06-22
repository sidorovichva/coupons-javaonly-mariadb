package DAO;

import Beans.Customer;

import java.util.List;

/**
 *Data Access Object interface with method signatures related to the Customer object
 */
public interface CustomersDAO {
    boolean isCustomerExists(String email, String password);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerID);
    List<Customer> getAllCustomers();
    Customer getOneCustomer(int customerID);
}
