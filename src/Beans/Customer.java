package Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer object
 */
public class Customer extends Client{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons;

    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    /**
     * The constructor is in use when new customer is created from admin facade
     */
    public Customer() {
        this.firstName = lineInput("Please, enter customer's First name");
        this.lastName = lineInput("Please, enter customer's Last name");
        this.email = stringInput("Please, enter customer's E-mail");
        this.password = stringInput("Please, enter customer's Password");
    }

    /**
     * @param customer provide information from the old version customer
     * Provides a possibility to change all fields except id
     * The constructor is in use when old customer is updated from admin facade
     */
    public Customer(Customer customer) {
        this.id = customer.getId();
        this.firstName = lineUpdate("Please, enter customer's First name", customer.getFirstName());
        this.lastName = lineUpdate("Please, enter customer's Last name", customer.getLastName());
        this.email = stringUpdate("Please, enter customer's E-mail", customer.getEmail());
        this.password = stringUpdate("Please, enter customer's Password", customer.getPassword());
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        StringBuffer separator = new StringBuffer(", ");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%10d", this.id) + separator.toString())
                .append(String.format("%20s", this.firstName) + separator.toString())
                .append(String.format("%20s", this.lastName) + separator.toString())
                .append(String.format("%40s", this.email) + separator.toString())
                .append(String.format("%15s", this.password) + separator.toString());
        return sb.toString();
    }
}
