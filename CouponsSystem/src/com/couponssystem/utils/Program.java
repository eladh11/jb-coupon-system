package com.couponssystem.utils;

import java.sql.SQLException;

import com.couponssystem.exception.AlreadyExitException;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;
import com.couponssystem.exception.PurchaseCouponException;
import com.couponssystem.exception.TableException;

public class Program {

	public static void main(String[] args) throws ClassNotFoundException, CouldNotAddDataToTableException, SQLException,
			TableException, IncorrectDetailsException, AlreadyExitException, PurchaseCouponException {
		System.out.println("START - the Test:");
		Test.testAll();
		System.out.println("Finish !");
	}
}