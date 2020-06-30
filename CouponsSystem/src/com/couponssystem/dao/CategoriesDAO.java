package com.couponssystem.dao;

import java.util.List;

import com.couponssystem.beans.Category;
import com.couponssystem.exception.CouldNotAddDataToTableException;
import com.couponssystem.exception.IncorrectDetailsException;

public interface CategoriesDAO {

	void addCategory(Category category) throws CouldNotAddDataToTableException;

	void updateCategory(int categoryID, Category category) throws IncorrectDetailsException;

	void deleteCategory(int CategoryID) throws IncorrectDetailsException;

	List<Category> getAllCategories() throws IncorrectDetailsException;

	Category getOneCategory(int categoryID);

}
