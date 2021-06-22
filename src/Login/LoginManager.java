package Login;

import Auxiliaries.InputAble;
import Beans.Company;
import Beans.Customer;
import DAO.CompaniesDAO;
import DAO.CustomersDAO;
import DBDAO.CompaniesDBDAO;
import DBDAO.CustomersDBDAO;
import Exceptions.Exceptions.CheckExceptionsAble;
import Exceptions.Exceptions.CouponExceptions;
import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import RandomDataForTestRuns.MyArrayInt;

import java.util.List;

/**
 *Contain all log-in related methods and at the end creates a new instance of facade depending on user input
 */
public class LoginManager implements InputAble, CheckExceptionsAble {
    /**
     *hard-coded admin login and e-mail
     */
    private final StringBuffer adminMail = new StringBuffer("admin@admin.com");
    private final StringBuffer adminPass = new StringBuffer("admin");
    /**
     *a number of times user can fail login and repeat the attempt before the system will be shut down
     */
    private final int maxAcceptableFails = 2;

    /**
     *current number of times user can fail login and repeat the attempt before the system will be shut down
     */
    private int numberOfFailedLogins;

    /**
     *singleton instance
     */
    private static LoginManager instance = null;

    /**
     *singleton constructor
     */
    private LoginManager() {
        this.numberOfFailedLogins = 0;
        System.out.println("\t\t\t<<<Welcome to Coupon Managing System>>>\n");
        loginStart();
    }

    /**
     *returns singleton instance
     */
    public static LoginManager getInstance() {
        if (instance == null)
            synchronized (LoginManager.class) {
                if (instance == null)
                    instance = new LoginManager();
            }
        return instance;
    }

    /**
     * @param message message for user provided by loginStart method
     * @return a type of the client that was chosen by the user.
     */
    private ClientType userIntInput(String message) {
        int answer = intInput(message + "(enter the number)");
        if (check((answer >= 1 && answer <= ClientType.values().length), CouponExceptions.WRONG_CHOICE))
            return (ClientType.values()[answer - 1]);
        return userIntInput(message);
    }

    /**
     * defines a type of the client, an e-mail and a password and activates login method
     * also contains commented part for automated log-in for debugging and test purposes
     * to activate this option uncomment a the bottom part and comment the first line
     */
    private void loginStart() {
        login(userIntInput(ClientType.createMenu().toString()), stringInput("your e-mail"), stringInput("your password"));

        /*
        //For automated successful random log -in:

        ClientType type = userIntInput(ClientType.createMenu().toString());
        switch (type) {
            case ADMINISTRATOR: login(type, "a", "a");
            case COMPANY: {
                CompaniesDAO c = new CompaniesDBDAO();
                List<Company> list = c.getAllCompanies();
                int r = MyArrayInt.randomNumber(0, list.size() - 1);
                login(type, list.get(r).getEmail(), list.get(r).getPassword());
            }
            case CUSTOMER: {
                CustomersDAO c = new CustomersDBDAO();
                List<Customer> list = c.getAllCustomers();
                int r = MyArrayInt.randomNumber(0, list.size() - 1);
                login(type, list.get(r).getEmail(), list.get(r).getPassword());
            }
        }*/
    }

    /**
     * @param clientType type of the client, enum
     * @param email entered by client email
     * @param password entered by client password,
     * creates a new instance of facade depending on user input
     */
    private void login(ClientType clientType, String email, String password) {
        switch (clientType) {
            case ADMINISTRATOR -> {
                if (email.equals(adminMail.toString()) && password.equals(adminPass.toString())) {
                    new AdminFacade();
                } else {
                    System.out.println("log-in fail");
                    wrongLogin();
                }
            }
            case COMPANY -> {
                CompaniesDBDAO d = new CompaniesDBDAO();
                Company company = d.getOneCompany(email, password);
                if (company != null) {
                    System.out.println(company);
                    new CompanyFacade(company.getId());
                }
                else {
                    wrongLogin();
                }
            }
            case CUSTOMER -> {
                CustomersDBDAO d = new CustomersDBDAO();
                Customer customer = d.getOneCustomerByMailAndPass(email, password);
                if (customer != null) {
                    System.out.println(customer);
                    new CustomerFacade(customer.getId());
                }
                else {
                    wrongLogin();
                }
            }
            default -> wrongLogin();
        }
    }

    /**
     * in case of failed login attempt checks if the user have another attempt and if yes, starts login
     * process again with corresponding message. If number of failed attempts are above the limit, terminates the system
     */
    private void wrongLogin() {
        this.numberOfFailedLogins += 1;
        if (this.numberOfFailedLogins > maxAcceptableFails) {
            System.out.print("Too many failed tries to log in. Exiting...");
            System.exit(0);
        } else {
            loginStart();
        }
    }

// TEST ================================================================================================================
// TEST ================================================================================================================
// TEST ================================================================================================================

    /**
     * regular constructor for test purposes only
     */
    public LoginManager(boolean isTest) {
    }

    /**
     * @param isTest overloading parameter, has no use inside the method
     * @param clientType type of the client, enum
     * @param email entered by client email
     * @param password entered by client password,
     * @return return the facade instance for test purposes only
     * creates a new instance of facade the clientType
     */
    public ClientFacade login(boolean isTest, ClientType clientType, String email, String password) {
        switch (clientType) {
            case ADMINISTRATOR -> {
                if (email.equals(adminMail.toString()) && password.equals(adminPass.toString())) {
                    return new AdminFacade(true);
                } else {
                    System.out.println("log-in fail");
                    wrongLogin();
                }
            }
            case COMPANY -> {
                CompaniesDBDAO d = new CompaniesDBDAO();
                Company company = d.getOneCompany(email, password);
                if (company != null) {
                    System.out.println(company);
                    return new CompanyFacade(company.getId(), true);
                }
                else {
                    wrongLogin();
                }
            }
            case CUSTOMER -> {
                CustomersDBDAO d = new CustomersDBDAO();
                Customer customer = d.getOneCustomerByMailAndPass(email, password);
                if (customer != null) {
                    System.out.println(customer);
                    return new CustomerFacade(customer.getId(), true);
                }
                else {
                    wrongLogin();
                }
            }
            default -> wrongLogin();
        }
        return null;
    }
}
