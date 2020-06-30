package com.couponssystem.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.couponssystem.beans.Category;
import com.couponssystem.beans.Coupon;
import com.couponssystem.beans.CustomerVsCoupon;
import com.couponssystem.dao.CouponsDAO;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;
import com.johnbryce.db.ConnectionPool;

public class CouponsDBDAO implements CouponsDAO {

	public static final String add_Coupon = "INSERT INTO `coupons-system`.`coupons` (`COMPANY_ID`, `CATEDORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) VALUES (?,?,?,?,?,?,?,?,?);";
	public static final String update_Coupon = "UPDATE `coupons-system`.`coupons` SET `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?,"
			+ " `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ?, `IMAGE` = ? WHERE (`ID` = ?);";
	public static final String delete_Coupon = "DELETE FROM `coupons-system`.`coupons` WHERE (`ID`=?);";
	public static final String get_All_Coupons = "SELECT * FROM `coupons-system`.`coupons`;";
	public static final String get_One_Coupon = "SELECT * FROM `coupons-system`.`coupons` WHERE (`ID`=?);";
	public static final String add_Coupon_Purchase = "INSERT INTO `coupons-system`.`customers_vs_coupons` (`CUSTOMER_ID`, `COUPON_ID`) VALUES (?,?);";
	public static final String delete_Coupon_Purchase = "DELETE FROM `coupons-system`.`customers_vs_coupons` WHERE (`CUSTOMER_ID` = ?) AND (`COUPON_ID` = ?);";
	public static final String delete_Coupon_Purchase_ForCompany = "DELETE FROM `coupons-system`.`customers_vs_coupons` WHERE (`COUPON_ID` = ?);";
	public static final String delete_Coupon_Purchase_ForCustomer = "DELETE FROM `coupons-system`.`customers_vs_coupons` WHERE (`CUSTOMER_ID` = ?) ;";
	public static final String get_All_CouponsByCompanyID = "SELECT * FROM `coupons-system`.`coupons` WHERE (`COMPANY_ID`=?);";
	public static final String get_All_Coupons_ByCategory = "SELECT * FROM `coupons-system`.`coupons` WHERE `CATEDORY_ID` =?;";
	public static final String get_All_CustomerVsCoupons = "SELECT * FROM `coupons-system`.`customers_vs_coupons`;";

	public Category getCategoryName(int id) {

		Connection connection = null;
		String category = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT NAME FROM `coupons-system`.`categories` WHERE ID= " + id;

			PreparedStatement statment = connection.prepareStatement(sql);

			ResultSet resultSet = statment.executeQuery();
			while (resultSet.next()) {
				category = resultSet.getString(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}
		return Category.valueOf(category);

	}

	@Override
	public void addCoupon(Coupon coupon) throws CouldNotAddDataToTableException {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = add_Coupon;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCompanyID());
			statement.setInt(2, coupon.getCategory().ordinal() + 1);
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, (Date) coupon.getStartDate());
			statement.setDate(6, (Date) coupon.getEndDate());
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new CouldNotAddDataToTableException();

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public void updateCoupon(int couponID, Coupon coupon) throws IncorrectDetailsException {

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = update_Coupon;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, coupon.getTitle());
			statement.setString(2, coupon.getDescription());
			statement.setDate(3, coupon.getStartDate());
			statement.setDate(4, coupon.getEndDate());
			statement.setInt(5, coupon.getAmount());
			statement.setDouble(6, coupon.getPrice());
			statement.setString(7, coupon.getImage());
			statement.setInt(8, couponID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot update the coupon...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void deleteCoupon(int couponID) throws IncorrectDetailsException {

		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = delete_Coupon;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.executeUpdate();

		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot delete the coupon...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public List<Coupon> getAllCoupons() throws IncorrectDetailsException {

		List<Coupon> coupons = new ArrayList<>();

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = get_All_Coupons;

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyID(resultSet.getInt(2));
				coupon.setCategory(Category.values()[resultSet.getInt(3) - 1]);
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(resultSet.getDate(6));
				coupon.setEndDate(resultSet.getDate(7));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot return all the coupons...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupons;

	}

	@Override
	public Coupon getOneCoupon(int couponID) {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = get_One_Coupon;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyID(resultSet.getInt(2));
				coupon.setCategory(Category.values()[resultSet.getInt(3) - 1]);
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(resultSet.getDate(6));
				coupon.setEndDate(resultSet.getDate(7));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
				return coupon;

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return null;

	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) {

		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = add_Coupon_Purchase;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setInt(1, customerID);
			statment.setInt(2, couponID);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) {

		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = delete_Coupon_Purchase;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setInt(1, customerID);
			statment.setInt(2, couponID);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

	@Override
	public void deleteCouponPurchaseForCompany(int couponID) {

		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = delete_Coupon_Purchase_ForCompany;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setInt(1, couponID);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

	@Override
	public void deleteCouponPurchaseForCustomer(int customerID) {

		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = delete_Coupon_Purchase_ForCustomer;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setInt(1, customerID);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}

	}

	@Override
	public List<Coupon> getAllCouponsByCompanyID(int companyid) throws IncorrectDetailsException {

		List<Coupon> coupons = new ArrayList<Coupon>();

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = get_All_CouponsByCompanyID;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyid);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyID(resultSet.getInt(2));
				coupon.setCategory(Category.values()[resultSet.getInt(3) - 1]);
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(resultSet.getDate(6));
				coupon.setEndDate(resultSet.getDate(7));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot return all the coupons(by - ID)...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupons;

	}

	@Override
	public List<Coupon> getAllCouponsByCategory(Category category) throws IncorrectDetailsException {

		List<Coupon> coupons = new ArrayList<Coupon>();

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = get_All_Coupons_ByCategory;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, category.ordinal() + 1);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyID(resultSet.getInt(2));
				coupon.setCategory(Category.values()[resultSet.getInt(3) - 1]);
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(resultSet.getDate(6));
				coupon.setEndDate(resultSet.getDate(7));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
				coupons.add(coupon);
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot return all the coupons(by - category)...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupons;

	}

	@Override
	public List<CustomerVsCoupon> getAllCustomerVsCoupons() throws IncorrectDetailsException {

		List<CustomerVsCoupon> custVsCoupons = new ArrayList<CustomerVsCoupon>();
		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = get_All_CustomerVsCoupons;

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				CustomerVsCoupon customerVsCoupon = new CustomerVsCoupon();
				customerVsCoupon.setCustomerID(resultSet.getInt(1));
				customerVsCoupon.setCouponID(resultSet.getInt(2));
				custVsCoupons.add(customerVsCoupon);
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot return the table: Customer_Vs_Coupon...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return custVsCoupons;
	}

}
