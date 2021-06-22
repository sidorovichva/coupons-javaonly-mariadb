package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import DB.*;
import Exceptions.Exceptions.CouponExceptions;
import Exceptions.Notifications.Notifications;

import java.util.List;

/**
 *Data Access Object related to the Company object
 */
public class CompaniesDBDAO extends ClientDBDAO implements CompaniesDAO {
    /**
     * sql command: return a company by e-mail and name
     */
    public static final StringBuffer IF_COMPANY_EXISTS = new CommandBuilder()
            .select().from(SQL.COMPANIES)
            .where(SQL.company_EMAIL).or(SQL.company_NAME).build();

    /**
     * sql command: return a company by id
     */
    public static final StringBuffer IF_COMPANY_EXISTS_BY_ID = new CommandBuilder()
            .select().from(SQL.COMPANIES)
            .where(SQL.company_ID).build();

    /**
     * sql command: add company with specified name, e-mail, and password
     */
    public static final StringBuffer ADD_COMPANY = new CommandBuilder()
            .insert(SQL.COMPANIES, new SQL[]{SQL.company_NAME, SQL.company_EMAIL, SQL.company_PASSWORD}).build();

    /**
     * sql command: update company' e-mail and password with specified id
     */
    public static final StringBuffer UPDATE_COMPANY = new CommandBuilder()
            .update(SQL.COMPANIES, new SQL[]{SQL.company_EMAIL, SQL.company_PASSWORD})
            .where(SQL.company_ID).build();

    /**
     * sql command: delete company with specified id
     */
    public static final StringBuffer DELETE_COMPANY = new CommandBuilder()
            .delete(SQL.COMPANIES)
            .where(SQL.company_ID).build();

    /**
     * sql command: return company with specified id
     */
    public static final StringBuffer GET_ONE_COMPANY_BY_ID = new CommandBuilder()
            .select().from(SQL.COMPANIES)
            .where(SQL.company_ID).build();

    /**
     * sql command: return all existing companies
     */
    public static final StringBuffer GET_ALL_COMPANIES = new CommandBuilder()
            .select().from(SQL.COMPANIES).build();

    /**
     * @param email input from the user while logging-in
     * @param name input from the user while logging-in
     * @return true if company with such e-mail and name exists
     */
    @Override
    public boolean isCompanyExists(String email, String name) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{email, name}), IF_COMPANY_EXISTS), mockBoolean);
    }

    /**
     * @param companyID input from the user
     * @return true if company with such id exists
     */
    public boolean isCompanyExists(int companyID) {
        return dataExtraction(DBUtils.runQuery(getMap(companyID), IF_COMPANY_EXISTS_BY_ID), mockBoolean);
    }

    /**
     * @param company input from the user
     * adds new Company to the corresponding table in DB
     */
    @Override
    public void addCompany(Company company) {
        check((DBUtils.runUpdate(getMap(new Object[]{company.getName(), company.getEmail(), company.getPassword()}),
                ADD_COMPANY)), Notifications.ADD_COMPANY_SUCCESS, Notifications.ADD_COMPANY_FAIL);
    }

    /**
     * @param company input from the user
     * replaces Company e-mail and password according to Company id in the corresponding table in DB
     */
    @Override
    public void updateCompany(Company company) {
        check((DBUtils.runUpdate(getMap(new Object[]{company.getEmail(), company.getPassword(), company.getId()}),
                UPDATE_COMPANY)), Notifications.UPDATE_COMPANY_SUCCESS, Notifications.UPDATE_COMPANY_FAIL);
    }

    /**
     * @param companyID input from the user
     * deletes Company with specific id in the corresponding table in DB
     */
    @Override
    public void deleteCompany(int companyID) {
        check((DBUtils.runUpdate(getMap(companyID),
                DELETE_COMPANY)), Notifications.DELETE_COMPANY_SUCCESS, Notifications.DELETE_COMPANY_FAIL);
    }

    /**
     * @return a list of all Companies from the corresponding table in DB
     */
    @Override
    public List<Company> getAllCompanies() {
        return dataExtraction(DBUtils.runQuery(GET_ALL_COMPANIES), CouponExceptions.COMPANIES_WERENT_FOUND, mockCompanyList, mockCompany);
    }

    /**
     * @param companyID input from the user
     * @return a Companies by id from the corresponding table in DB
     */
    @Override
    public Company getOneCompany(int companyID) {
        return dataExtraction(DBUtils.runQuery(getMap(companyID),
                GET_ONE_COMPANY_BY_ID), CouponExceptions.COMPANY_DOESNT_EXIST, mockCompany);
    }

    /**
     * @param email input from the user while logging-in
     * @param password input from the user while logging-in
     * @return a Companies by id from the corresponding table in DB
     */
    public Company getOneCompany(String email, String password) {
        return dataExtraction(DBUtils.runQuery(getMap(new Object[]{email, password}),
                IF_COMPANY_EXISTS), CouponExceptions.COMPANY_DOESNT_EXIST, mockCompany);
    }
}
