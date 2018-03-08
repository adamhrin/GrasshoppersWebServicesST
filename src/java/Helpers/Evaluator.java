/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Adam
 */
public class Evaluator {

    public static int evaluateAcceptedTraining(ResultSet result, String acceptsString) throws SQLException {
        boolean playerAcceptedTrainingBoolean = result.getBoolean(acceptsString);
        if (result.wasNull()) {
            return 0;
        } else if (playerAcceptedTrainingBoolean) {
            return 1;
        } else {
            return -1;
        }
    }
    
}
