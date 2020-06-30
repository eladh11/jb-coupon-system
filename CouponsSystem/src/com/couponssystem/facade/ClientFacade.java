package com.couponssystem.facade;

import com.couponssystem.dao.CompaniesDAO;
import com.couponssystem.dao.CouponsDAO;
import com.couponssystem.dao.CustomersDAO;
import com.couponssystem.dbdao.CompaniesDBDAO;
import com.couponssystem.dbdao.CouponsDBDAO;
import com.couponssystem.dbdao.CustomersDBDAO;
import com.couponssystem.exception.IncorrectDetailsException;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO = null;
	protected CustomersDAO customersDAO = null;
	protected CouponsDAO couponsDAO = null;

	public ClientFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}

	public abstract boolean login(String email, String password) throws IncorrectDetailsException;
}
