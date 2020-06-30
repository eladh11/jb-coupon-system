package com.couponssystem.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.couponssystem.beans.Customer;
import com.couponssystem.dao.CustomersDAO;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;
import com.johnbryce.db.ConnectionPool;

public class CustomersDBDAO implements CustomersDAO {

	public final static String is_Cutomer_Exists = "SELECT * FROM `coupons-system`.`customers` where EMAIL=? AND PASSWORD =?;";
	public final static String add_Customer = "INSERT INTO `coupons-system`.`customers` (`FIRST_NAME`,`LAST_NAME`,`EMAIL`,`PASSWORD`) VALUES (?,?,?,?);";
	public final static String update_Customer = "UPDATE `coupons-system`.`customers` SET `FIRST_NAME`=?,`LAST_NAME`=?, `EMAIL`=?, `PASSWORD`=? WHERE (`ID`=?);";
	public final static String delete_Customer = "DELETE FROM `coupons-system`.`customers` WHERE (`ID`=?);";
	public final static String get_All_Customers = "SELECT * FROM `coupons-system`.`customers`;";
	public final static String get_One_Customer_ByID = "SELECT * FROM `coupons-system`.`customers` where (`ID`=?);";
	public final static String get_One_Customer_ByEmail_andPassword = "SELECT * FROM `coupons-system`.`customers` where EMAIL=? and PASSWORD=?;";

	@Override
	public boolean isCutomerExists(String email, String password) throws IncorrectDetailsException {

		Connection connection = null;
		boolean flag = false;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = is_Cutomer_Exists;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flag = true;
				System.out.println("the Details are Correct.");
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("the Details are Incorrect...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		if (flag == false) {
			System.out.println("Customer Email:" + email + " Password:" + password + " doesn't exist");
		}
		return flag;
	}

	@Override
	public void addCustomer(Customer customer) throws CouldNotAddDataToTableException {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = add_Customer;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new CouldNotAddDataToTableException();

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public void updateCustomer(int customerID, Customer customer) throws IncorrectDetailsException {

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = update_Customer;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customerID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot update the customer...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public void deleteCustomer(int customerID) throws IncorrectDetailsException {

		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = delete_Customer;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.executeUpdate();

		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot delete the customer...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public List<Customer> getAllCustomers() throws IncorrectDetailsException {

		List<Customer> customers = new ArrayList<Customer>();
		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = get_All_Customers;

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt(1));
				customer.setFirstName(resultSet.getString(2));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPassword(resultSet.getString(5));
				customers.add(customer);
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot return the customers details...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return customers;
	}

	@Override
	public Customer getOneCustomer(int customerID) {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = get_One_Customer_ByID;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt(1));
				customer.setFirstName(resultSet.getString(2));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPassword(resultSet.getString(5));
				return customer;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return null;

	}

	@Override
	public Customer getOneCustomer(String email, String password) {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = get_One_Customer_ByEmail_andPassword;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email2 = resultSet.getString(4);
				String password2 = resultSet.getString(5);
				return new Customer(id, firstName, lastName, email2, password2);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return null;
	}

}
