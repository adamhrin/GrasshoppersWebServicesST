/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import CustomException.CustomException;
import Helpers.Evaluator;
import Managers.DBManager;
import Models.Brigade;
import Models.League;
import Models.Location;
import Models.Training;
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
public class BrigadesDao {

    public List<Brigade> getBrigades() throws CustomException {
        List<Brigade> brigadesList = new ArrayList<Brigade>();
        DBManager db = new DBManager();
        Connection conn = db.getConnection();
        if (conn == null) {
            Brigade unsuccessful = new Brigade();
            unsuccessful.setIdBrigade(-1);
            brigadesList.add(unsuccessful);
        } else {
            int idPlayer = 1; //toto cakam ze mi pride ako parameter, zatial davam skusobne 1
            String allBrigadesQuery = "SELECT brigade.id_brigade, id_league, league.name as league_name, id_location, location.name as location_name, " +
                                      "start_date_time, DATE_FORMAT(start_date_time,'%H:%i:%s') as start_time, " +
                                      "end_date_time, DATE_FORMAT(end_date_time,'%H:%i:%s') as end_time, " +
                                      "(select (count(*) > 0) from brigade_position_player where id_brigade = brigade.id_brigade and id_player = " + idPlayer + ") as is_registered_player " +
                                      "FROM brigade join location using (id_location) join league using (id_league) " + 
                                      "ORDER BY start_date_time";
            ResultSet rAllBrigades = db.selectQuery(conn, allBrigadesQuery);
            try {
                //brigadesList = new ArrayList<Brigade>();
                while (rAllBrigades.next()) {
                    Brigade brigade = new Brigade();
                    
                    int idBrigade = rAllBrigades.getInt("id_brigade");
                    brigade.setIdBrigade(idBrigade);
                    
                    Location location = new Location();
                    location.setId(rAllBrigades.getInt("id_location"));
                    location.setName(rAllBrigades.getString("location_name"));
                    brigade.setLocation(location);
                    
                    League league = new League();
                    league.setId(rAllBrigades.getInt("id_league"));
                    league.setName(rAllBrigades.getString("league_name"));
                    brigade.setLeague(league);
                    
                    //START DATE AND TIME
                    String startDateTimeString = rAllBrigades.getString("start_date_time");
                    String startTimeString = rAllBrigades.getString("start_time");
                    brigade.setStartDateTimeString(startDateTimeString);
                    brigade.setStartTimeString(startTimeString);
                    
                    //END DATE AND TIME
                    String endDateTimeString = rAllBrigades.getString("end_date_time");
                    String endTimeString = rAllBrigades.getString("end_time");
                    brigade.setEndDateTimeString(endDateTimeString);
                    brigade.setEndTimeString(endTimeString);
                    
                    boolean isRegisteredPlayer = rAllBrigades.getBoolean("is_registered_player");
                    brigade.setIsRegisteredPlayer(isRegisteredPlayer);
                    
                    PositionsDao positionsDao = new PositionsDao();
                    brigade.setPositions(positionsDao.getPositionsForBrigade(idBrigade, idPlayer, isRegisteredPlayer, db, conn));
                    
                    brigadesList.add(brigade);
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
        return brigadesList;
    }
    
}
