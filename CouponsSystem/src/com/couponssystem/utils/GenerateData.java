package com.couponssystem.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import com.couponssystem.beans.Category;
import com.couponssystem.beans.Company;
import com.couponssystem.beans.Coupon;
import com.couponssystem.beans.Customer;
import com.couponssystem.dao.CouponsDAO;
import com.couponssystem.dbdao.CategoriesDBDAO;
import com.couponssystem.dbdao.CompaniesDBDAO;
import com.couponssystem.dbdao.CouponsDBDAO;
import com.couponssystem.dbdao.CustomersDBDAO;
import com.couponssystem.exception.AlreadyExitException;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;
import com.couponssystem.exception.PurchaseCouponException;
import com.couponssystem.exception.TableException;
import com.couponssystem.facade.AdminFacade;
import com.couponssystem.facade.CompanyFacade;
import com.couponssystem.facade.CustomerFacade;
import com.couponssystem.security.ClientType;
import com.couponssystem.security.LoginManager;
import com.johnbryce.db.ConnectionPool;
import com.johnbryce.db.Database;
// Create Data For Table
public class GenerateData {

	final static int amount = 100;
	final static int ElectricityPrice = 50;
	final static double FoodPrice = 4.9;
	final static int RestaurantPrice = 10;
	final static int SpaPrice = 300;
	final static int VacationPrice = 50;

	// Create Categories:
	public static void generateCategories() throws CouldNotAddDataToTableException {
		CategoriesDBDAO categoriesDBDAO = new CategoriesDBDAO();
		categoriesDBDAO.addCategory(Category.ELECTRICITY);
		categoriesDBDAO.addCategory(Category.FOOD);
		categoriesDBDAO.addCategory(Category.RESTAURANT);
		categoriesDBDAO.addCategory(Category.SPA);
		categoriesDBDAO.addCategory(Category.VACATION);

	}

	// Create Companies:
	public static void generateCompanies() throws CouldNotAddDataToTableException {
		CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
		companiesDBDAO.addCompany(new Company("Electricity-Store", "Electricity@gmail.com", "Electricity1234"));
		companiesDBDAO.addCompany(new Company("Coca-cola", "cola@gmail.com", "cola1234"));
		companiesDBDAO.addCompany(new Company("Wiskey-bar", "Wiskey@gmail.com", "Wiskey1234"));
		companiesDBDAO.addCompany(new Company("Spa-Hotel", "spa@gamil.com", "spa1234"));
		companiesDBDAO.addCompany(new Company("Trivago", "Trivago@gmail.com", "Trivago1234"));

	}

	// Create Coupons:
	@SuppressWarnings("deprecation")
	public static void generateCoupons() throws CouldNotAddDataToTableException {
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
		Coupon coupon1 = new Coupon(1, Category.ELECTRICITY, "Get 100 Shekels!",
				"pay only 50 Shekels and Get 100 Shekels to our shop.", new java.sql.Date(120, 01, 01),
				new java.sql.Date(121, 01, 01), amount, ElectricityPrice, "url-for-img");
		Coupon coupon2 = new Coupon(2, Category.FOOD, "1+1", "buy one cola and get two cola.",
				new java.sql.Date(120, 01, 01), new java.sql.Date(121, 01, 01), amount, FoodPrice, "url-for-img");
		Coupon coupon3 = new Coupon(3, Category.RESTAURANT, "free Dessert",
				"if you come to our Restaurant you can Get a free Dessert(if you order 80 Shekels or more...).",
				new java.sql.Date(120, 01, 01), new java.sql.Date(121, 01, 01), amount, RestaurantPrice, "url-for-img");
		Coupon coupon4 = new Coupon(4, Category.SPA, "45 minutes of massage for couple",
				"You will pay the price of one massage and receive a double massage in our hotel in the center",
				new java.sql.Date(120, 01, 01), new java.sql.Date(121, 01, 01), amount, SpaPrice, "url-for-img");
		Coupon coupon5 = new Coupon(5, Category.VACATION, "want 30% of a Room?",
				"Flying for vacation? Just going on vacation? This is the coupon for you! Get 30% off.",
				new java.sql.Date(120, 01, 01), new java.sql.Date(121, 01, 01), amount, VacationPrice, "url-for-img");
		couponsDBDAO.addCoupon(coupon1);
		couponsDBDAO.addCoupon(coupon2);
		couponsDBDAO.addCoupon(coupon3);
		couponsDBDAO.addCoupon(coupon4);
		couponsDBDAO.addCoupon(coupon5);
	}

