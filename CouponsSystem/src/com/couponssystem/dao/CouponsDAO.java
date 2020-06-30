package com.couponssystem.dao;

import java.util.List;

import com.couponssystem.beans.Category;
import com.couponssystem.beans.Coupon;
import com.couponssystem.beans.CustomerVsCoupon;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;

public interface CouponsDAO {

	void addCoupon(Coupon coupon) throws CouldNotAddDataToTableException;

	void updateCoupon(int couponID, Coupon coupon) throws IncorrectDetailsException;

	void deleteCoupon(int couponID) throws IncorrectDetailsException;

	List<Coupon> getAllCoupons() throws IncorrectDetailsException;

	Coupon getOneCoupon(int couponID);

	void addCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchaseForCompany(int couponID);

	void deleteCouponPurchaseForCustomer(int customerID);

	List<Coupon> getAllCouponsByCompanyID(int companyID) throws IncorrectDetailsException;

	List<Coupon> getAllCouponsByCategory(Category category) throws IncorrectDetailsException;

	List<CustomerVsCoupon> getAllCustomerVsCoupons() throws IncorrectDetailsException;

}
