package com.couponssystem.security;

import com.couponssystem.exception.IncorrectDetailsException;
import com.couponssystem.facade.AdminFacade;
import com.couponssystem.facade.ClientFacade;
import com.couponssystem.facade.CompanyFacade;
import com.couponssystem.facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance = null;
	private static ClientFacade clientFacade;

	private LoginManager() {
	}

	public static LoginManager getInstance() {
		if (instance == null) {
			synchronized (LoginManager.class) {
				if (instance == null) {
					instance = new LoginManager();
				}
			}
		}
		return instance;
	}

	public static ClientFacade login(String email, String password, ClientType clientType)
			throws IncorrectDetailsException {
		switch (clientType) {
		case Administrator:
			clientFacade = new AdminFacade();
			if (clientFacade.login(email, password)) {
				return clientFacade;
			} else {
				return null;
			}
		case Company:
			clientFacade = new CompanyFacade();
			if (clientFacade.login(email, password)) {
				int companyID = ((CompanyFacade) clientFacade).getCompanyID(email, password);
				((CompanyFacade) clientFacade).setCompanyID(companyID);
				return clientFacade;
			} else {
				return null;
			}
		case Customer:
			clientFacade = new CustomerFacade();
			if (clientFacade.login(email, password)) {
				int customerID = ((CustomerFacade) clientFacade).getCustomerID(email, password);
				((CustomerFacade) clientFacade).setCustomerID(customerID);
				return clientFacade;
			}

		default:
			clientFacade = null;
			break;
		}
		return null;
	}
}
