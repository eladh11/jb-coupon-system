package com.couponssystem.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.couponssystem.beans.Category;
import com.couponssystem.dao.CategoriesDAO;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;
import com.johnbryce.db.ConnectionPool;

public class CategoriesDBDAO implements CategoriesDAO {

	public final static String add_Category = "INSERT INTO `coupons-system`.`categories` (`NAME`) VALUES (?);";
	public final static String update_Category = "UPDATE `coupons-system`.`categories` SET `NAME`=? WHERE (`ID`=?);";
	public final static String delete_Category = "DELETE FROM `coupons-system`.`categories` WHERE (`ID`=?);";
	public final static String get_All_Categories = "SELECT * FROM `coupons-system`.`categories`;";
	public final static String get_One_Category = "SELECT * FROM `coupons-system`.`categories` where (`ID`=?);";

	@Override
	public void addCategory(Category category) throws CouldNotAddDataToTableException {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = add_Category;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, category.toString());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new CouldNotAddDataToTableException();

		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public void updateCategory(int categoryID, Category category) throws IncorrectDetailsException {

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = update_Category;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, category.toString());
			statement.setInt(2, categoryID);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot update the table...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void deleteCategory(int CategoryID) throws IncorrectDetailsException {

		Connection connection = null;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = delete_Category;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, CategoryID);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot delete the category...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public List<Category> getAllCategories() throws IncorrectDetailsException {

		List<Category> categories = new ArrayList<Category>();
		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = get_All_Categories;

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int category = resultSet.getInt(1);
				categories.add(getCategoryName(category));
			}
		} catch (Exception e) {
			throw new IncorrectDetailsException("cannot return the categories...");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return categories;

	}

	@Override
	public Category getOneCategory(int categoryID) {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = get_One_Category;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, categoryID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int category = resultSet.getInt(1);
				return getCategoryName(category);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return null;
	}

	public com.couponssystem.beans.Category getCategoryName(int id) {

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
		return com.couponssystem.beans.Category.valueOf(category);

	}

}
