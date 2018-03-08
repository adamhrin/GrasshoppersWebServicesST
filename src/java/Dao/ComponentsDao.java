/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Managers.DBManager;
import Models.Category;
import Models.Category;
import Models.Component;
import Models.Component;
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
 * @param <T>
 */
public class ComponentsDao<T extends Component> {
    
    private Class<T> componentClass;
    
    private T getInstance() throws Exception {
        return this.componentClass.newInstance();
    }
    
    protected List<T/*Component*/> getComponents(String idComponentString, String componentDbTable)
    {
//        List<Component> componentsList = new ArrayList<Component>();
//        DBManager db = new DBManager();
//        Connection conn = db.getConnection();
//        if (conn == null) {
//            Component unsuccessful = new Component();
//            unsuccessful.setId(-1);
//            componentsList.add(unsuccessful);
//        } else {
//            //int idPlayer = 1; //toto cakam ze mi pride ako parameter, zatial davam skusobne 1
//            String allComponentsQuery = "SELECT " + idComponentString + ", name from " + componentDbTable;
//            ResultSet rAllComponents = db.selectQuery(conn, allComponentsQuery);
//            try {
//                //categoriesList = new ArrayList<Category>();
//                while (rAllComponents.next()) {
//                    Component component = new Component();
//                    
//                    int idComponent = rAllComponents.getInt(idComponentString);
//                    component.setId(idComponent);
//                    
//                    String name = rAllComponents.getString("name");
//                    component.setName(name);
//                    
//                    componentsList.add(component);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
//            }        
//        }
//        try {  
//            if(conn != null)
//                conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return componentsList;
        List<T> componentsList = new ArrayList<T>();
        DBManager db = new DBManager();
        Connection conn = db.getConnection();
        if (conn == null) {
            T unsuccessful = null;
            try {
                unsuccessful = this.getInstance();
            } catch (Exception ex) {
                return null;
            }
            unsuccessful.setId(-1);
            componentsList.add(unsuccessful);
        } else {
            //int idPlayer = 1; //toto cakam ze mi pride ako parameter, zatial davam skusobne 1
            String allComponentsQuery = "SELECT " + idComponentString + ", name from " + componentDbTable;
            ResultSet rAllComponents = db.selectQuery(conn, allComponentsQuery);
            try {
                //categoriesList = new ArrayList<Category>();
                while (rAllComponents.next()) {
                    T component = null;
                    try {
                        component = this.getInstance();
                    } catch (Exception ex) {
                        Logger.getLogger(ComponentsDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    int idComponent = rAllComponents.getInt(idComponentString);
                    component.setId(idComponent);
                    
                    String name = rAllComponents.getString("name");
                    component.setName(name);
                    
                    componentsList.add(component);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
        try {  
            if(conn != null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return componentsList;
    }
}
