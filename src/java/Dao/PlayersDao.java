/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Helpers.Evaluator;
import Managers.DBManager;
import Models.Category;
import Models.Player;
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
public class PlayersDao {
    public List<Player> getInvitedPlayersForTraining(int idTraining, DBManager db, Connection conn) {
        List<Player> invitedPlayersForTraining = null;
        String invitedPlayersForTrainingQuery = "SELECT id_player, firstname, nick, surname, number, accepts " +
                                                "FROM player join player_on_training using (id_player) " +
                                                "WHERE id_training = " + idTraining + " " +
                                                "ORDER BY id_player";
        ResultSet rInvitedPlayers = db.selectQuery(conn, invitedPlayersForTrainingQuery);
        try {
            invitedPlayersForTraining = new ArrayList<Player>();
            while (rInvitedPlayers.next()) {
                int idPlayer = rInvitedPlayers.getInt("id_player");
                String firstname = rInvitedPlayers.getString("firstname");
                String nick = rInvitedPlayers.getString("nick");
                String surname = rInvitedPlayers.getString("surname");
                int number = rInvitedPlayers.getInt("number");
                int playerAcceptedTraining = Evaluator.evaluateAcceptedTraining(rInvitedPlayers, "accepts");
                
                rInvitedPlayers.getObject("accepts");
                Player invitedPlayer = new Player();
                invitedPlayer.setIdPlayer(idPlayer);
                invitedPlayer.setFirstname(firstname);
                invitedPlayer.setNick(nick);
                invitedPlayer.setSurname(surname);
                invitedPlayer.setNumber(number);
                invitedPlayer.setPlayerAcceptedTraining(playerAcceptedTraining);
                invitedPlayersForTraining.add(invitedPlayer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return invitedPlayersForTraining;
    }
}