	// Create Customers:
	public static void generateCustomersForTable() throws CouldNotAddDataToTableException {
		CustomersDBDAO customersDBDAO = new CustomersDBDAO();
		customersDBDAO.addCustomer(new Customer("Elad", "Hakmon", "elad@gmail.com", "elad12345"));
		for (int i = 0; i < 20; i++) {
			customersDBDAO.addCustomer(generateCustomer());
		}

	}

	// Generate Random Customers:
	public static Customer generateCustomer() {
		Customer customer = new Customer(generateFirstName(), generateLastName(), null, null);
		customer.setEmail(generateEmail(customer));
		customer.setPassword(generatePassword(customer));
		return customer;
	}

	// Generate Random Customers - First Name:
	public static String generateFirstName() {

		int rand = (int) (Math.random() * 25);
		switch (rand) {
		case 0:
			return "elad";
		case 1:
			return "kobi";
		case 2:
			return "dan";
		case 3:
			return "haki";
		case 4:
			return "heli";
		case 5:
			return "natali";
		case 6:
			return "omer";
		case 7:
			return "karin";
		case 8:
			return "danit";
		case 9:
			return "yossi";
		case 10:
			return "hen";
		case 11:
			return "ben";
		case 12:
			return "yoni";
		case 13:
			return "kfir";
		case 14:
			return "sharon";
		case 15:
			return "yaeli";
		case 16:
			return "dana";
		case 17:
			return "sapir";
		case 18:
			return "moshe";
		case 19:
			return "rotem";
		case 20:
			return "eden";
		case 21:
			return "israel";
		case 22:
			return "noha";
		case 23:
			return "malachi";
		case 24:
			return "tchahi";

		default:
			return null;
		}

	}

