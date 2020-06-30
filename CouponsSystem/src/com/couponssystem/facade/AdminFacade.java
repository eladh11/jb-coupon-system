package com.couponssystem.facade;

import java.util.List;

import com.couponssystem.beans.Company;
import com.couponssystem.beans.Coupon;
import com.couponssystem.beans.Customer;
import com.couponssystem.exception.AlreadyExitException;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;

public class AdminFacade extends ClientFacade {
	public AdminFacade() {
		super();
	}

	@Override
	public String toString() {
		return "AdminFacade [toString()=" + super.toString() + "]";
	}

	@Override
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			System.out.println("admin login Successfully!");
			return true;
		}
		System.out.println("the details are incorrect...");
		return false;
	}

	public void addCompany(Company company)
			throws AlreadyExitException, IncorrectDetailsException, CouldNotAddDataToTableException {
		List<Company> companies = companiesDAO.getAllCompanies();

		for (Company comp : companies) {
			if (comp.getName().equals(company.getName())) {
				throw new AlreadyExitException("Company: " + company.getName() + " Already Exit.");
			}
			if (comp.getEmail().equalsIgnoreCase(company.getEmail())) {
				throw new AlreadyExitException("Email: " + company.getEmail() + " Already Exit.");
			}
		}
		companiesDAO.addCompany(company);

	}

	public void updateCompany(int companyID, Company company) throws IncorrectDetailsException {
		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getId() == company.getId()) {
				throw new IncorrectDetailsException("ID - cannot change the company ID");
			}
			if (comp.getName() == company.getName()) {
				throw new IncorrectDetailsException("NAME - cannot change the company NAME");
			}
		}
		companiesDAO.updateCompany(companyID, company);
	}

	public void deleteCompany(int companyID) throws IncorrectDetailsException {

		List<Coupon> originalCoupon = couponsDAO.getAllCoupons();
		for (Coupon coupon : originalCoupon) {
			if (companyID == coupon.getCompanyID()) {
				// delete the coupon from `coupons_VS_customers` Table.
				couponsDAO.deleteCouponPurchaseForCompany(coupon.getId());
				// delete the coupons of the selected company.
				couponsDAO.deleteCoupon(coupon.getId());
			}
		}
		companiesDAO.deletaCompany(companyID);
		System.out.println("Delete the company Successfully.");
	}

	public List<Company> getAllCompanies() throws IncorrectDetailsException {
		return companiesDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyID) {
		return companiesDAO.getOneCompany(companyID);
	}

	public void addCustomer(Customer customer) throws IncorrectDetailsException, CouldNotAddDataToTableException {
		List<Customer> customers = customersDAO.getAllCustomers();
		for (Customer custom : customers) {
			if (custom.getEmail().equalsIgnoreCase(customer.getEmail())) {
				throw new IncorrectDetailsException("Email:" + customer.getEmail() + " Already Exit.");
			}
		}
		customersDAO.addCustomer(customer);

	}

	public void updateCustomer(int customerID, Customer customer) throws IncorrectDetailsException {
		List<Customer> customers = customersDAO.getAllCustomers();
		for (Customer custom : customers) {
			if (custom.getId() == customer.getId()) {
				throw new IncorrectDetailsException("ID - cannot change the ID");
			}
		}
		customersDAO.updateCustomer(customerID, customer);

	}

	public void deleteCustomer(int customerID) throws IncorrectDetailsException {

		try {
			// delete the customer coupons.
			couponsDAO.deleteCouponPurchaseForCustomer(customerID);
			customersDAO.deleteCustomer(customerID);
		} catch (Exception e) {
			throw new IncorrectDetailsException("the ID not found...");
		}
		System.out.println("Delete the customer Successfully.");
	}

	public List<Customer> getAllCustomers() throws IncorrectDetailsException {
		return customersDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) {
		return customersDAO.getOneCustomer(customerID);
	}

}
