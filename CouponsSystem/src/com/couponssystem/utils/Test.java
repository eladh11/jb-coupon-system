package com.couponssystem.utils;

import java.sql.SQLException;

import com.couponssystem.daily.CouponExpirationDailyJob;
import com.couponssystem.exception.AlreadyExitException;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;
import com.couponssystem.exception.PurchaseCouponException;
import com.couponssystem.exception.TableException;
import com.johnbryce.db.ConnectionPool;

public class Test {

	public static void testAll() throws CouldNotAddDataToTableException, SQLException, TableException,
			ClassNotFoundException, IncorrectDetailsException, AlreadyExitException, PurchaseCouponException {

		// Build all the Tables information
		Class.forName("com.mysql.cj.jdbc.Driver");
		GenerateData.getAllData();

		// Start the Daily Thread
		CouponExpirationDailyJob.startTheDailyThread();

		// open the Connection
		ConnectionPool.getInstance();

		// Login as Admin
		GenerateData.loginAsAdmin();

		// Login as Company
		GenerateData.loginAsCompany();

		// Login as Customer
		GenerateData.loginAsCustomer();

		// Final Step - Close All Connection + Stop the daily Thread
		// closeAll();
	}

	public static void closeAll() throws IncorrectDetailsException {

		// stop the daily Thread
		CouponExpirationDailyJob.stopTheDailyThread();

		// close all Connection
		try {
			ConnectionPool.getInstance().closeAllConnection();
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot close all Connection...");
		}

	}

}
