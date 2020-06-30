package com.couponssystem.facade;

import java.util.ArrayList;
import java.util.List;

import com.couponssystem.beans.Category;
import com.couponssystem.beans.Company;
import com.couponssystem.beans.Coupon;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;

public class CompanyFacade extends ClientFacade {

	private int companyID;

	public CompanyFacade(int companyID) {
		super();
		this.companyID = companyID;
	}

	public CompanyFacade() {
		super();
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	@Override
	public String toString() {
		return "CompanyFacade [companyID=" + companyID + ", toString()=" + super.toString() + "]";
	}

	@Override
	public boolean login(String email, String password) throws IncorrectDetailsException {
		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getEmail().equals(email) && comp.getPassword().equals(password)) {
				System.out.println("Company login Successfully!");
				return true;
			}
		}
		System.out.println("Email: " + email + " Password: " + password + " are not found.");
		return false;
	}

	public void addCoupon(Coupon coupon) throws IncorrectDetailsException, CouldNotAddDataToTableException {
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon coup : coupons) {
			if (coup.getId() == coupon.getId()) {
				if (coup.getTitle().equals(coupon.getTitle())) {
					throw new IncorrectDetailsException("cannot be the same title for the same company.");
				}
			}
		}
		couponsDAO.addCoupon(coupon);
	}

	public void updateCoupon(int couponID, Coupon coupon) throws IncorrectDetailsException {
		Coupon idx = couponsDAO.getOneCoupon(couponID);
		if (coupon.getCategory() != null) {
			idx.setCategory(coupon.getCategory());
		}
		if (coupon.getTitle() != null) {
			idx.setTitle(coupon.getTitle());
		}
		if (coupon.getDescription() != null) {
			idx.setDescription(coupon.getDescription());
		}
		if (coupon.getStartDate() != null) {
			idx.setStartDate(coupon.getStartDate());
		}
		if (coupon.getEndDate() != null) {
			idx.setEndDate(coupon.getEndDate());
		}
		if (coupon.getAmount() > 0) {
			idx.setAmount(coupon.getAmount());
		}
		if (coupon.getPrice() > 0) {
			idx.setPrice(coupon.getPrice());
		}
		if (coupon.getImage() != null) {
			idx.setImage(coupon.getImage());
		}
		couponsDAO.updateCoupon(couponID, idx);

	}

	public void deleteCoupon(int couponID) throws IncorrectDetailsException {

		// delete the coupon from `coupons_VS_customers` Table.
		couponsDAO.deleteCouponPurchaseForCompany(couponID);
		// delete the coupon from coupons table.
		couponsDAO.deleteCoupon(couponID);

	}

	public List<Coupon> getCompanyCoupons() throws IncorrectDetailsException {

		List<Coupon> idx = new ArrayList<Coupon>();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		if (coupons != null) {
			for (Coupon coupon : coupons) {
				if (coupon.getCompanyID() == this.companyID) {
					idx.add(coupon);
				}
			}
		}
		return idx;

	}

	public List<Coupon> getCompanyCoupons(Category category) throws IncorrectDetailsException {
		return couponsDAO.getAllCouponsByCategory(category);
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) throws IncorrectDetailsException {
		List<Coupon> idx = new ArrayList<Coupon>();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		if (coupons != null) {
			for (Coupon coupon : coupons) {
				if (coupon.getCompanyID() == companyID && coupon.getPrice() <= maxPrice) {
					idx.add(coupon);
				}
			}
		}
		return idx;

	}

	public Company getCompanyDetails() throws IncorrectDetailsException {
		Company company = companiesDAO.getOneCompany(this.companyID);
		List<Coupon> coupons = couponsDAO.getAllCouponsByCompanyID(this.companyID);
		company.setCoupons(coupons);
		return company;

	}

	public int getCompanyID(String email, String password) {
		Company company = companiesDAO.getOneCompany(email, password);
		return company.getId();
	}
}
