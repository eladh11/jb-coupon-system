package com.couponssystem.dao;

import java.util.List;

import com.couponssystem.beans.Company;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;

public interface CompaniesDAO {

	boolean isCompanyExists(String email, String password) throws IncorrectDetailsException;

	void addCompany(Company company) throws CouldNotAddDataToTableException;

	void updateCompany(int companyID, Company company) throws IncorrectDetailsException;

	void deletaCompany(int companyID) throws IncorrectDetailsException;

	List<Company> getAllCompanies() throws IncorrectDetailsException;

	Company getOneCompany(int companyID);

	Company getOneCompany(String email, String password);

}
