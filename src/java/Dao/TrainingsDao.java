/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import CustomException.CustomException;
import Helpers.Evaluator;
import Managers.DBManager;
import Models.Category;
import Models.Location;
import Models.Player;
import Models.Training;
import Models.User;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class TrainingsDao {

    public List<Training> getTrainings() throws CustomException {
        List<Training> trainingsList = new ArrayList<Training>();
        DBManager db = new DBManager();
        Connection conn = db.getConnection();
        if (conn == null) {
            Training unsuccessful = new Training();
            unsuccessful.setIdTraining(-1);
            trainingsList.add(unsuccessful);
        } else {
            int idPlayer = 1; //toto cakam ze mi pride ako parameter, zatial davam skusobne 1
            String allTrainingsQuery = "SELECT id_training, id_location, name, " +
                                              "start_date_time, DATE_FORMAT(start_date_time,'%H:%i:%s') as start_time, " +
                                              "end_date_time, DATE_FORMAT(end_date_time,'%H:%i:%s') as end_time, " +
                                              "accepts " +
                                       "FROM training join location using (id_location) join player_on_training using (id_training) " +
                                       "WHERE id_player = " + idPlayer + " " +
                                       "ORDER BY start_date_time";
            ResultSet rAllTrainings = db.selectQuery(conn, allTrainingsQuery);
            try {
                //trainingsList = new ArrayList<Training>();
                while (rAllTrainings.next()) {
                    Training training = new Training();
                    
                    int idTraining = rAllTrainings.getInt("id_training");
                    training.setIdTraining(idTraining);
                    
                    //START DATE AND TIME
                    String startDateTimeString = rAllTrainings.getString("start_date_time");
                    String startTimeString = rAllTrainings.getString("start_time");
                    training.setStartDateTimeString(startDateTimeString);
                    training.setStartTimeString(startTimeString);
                    
                    //END DATE AND TIME
                    String endDateTimeString = rAllTrainings.getString("end_date_time");
                    String endTimeString = rAllTrainings.getString("end_time");
                    training.setEndDateTimeString(endDateTimeString);
                    training.setEndTimeString(endTimeString);
                    
                    int acceptedByPlayer = Evaluator.evaluateAcceptedTraining(rAllTrainings, "accepts");
                    training.setAcceptedByPlayer(acceptedByPlayer);
                    
                    Location location = new Location();
                    location.setId(rAllTrainings.getInt("id_location"));
                    location.setName(rAllTrainings.getString("name"));
                    training.setLocation(location);
                    
                    CategoriesDao categoriesDao = new CategoriesDao();
                    training.setCategories(categoriesDao.getCategoriesForTraining(idTraining, db, conn));
                    
                    PlayersDao playersDao = new PlayersDao();
                    training.setInvitedPlayers(playersDao.getInvitedPlayersForTraining(idTraining, db, conn));
                    
                    trainingsList.add(training);
                }
            } catch (SQLException ex) {
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
        return trainingsList;
    }
    
}
