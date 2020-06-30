package com.johnbryce.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.couponssystem.exception.TableException;

public class Database {
	private static Connection connection;
	// My Details to MySQl Workbench:
	private static final String url = "jdbc:mysql://localhost:3306/123?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "e1l1a1d1";

	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	private final static String CREATAE_SCHEMA = "CREATE SCHEMA `coupons-system` ;";
	private final static String CREATE_TABLE_COMPANIES = "CREATE TABLE `coupons-system`.`companies` ( `ID` INT NOT NULL AUTO_INCREMENT,"
			+ " `NAME` VARCHAR(45) NOT NULL, `EMAIL` VARCHAR(45) NOT NULL,"
			+ "`PASSWORD` VARCHAR(45) NOT NULL,  PRIMARY KEY (`ID`))";
	private final static String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupons-system`.`customers` (`ID` INT NOT NULL AUTO_INCREMENT, "
			+ "`FIRST_NAME` VARCHAR(45) NOT NULL,`LAST_NAME` VARCHAR(45) NOT NULL, "
			+ "`EMAIL` VARCHAR(45) NOT NULL,`PASSWORD` VARCHAR(45) NOT NULL,PRIMARY KEY (`ID`))";
	private final static String CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupons-system`.`categories`(`ID` INT NOT NULL AUTO_INCREMENT,`NAME` VARCHAR(45) NOT NULL,  PRIMARY KEY (`ID`))";
	private final static String CREATE_TABLE_COUPONS = "CREATE TABLE `coupons-system`.`coupons` (`ID` INT NOT NULL AUTO_INCREMENT,"
			+ " `COMPANY_ID` INT NOT NULL, `CATEDORY_ID` INT NOT NULL, `TITLE` VARCHAR(45) NOT NULL, "
			+ " `DESCRIPTION` VARCHAR(100) NOT NULL,`START_DATE` DATE NOT NULL,`END_DATE` DATE NOT NULL, "
			+ " `AMOUNT` INT NOT NULL,`PRICE` DOUBLE NOT NULL,  `IMAGE` VARCHAR(100) NULL, PRIMARY KEY (`ID`), "
			+ " INDEX `COMPANY_ID_idx` (`COMPANY_ID` ASC) VISIBLE,  INDEX `CATEGORY_ID_idx` (`CATEDORY_ID` ASC) VISIBLE, "
			+ " CONSTRAINT `COMPANY_ID` FOREIGN KEY (`COMPANY_ID`)  REFERENCES `coupons-system`.`companies` (`ID`) "
			+ " ON DELETE NO ACTION  ON UPDATE NO ACTION, CONSTRAINT `CATEGORY_ID`  FOREIGN KEY (`CATEDORY_ID`) REFERENCES `coupons-system`.`categories` (`ID`) "
			+ " ON DELETE NO ACTION  ON UPDATE NO ACTION)";
	private final static String CREATE_TABLE_CUSTOMER_VS_COUPONS = "CREATE TABLE `coupons-system`.`customers_vs_coupons` (`CUSTOMER_ID` INT NOT NULL,`COUPON_ID` INT NOT NULL,"
			+ " PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`), INDEX `COUPON_ID_idx` (`COUPON_ID` ASC) VISIBLE,CONSTRAINT `CUSTOMER_ID`"
			+ " FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `coupons-system`.`customers` (`ID`) ON DELETE NO ACTION "
			+ " ON UPDATE NO ACTION, CONSTRAINT `COUPON_ID` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupons-system`.`coupons` (`ID`)"
			+ " ON DELETE NO ACTION  ON UPDATE NO ACTION)";

	// Create Schema Coupons System
	public static void createSchemaCouponsSystem() {
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = CREATAE_SCHEMA;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	// Create Table Companies
	public static void createTableCompanies() throws SQLException, TableException {

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = CREATE_TABLE_COMPANIES;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			throw new TableException();
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// Create Table Customers
	public static void createTableCustomers() throws SQLException, TableException {

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = CREATE_TABLE_CUSTOMERS;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			throw new TableException();
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// Create Table Categories
	public static void createTableCategories() throws SQLException, TableException {

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = CREATE_TABLE_CATEGORIES;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			throw new TableException();
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// Create Table Coupons
	public static void createTableCoupons() throws SQLException, TableException {

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = CREATE_TABLE_COUPONS;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			throw new TableException();
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// Create Table Customers_vs_coupons
	public static void createTableCustomersVsCoupons() throws SQLException, TableException {

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = CREATE_TABLE_CUSTOMER_VS_COUPONS;

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			throw new TableException();
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// Create All Tables
	public static void createAllTables() throws SQLException, TableException {
		createSchemaCouponsSystem();
		createTableCategories();
		createTableCompanies();
		createTableCustomers();
		createTableCoupons();
		createTableCustomersVsCoupons();
	}
}