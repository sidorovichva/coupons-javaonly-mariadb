package Facade;

import Exceptions.Exceptions.CheckExceptionsAble;
import Beans.Category;
import Beans.Company;
import Beans.Customer;
import Exceptions.Exceptions.CouponExceptions;
import Login.LoginManager;

import java.util.List;

/**
 *facade for the client type Administrator
 */
public class AdminFacade extends ClientFacade implements CheckExceptionsAble {
    /**
     * starts initial Admin menu
     */
    public AdminFacade() {
        initialMenu();
    }

    /**
     * creates a menu with initial admin options
     */
    @Override
    protected void initialMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.COMPANIES);
        this.menu.add(MenuOptions.CUSTOMERS);
        this.menu.add(MenuOptions.CATEGORIES);
        this.menu.add(MenuOptions.REPORTS);
        this.menu.add(MenuOptions.LOG_OUT);
        this.menu.add(MenuOptions.EXIT);
        createMenu();
        menuSwitcher(menuChoice(this.menu.size()));
    }

    /**
     * creates a menu with options related to client type Company and accessible by Admin
     */
    private void companiesMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.ADD_COMPANY);
        this.menu.add(MenuOptions.UPDATE_COMPANY);
        this.menu.add(MenuOptions.DELETE_COMPANY);
        this.menu.add(MenuOptions.GET_ALL_COMPANIES);
        this.menu.add(MenuOptions.GET_ONE_COMPANY);
        this.menu.add(MenuOptions.BACK);
        this.menu.add(MenuOptions.EXIT);
        createMenu();
        menuSwitcher(menuChoice(this.menu.size()));
    }

    /**
     * creates a menu with options related to client type Customer and accessible by Admin
     */
    private void customersMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.ADD_CUSTOMER);
        this.menu.add(MenuOptions.UPDATE_CUSTOMER);
        this.menu.add(MenuOptions.DELETE_CUSTOMER);
        this.menu.add(MenuOptions.GET_ALL_CUSTOMERS);
        this.menu.add(MenuOptions.GET_ONE_CUSTOMER);
        this.menu.add(MenuOptions.BACK);
        this.menu.add(MenuOptions.EXIT);
        createMenu();
        menuSwitcher(menuChoice(this.menu.size()));
    }

    /**
     * creates a menu with options related to Categories of Coupons and accessible by Admin
     */
    private void categoriesMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.ADD_CATEGORY);
        this.menu.add(MenuOptions.GET_ALL_CATEGORIES);
        this.menu.add(MenuOptions.BACK);
        this.menu.add(MenuOptions.EXIT);
        createMenu();
        menuSwitcher(menuChoice(this.menu.size()));
    }

    /**
     * creates a menu with options related to general reports and accessible by Admin
     */
    private void reportsMenu() {
        this.menu.clear();
        this.menu.add(MenuOptions.TOTAL_SELLS_BY_COMPANY);
        this.menu.add(MenuOptions.TOTAL_SELLS_BY_CATEGORY);
        this.menu.add(MenuOptions.GET_ALL_COUPONS_BY_CATEGORY);
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
            case COMPANIES -> {
                companiesMenu();
            }
            case CUSTOMERS -> {
                customersMenu();
            }
            case CATEGORIES -> {
                categoriesMenu();
            }
            case REPORTS -> {
                reportsMenu();
            }
            case BACK -> {
                initialMenu();
            }
            case LOG_OUT -> {
                LoginManager.getInstance();
            }
            case EXIT -> {
                exit();
            }
            case ADD_COMPANY -> {
                Company company = new Company();
                if (check(!this.companiesDBDAO.isCompanyExists(company.getEmail(), company.getName()), CouponExceptions.COMPANY_ALREADY_EXISTS))
                    this.companiesDBDAO.addCompany(company);
                companiesMenu();
            }
            case UPDATE_COMPANY -> {
                int companyID = ID(BeanType.COMPANY);
                if (check(companyID, this.companiesDBDAO.isCompanyExists(companyID), CouponExceptions.COMPANY_DOESNT_EXIST))
                    this.companiesDBDAO.updateCompany(new Company(this.companiesDBDAO.getOneCompany(companyID)));
                companiesMenu();
            }
            case DELETE_COMPANY -> {
                int companyID = ID(BeanType.COMPANY);
                if (check(companyID, this.companiesDBDAO.isCompanyExists(companyID), CouponExceptions.COMPANY_DOESNT_EXIST))
                    this.companiesDBDAO.deleteCompany(companyID);
                companiesMenu();
            }
            case GET_ALL_COMPANIES -> {
                getAllCompanies().stream().forEach(p -> System.out.println(p));
                companiesMenu();
            }
            case GET_ONE_COMPANY -> {
                int companyID = ID(BeanType.COMPANY);
                if (check(companyID, this.companiesDBDAO.isCompanyExists(companyID), CouponExceptions.COMPANY_DOESNT_EXIST)) {
                    System.out.println(this.companiesDBDAO.getOneCompany(companyID));
                    if (this.couponDBDAO.doesCompanyHaveCoupons(companyID))
                        this.couponDBDAO.getAllCoupons(companyID).stream().forEach(p -> System.out.println(p));
                }
                companiesMenu();
            }
            case ADD_CUSTOMER -> {
                Customer customer = new Customer();
                if (check(!this.customersDBDAO.isCustomerExists(customer.getEmail(), customer.getPassword()), CouponExceptions.CUSTOMER_ALREADY_EXISTS))
                    this.customersDBDAO.addCustomer(customer);
                customersMenu();
            }
            case UPDATE_CUSTOMER -> {
                int customerID = ID(BeanType.CUSTOMER);
                if (check(customerID, this.customersDBDAO.isCustomerExist(customerID), CouponExceptions.CUSTOMER_DOESNT_EXIST))
                    this.customersDBDAO.updateCustomer(new Customer(this.customersDBDAO.getOneCustomer(customerID)));
                customersMenu();
            }
            case DELETE_CUSTOMER -> {
                int customerID = ID(BeanType.CUSTOMER);
                if (check(customerID, this.customersDBDAO.isCustomerExist(customerID), CouponExceptions.CUSTOMER_DOESNT_EXIST))
                    this.customersDBDAO.deleteCustomer(customerID);
                customersMenu();
            }
            case GET_ALL_CUSTOMERS -> {
                this.customersDBDAO.getAllCustomers().stream().forEach(p -> System.out.println(p));
                customersMenu();
            }
            case GET_ONE_CUSTOMER -> {
                int customerID = ID(BeanType.CUSTOMER);
                if (check(customerID, this.customersDBDAO.isCustomerExist(customerID), CouponExceptions.CUSTOMER_DOESNT_EXIST)) {
                    System.out.println(this.customersDBDAO.getOneCustomer(customerID));
                    if (this.couponDBDAO.doesCustomerHaveCoupons(customerID))
                        this.couponDBDAO.getAllCouponsByCustomer(customerID).stream().forEach(p -> System.out.println(p));
                }
                customersMenu();
            }
            case ADD_CATEGORY -> {
                Category category = new Category(new StringBuffer(stringInput("new category name").toUpperCase()));
                if (check(!this.couponDBDAO.checkCouponCategoryExistence(category.getName()), CouponExceptions.CATEGORY_ALREADY_EXISTS))
                    this.couponDBDAO.addCategory(category.getName());
                categoriesMenu();
            }
            case GET_ALL_CATEGORIES -> {
                this.couponDBDAO.getAllCategories().stream().forEach(p -> System.out.println(p));
                categoriesMenu();
            }
            case TOTAL_SELLS_BY_COMPANY -> {
                this.reportsDBDAO.totalSellsByCompany();
                reportsMenu();
            }
            case TOTAL_SELLS_BY_CATEGORY -> {
                this.reportsDBDAO.totalSellsByCategory();
                reportsMenu();
            }
            case GET_ALL_COUPONS_BY_CATEGORY -> {
                this.reportsDBDAO.getAllCouponsByCategory(new Category());
                reportsMenu();
            }
            default -> {
                initialMenu();
            }
        }
    }


