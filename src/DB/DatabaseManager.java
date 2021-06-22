package DB;

import Beans.InitialCategories;
import DBDAO.CouponsDBDAO;

/**
 * Contains information about DB structure and creates DB if it doesn't exist
 */
public class DatabaseManager implements Serialization {
    /**
     * Receives url, username and password to DB from the config file
     */
    private static ConnectionSettings settings = (ConnectionSettings) Serialization.read(ConnectionSettings.fileName);

    /**
     * Url, Username and Password to DB from the config file or default from SQL enum
     */
    public static final String url = (settings != null) ? settings.getUrl() : SQL.URL.getText();
    public static final String username = (settings != null) ? settings.getUsername() : SQL.USERNAME.getText();;
    public static final String password = (settings != null) ? settings.getPassword() : SQL.PASSWORD.getText();;

    private static final StringBuffer CREATE_SCHEMA = new StringBuffer("CREATE SCHEMA IF NOT EXISTS " + SQL.DB_NAME.getText());
    private static final StringBuffer DROP_SCHEMA = new StringBuffer("DROP SCHEMA " + SQL.DB_NAME.getText());

    private static final StringBuffer CREATE_TABLE_COMPANIES = new StringBuffer("CREATE TABLE IF NOT EXISTS " + SQL.COMPANIES.getText() + " " +
            "(`ID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            " `NAME` VARCHAR(30) NOT NULL," +
            " `EMAIL` VARCHAR(30) NOT NULL," +
            " `PASSWORD` VARCHAR(30) NOT NULL)");
    private static final StringBuffer CREATE_TABLE_CUSTOMERS = new StringBuffer("CREATE TABLE IF NOT EXISTS " + SQL.CUSTOMERS.getText() + " " +
            "(`ID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            " `FIRST_NAME` VARCHAR(30) NOT NULL," +
            " `LAST_NAME` VARCHAR(30) NOT NULL," +
            " `EMAIL` VARCHAR(30) NOT NULL," +
            " `PASSWORD` VARCHAR(30) NOT NULL)");
    private static final StringBuffer CREATE_TABLE_CATEGORIES = new StringBuffer("CREATE TABLE IF NOT EXISTS " + SQL.CATEGORIES.getText() + " " +
            "(`ID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            " `NAME` VARCHAR(30) NOT NULL)");
    private static final StringBuffer CREATE_TABLE_COUPONS = new StringBuffer("CREATE TABLE IF NOT EXISTS " + SQL.COUPONS.getText() + " " +
            "(`ID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
            " `COMPANY_ID` INT NOT NULL, FOREIGN KEY (COMPANY_ID) REFERENCES " + SQL.COMPANIES.getText() + "(ID) ON DELETE CASCADE," +
            " `CATEGORY_ID` INT NOT NULL, FOREIGN KEY (CATEGORY_ID) REFERENCES " + SQL.CATEGORIES.getText() + "(ID) ON DELETE CASCADE," +
            " `TITLE` VARCHAR(50) NOT NULL," +
            " `DESCRIPTION` VARCHAR(200) NOT NULL," +
            " `START_DATE` DATE NOT NULL," +
            " `END_DATE` DATE NOT NULL," +
            " `AMOUNT` INT NOT NULL," +
            " `PRICE` DOUBLE NOT NULL," +
            " `IMAGE` VARCHAR(200) NOT NULL)");
    private static final StringBuffer CREATE_TABLE_CUSTOMERS_VS_COUPONS = new StringBuffer("CREATE TABLE IF NOT EXISTS " + SQL.CUSTOMERS_VS_COUPONS.getText() + " " +
            "(`CUSTOMER_ID` INT NOT NULL," +
            " `COUPON_ID` INT NOT NULL," +
            " PRIMARY KEY (CUSTOMER_ID, COUPON_ID)," +
            " UNIQUE INDEX (CUSTOMER_ID, COUPON_ID)," +
            " FOREIGN KEY (CUSTOMER_ID) REFERENCES " + SQL.CUSTOMERS.getText() + "(ID) ON DELETE CASCADE," +
            " FOREIGN KEY (COUPON_ID) REFERENCES " + SQL.COUPONS.getText() + "(ID) ON DELETE CASCADE)");

    /**
     * Creates the DB with all the tables at the beginning of each session.
     * If they already exist, nothing will be created additionally
     */
    public static void createAll() {
        DBUtils.runUpdate(CREATE_SCHEMA);
        DBUtils.runUpdate(CREATE_TABLE_COMPANIES);
        DBUtils.runUpdate(CREATE_TABLE_CUSTOMERS);
        DBUtils.runUpdate(CREATE_TABLE_CATEGORIES);
        DBUtils.runUpdate(CREATE_TABLE_COUPONS);
        DBUtils.runUpdate(CREATE_TABLE_CUSTOMERS_VS_COUPONS);

        for (InitialCategories c : InitialCategories.values()) {
            CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
            if (!couponsDBDAO.checkCouponCategoryExistence(c.getText())) couponsDBDAO.addInitialCategory(c.getText());
        }
    }

    /**
     * deletes the existing DB
     */
    public static void dropAll() {
        DBUtils.runUpdate(DROP_SCHEMA);
    }
}
