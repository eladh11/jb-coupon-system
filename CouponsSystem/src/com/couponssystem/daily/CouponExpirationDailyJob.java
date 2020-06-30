package com.couponssystem.daily;

import java.util.Date;
import java.util.List;

import com.couponssystem.beans.Coupon;
import com.couponssystem.dao.CouponsDAO;
import com.couponssystem.dbdao.CouponsDBDAO;

public class CouponExpirationDailyJob implements Runnable {

	private static boolean quit = false;
	private static CouponsDAO couponsDAO = new CouponsDBDAO();

	public CouponExpirationDailyJob() {

	}

	// implements the Runnable Method
	@Override
	public void run() {
		while (!quit) {
			List<Coupon> coupons = null;
			try {
				coupons = couponsDAO.getAllCoupons();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			for (Coupon coupon : coupons) {
				if (coupon.getEndDate().before(new Date())) {
					// the time to delete the is come
					couponsDAO.deleteCouponPurchaseForCompany(coupon.getId());
					try {
						couponsDAO.deleteCoupon(coupon.getId());
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			try {
				Thread.sleep(24 * 60 * 60 * 1000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void stopTheDailyThread() {
		quit = true;
	}

	public static void startTheDailyThread() {
		Thread thread = new Thread(new CouponExpirationDailyJob());
		thread.start();
	}

}
