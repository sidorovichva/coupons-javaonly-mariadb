package DAO;

import Beans.Company;

import java.util.List;

/**
 *Data Access Object interface with method signatures related to the Company object
 */
public interface CompaniesDAO {
    boolean isCompanyExists(String email, String name);
    void addCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(int companyID);
    List<Company> getAllCompanies();
    Company getOneCompany(int companyID);
}
