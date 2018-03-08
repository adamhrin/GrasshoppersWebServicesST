/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import CustomException.CustomException;
import Managers.DBManager;
import Models.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class CategoriesDao extends ComponentsDao<Category> {
    
    public List<Category> getCategoriesForTraining(int idTraining, DBManager db, Connection conn) {
        List<Category> categoriesForTraining = null;
        String allCategoriesForTrainingQuery = "SELECT id_category, name " +
                                               "FROM category join category_on_training using (id_category) " +
                                               "WHERE id_training = " + idTraining + " " +
                                               "ORDER BY id_category";
        ResultSet rAllCategories = db.selectQuery(conn, allCategoriesForTrainingQuery);
        try {
            categoriesForTraining = new ArrayList<Category>();
            while (rAllCategories.next()) {
                int idCategory = rAllCategories.getInt("id_category");
                String name = rAllCategories.getString("name");
                Category category = new Category();
                category.setId(idCategory);
                category.setName(name);
                categoriesForTraining.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return categoriesForTraining;
    }
    
    public List<Category> getCategories() throws CustomException {
//        return (List<Category>)super.getComponents("id_category", "category");
        List<Category> categoriesList = new ArrayList<Category>();
        DBManager db = new DBManager();
        Connection conn = db.getConnection();
        if (conn == null) {
            Category unsuccessful = new Category();
            unsuccessful.setId(-1);
            categoriesList.add(unsuccessful);
        } else {
            //int idPlayer = 1; //toto cakam ze mi pride ako parameter, zatial davam skusobne 1
            String allCategoriesQuery = "SELECT id_category, name from category";
            ResultSet rAllCategories = db.selectQuery(conn, allCategoriesQuery);
            try {
                //categoriesList = new ArrayList<Category>();
                while (rAllCategories.next()) {
                    Category category = new Category();
                    
                    int idCategory = rAllCategories.getInt("id_category");
                    category.setId(idCategory);
                    
                    String name = rAllCategories.getString("name");
                    category.setName(name);
                    
                    categoriesList.add(category);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomException(CustomException.ERR_DATA_NOT_FOUND);
            }        
        }
        try {  
            if(conn != null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(CustomException.ERR_CONNECTION_CLOSE);
        }
        return categoriesList;
    }
}