// TEST ================================================================================================================
// TEST ================================================================================================================
// TEST ================================================================================================================

    /**
     * overloaded constructor for test purposes only
     */
    public AdminFacade(boolean isTest) {
    }

    /**
     * @param company company object
     * creates a new company in the company table of the DB
     * >>>for Test purposes only<<<
     */
    public void addCompany(Company company) {
        this.companiesDBDAO.addCompany(company);
    }

    /**
     * @param company company object
     * updates an existing company in the company table of the DB
     * >>>for Test purposes only<<<
     */
    public void updateCompany(Company company) {
        this.companiesDBDAO.updateCompany(company);
    }

    /**
     * @param companyID company id
     * deletes an existing company in the company table of the DB
     * >>>for Test purposes only<<<
     */
    public void deleteCompany(int companyID) {
        this.companiesDBDAO.deleteCompany(companyID);
    }

    /**
     * @return all existing companies
     * >>>for Test purposes only<<<
     */
    public List<Company> getAllCompanies() {
        return this.companiesDBDAO.getAllCompanies();
    }

    /**
     * @param companyID company id
     * @return a company specified by id
     * >>>for Test purposes only<<<
     */
    public Company getOneCompany(int companyID) {
        return this.companiesDBDAO.getOneCompany(companyID);
    }

    /**
     * @param customer customer object
     * adds a new customer
     * >>>for Test purposes only<<<
     */
    public void addCustomer(Customer customer) {
        this.customersDBDAO.addCustomer(customer);
    }

    /**
     * @param customer customer object
     * updates an existing customer
     * >>>for Test purposes only<<<
     */
    public void updateCustomer(Customer customer) {
        this.customersDBDAO.updateCustomer(customer);
    }

    /**
     * @param customerID customer id
     * deletes an existing customer
     * >>>for Test purposes only<<<
     */
    public void deleteCustomer(int customerID) {
        this.customersDBDAO.deleteCustomer(customerID);
    }

    /**
     * @return all existing customers
     * >>>for Test purposes only<<<
     */
    public List<Customer> getAllCustomers() {
        return this.customersDBDAO.getAllCustomers();
    }

    /**
     * @param customerID customer id
     * @return a customer specified by id
     * >>>for Test purposes only<<<
     */
    public Customer getOneCustomer(int customerID) {
        return this.customersDBDAO.getOneCustomer(customerID);
    }
}