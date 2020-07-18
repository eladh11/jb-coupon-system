package com.couponssystem.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.couponssystem.beans.Category;
import com.couponssystem.beans.Coupon;
import com.couponssystem.beans.Customer;
import com.couponssystem.beans.CustomerVsCoupon;
import com.couponssystem.exception.IncorrectDetailsException;
import com.couponssystem.exception.PurchaseCouponException;

public class CustomerFacade extends ClientFacade {

	private int customerID;

	public CustomerFacade(int customerID) {
		super();
		this.customerID = customerID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public CustomerFacade() {
		super();
	}

	@Override
	public String toString() {
		return "CustomerFacade [customerID=" + customerID + ", toString()=" + super.toString() + "]";
	}

	@Override
	public boolean login(String email, String password) throws IncorrectDetailsException {
		return this.customersDAO.isCutomerExists(email, password);
	}

	public void purchaseCoupon(Coupon coupon) throws PurchaseCouponException, IncorrectDetailsException {
		// cannot buy the same coupon more than once
		List<CustomerVsCoupon> customerVsCoupons = couponsDAO.getAllCustomerVsCoupons();
		for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
			if (customerVsCoupon.getCouponID() == coupon.getId()
					&& customerVsCoupon.getCustomerID() == this.customerID) {
				throw new PurchaseCouponException("cannot buy the same coupon more than once...");
			}
		}
		// cannot buy coupon that is amount=0
		Coupon c = couponsDAO.getOneCoupon(coupon.getId());
		if (c.getAmount() == 0) {
			throw new PurchaseCouponException("cannot buy the coupon: amount = 0");
		}
		// cannot buy coupon if is date expired
		if (c.getEndDate().before(new Date())) {
			throw new PurchaseCouponException("the date of the coupon expired...");

		}
		// amount - 1
		System.out.println("coupon: " + c.getTitle() + " is available.");
		coupon.setAmount(coupon.getAmount() - 1);
		couponsDAO.updateCoupon(coupon.getId(), coupon);
		couponsDAO.addCouponPurchase(this.customerID, coupon.getId());
		System.out.println("Enjoy your coupon :->");

	}

	public List<Coupon> getCustomerCoupons() throws IncorrectDetailsException {
		List<Coupon> coupons = new ArrayList<Coupon>();
		List<CustomerVsCoupon> customerVsCoupons = couponsDAO.getAllCustomerVsCoupons();
		if (coupons != null) {
			for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
				if (customerVsCoupon.getCustomerID() == this.customerID) {
					Coupon idx = couponsDAO.getOneCoupon(customerVsCoupon.getCouponID());
					coupons.add(idx);
				}
			}
		}
		return coupons;
	}

	public List<Coupon> getCustomerCoupons(Category category) throws IncorrectDetailsException {
		List<Coupon> coupons = new ArrayList<Coupon>();
		List<CustomerVsCoupon> customerVsCoupons = couponsDAO.getAllCustomerVsCoupons();
		for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
			if (customerVsCoupon.getCustomerID() == this.customerID) {
				Coupon idx = couponsDAO.getOneCoupon(customerVsCoupon.getCouponID());
				if (idx.getCategory().toString().equals(category.toString())) {
					coupons.add(idx);
				}
			}
		}
		return coupons;

	}

	public List<Coupon> getCustomerCoupons(double maxPrice) throws IncorrectDetailsException {

		List<Coupon> coupons = new ArrayList<Coupon>();
		List<CustomerVsCoupon> customerVsCoupons = couponsDAO.getAllCustomerVsCoupons();
		for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
			if (customerVsCoupon.getCustomerID() == this.customerID) {
				Coupon idx = couponsDAO.getOneCoupon(customerVsCoupon.getCouponID());
				if (idx.getPrice() <= maxPrice) {
					coupons.add(idx);
				} else {
					System.out.println("the price to high...");
				}
			}
		}
		return coupons;

	}

	public Customer getCustomerDetails() throws IncorrectDetailsException {
		Customer customer = customersDAO.getOneCustomer(this.customerID);
		List<Coupon> coupons = couponsDAO.getAllCouponsByCompanyID(this.customerID);
		customer.setCoupons(coupons);
		if (coupons == null) {
			System.out.println("the customer does not have any coupon...");
		}
		return customer;
	}

	public int getCustomerID(String email, String password) {
		Customer customer = customersDAO.getOneCustomer(email, password);
		return customer.getId();
	}

}
