package com.couponssystem.dao;

import java.util.List;

import com.couponssystem.beans.Customer;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;

public interface CustomersDAO {

	boolean isCutomerExists(String email, String password) throws IncorrectDetailsException;

	void addCustomer(Customer customer) throws CouldNotAddDataToTableException;

	void updateCustomer(int customerID, Customer customer) throws IncorrectDetailsException;

	void deleteCustomer(int customerID) throws IncorrectDetailsException;

	List<Customer> getAllCustomers() throws IncorrectDetailsException;

	Customer getOneCustomer(int customerID);

	Customer getOneCustomer(String email, String password);
}
