package com.couponssystem.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.couponssystem.beans.Company;
import com.couponssystem.dao.CompaniesDAO;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;
import com.johnbryce.db.ConnectionPool;

public class CompaniesDBDAO implements CompaniesDAO {

	public final static String is_Company_Exists = "SELECT * FROM `coupons-system`.`companies` where EMAIL=? AND PASSWORD =?";
	public final static String add_Company = "INSERT INTO `coupons-system`.`companies` (`NAME`,`EMAIL`,`PASSWORD`) VALUES (?,?,?);";
	public final static String update_Company = "UPDATE `coupons-system`.`companies` SET `NAME`=?, `EMAIL`=?, `PASSWORD`=? WHERE (`ID`=?);";
	public final static String delete_Company = "DELETE FROM `coupons-system`.`companies` WHERE (`ID`=?);";
	public final static String get_All_Companies = "SELECT * FROM `coupons-system`.`companies`;";
	public final static String get_One_Company = "SELECT * FROM `coupons-system`.`companies` WHERE (`ID`=?);";
	public final static String getOneCompany_ByEmail_andPassword = "SELECT * FROM `coupons-system`.`companies` WHERE `EMAIL`=? AND `PASSWORD`=?;";

	@Override
	public boolean isCompanyExists(String email, String password) throws IncorrectDetailsException {

		Connection connection = null;
		boolean flag = false;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = is_Company_Exists;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flag = true;
				System.out.println("the Details are Correct.");
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("Incorrect Details...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		if (flag == false) {
			System.out.println("Company Email:" + email + " Password:" + password + " doesn't exist");
		}
		return flag;

	}

	@Override
	public void addCompany(Company company) throws CouldNotAddDataToTableException {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = add_Company;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new CouldNotAddDataToTableException();

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public void updateCompany(int companyID, Company company) throws IncorrectDetailsException {
		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = update_Company;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setInt(4, companyID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot update the table...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void deletaCompany(int companyID) throws IncorrectDetailsException {
		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = delete_Company;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			statement.executeUpdate();

		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot delete the company...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public List<Company> getAllCompanies() throws IncorrectDetailsException {

		List<Company> companies = new ArrayList<Company>();
		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = get_All_Companies;

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Company company = new Company();
				company.setId(resultSet.getInt(1));
				company.setName(resultSet.getString(2));
				company.setEmail(resultSet.getString(3));
				company.setPassword(resultSet.getString(4));
				companies.add(company);
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot return all companies...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = get_One_Company;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Company company = new Company();
				company.setId(resultSet.getInt(1));
				company.setName(resultSet.getString(2));
				company.setEmail(resultSet.getString(3));
				company.setPassword(resultSet.getString(4));
				return company;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return null;
	}

	@Override
	public Company getOneCompany(String email, String password) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getOneCompany_ByEmail_andPassword;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email2 = resultSet.getString(3);
				String password2 = resultSet.getString(4);
				return new Company(id, name, email2, password2);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return null;
	}
}