	// Generate Random Customers - Last Name:
	public static String generateLastName() {
		String string = " ";
		Random random = new Random();
		String abc = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < 5; i++) {
			string += abc.charAt(random.nextInt(abc.length()));

		}
		return string;
	}

	// Generate Random Customers - Email:
	public static String generateEmail(Customer customer) {
		return customer.getFirstName() + (int) (Math.random() * 1000 + 100) + "@gmail.com";
	}

	// Generate Random Customers - Password:
	public static String generatePassword(Customer customer) {
		return customer.getLastName() + "12345";
	}

	// Create all Random Data:
	public static void getAllData() throws CouldNotAddDataToTableException, SQLException, TableException {
		Database.createAllTables();
		generateCategories();
		generateCompanies();
		generateCoupons();
		generateCustomersForTable();
	}

	// Login as admin:
	public static void loginAsAdmin()
			throws IncorrectDetailsException, AlreadyExitException, CouldNotAddDataToTableException {
		// Login as Administrator:
		AdminFacade eladHakmon = (AdminFacade) LoginManager.login("admin@admin.com", "admin", ClientType.Administrator);

		// Create new Company to Check the AdminFacade:
		Company c = new Company("new-company", "newcompany@gmail.com", "newcompany12345");
		// add Company to Table:
		eladHakmon.addCompany(c);
		// Get one Company By `ID`:
		System.out.println(eladHakmon.getOneCompany(getCompanyID(c.getEmail())));
		space();
		c.setName("update-company");
		// Update the Company `NAME`:
		eladHakmon.updateCompany(getCompanyID(c.getEmail()), c);
		System.out.println(eladHakmon.getOneCompany(getCompanyID(c.getEmail())));
		space();
		// Delete the Company from Table:
		eladHakmon.deleteCompany(getCompanyID(c.getEmail()));
		// Get all Companies:
		System.out.println(eladHakmon.getAllCompanies());
		space();
		// Create new Customer to Check the AdminFacade:
		Customer customer = new Customer("Facade", "Customer", "facadeCustomer@gmail.com", "facadeCustomer12345");
		// add Customer to Table:
		eladHakmon.addCustomer(customer);
		// Get one Customer By `ID`:
		System.out.println(eladHakmon.getOneCustomer(getCustomerID(customer.getEmail())));
		space();
		customer.setLastName("Update");
		// Update the Customer `LAST_NAME`:
		eladHakmon.updateCustomer(getCustomerID(customer.getEmail()), customer);
		System.out.println(eladHakmon.getOneCustomer(getCustomerID(customer.getEmail())));
		space();
		// Delete the Company from Table:
		eladHakmon.deleteCustomer(getCustomerID(customer.getEmail()));
		// Get all Customers:
		System.out.println(eladHakmon.getAllCustomers());
		space();

	}

	@SuppressWarnings("deprecation")
	// Login as Company:
	public static void loginAsCompany() throws IncorrectDetailsException, CouldNotAddDataToTableException {
		// Login as Company:
		CompanyFacade cola = (CompanyFacade) LoginManager.login("cola@gmail.com", "cola1234", ClientType.Company);
		// add new Coupon to Check the CompanyFacade:
		Coupon coupon = new Coupon(2, Category.FOOD, "new coupon - Build from Facade.",
				"try to Build a coupon from the Facade", new java.sql.Date(120, 1, 1), new java.sql.Date(121, 1, 1),
				amount, 10, "url-for-img");
		// add the Coupon to Table:
		cola.addCoupon(coupon);
		// Print the Coupons of the Selected Company:
		System.out.println(cola.getCompanyCoupons());
		space();
		coupon.setTitle("blabla");
		// Update the Coupon(I have access to the coupon ID):
		cola.updateCoupon(6, coupon);
		// Print Company Details:
		System.out.println(cola.getCompanyDetails());
		space();
		// Print the Coupons By Price:
		System.out.println(cola.getCompanyCoupons(9)); // Random Price
		space();
		// Print the Coupons By Category:
		System.out.println(cola.getCompanyCoupons(Category.FOOD));
		space();
		// Delete the Coupon from Table:
		cola.deleteCoupon(coupon.getId());
		System.out.println(cola.getCompanyDetails());
		space();

	}

	// Login as Customer:
	public static void loginAsCustomer() throws IncorrectDetailsException, PurchaseCouponException {
		// Login as Customer:
		CustomerFacade eladHakmon = (CustomerFacade) LoginManager.login("elad@gmail.com", "elad12345",
				ClientType.Customer);
		CouponsDAO couponsDAO = new CouponsDBDAO();
		// Get one Coupon from the Table;
		Coupon coupon = couponsDAO.getOneCoupon(2);
		// Customer Buy a Coupon:
		eladHakmon.purchaseCoupon(coupon);
		// Get all Customer Coupons:
		System.out.println(eladHakmon.getCustomerCoupons());
		space();
		// Get all Customer Coupons By Category:
		System.out.println(eladHakmon.getCustomerCoupons(Category.FOOD));
		space();
		// Get all Customer Coupons By Price:
		System.out.println(eladHakmon.getCustomerCoupons(5));
		space();
		// Get all Customer Details:
		System.out.println(eladHakmon.getCustomerDetails());

	}

	public static int getCustomerID(String email) {
		Connection connection = null;
		int idx = 0;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT `ID` FROM `coupons-system`.`customers` WHERE (`EMAIL`=?);";

			PreparedStatement statment = connection.prepareStatement(sql);

			statment.setString(1, email);
			ResultSet resultSet = statment.executeQuery();
			while (resultSet.next()) {
				idx = resultSet.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}
		return idx;
	}

	public static int getCompanyID(String email) {
		Connection connection = null;
		int idx = 0;
		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT `ID` FROM `coupons-system`.`companies` WHERE (`EMAIL`=?);";

			PreparedStatement statment = connection.prepareStatement(sql);

			statment.setString(1, email);
			ResultSet resultSet = statment.executeQuery();
			while (resultSet.next()) {
				idx = resultSet.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			ConnectionPool.getInstance().returnConnection(connection);

		}
		return idx;
	}

	public static void space() {
		System.out.println();
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println();
	}
}
