/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Managers.DBManager;
import Models.Player;
import Models.Position;
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
public class PositionsDao {

    public List<Position> getPositionsForBrigade(int idBrigade, int idPlayer, boolean isRegisteredPlayerForBrigade, DBManager db, Connection conn) {
        List<Position> positionsForBrigade = null;
        String allPositionsForBrigadeQuery = "SELECT id_position, position.name, hours, id_player, firstname, nick, surname " +
                                            "FROM brigade_position_player " +
                                            "join brigade using (id_brigade) " +
                                            "join position using (id_position) " +
                                            "left join player using (id_player) WHERE id_brigade = " + idBrigade + " " +
                                            "order by id_position";
        ResultSet rAllPositions = db.selectQuery(conn, allPositionsForBrigadeQuery);
        try {
            positionsForBrigade = new ArrayList<Position>();
            while (rAllPositions.next()) {
                int idPosition = rAllPositions.getInt("id_position");
                String positionName = rAllPositions.getString("name");
                int numOfHours = rAllPositions.getInt("hours");
                
                //Player na pozicii
                //mysli sa ten player ktory odoslal request, cize ten, koho appka to ma zobrazit
                boolean isRegisteredPlayerForPosition = false;
                Player registeredPlayer = null;
                int idPlayerForPosition = rAllPositions.getInt("id_player");
                if (!rAllPositions.wasNull())
                {
                    registeredPlayer = new Player();
                    registeredPlayer.setFirstname(rAllPositions.getString("firstname"));
                    registeredPlayer.setNick(rAllPositions.getString("nick"));
                    registeredPlayer.setSurname(rAllPositions.getString("surname"));
                    if (idPlayer == idPlayerForPosition)
                    {
                        isRegisteredPlayerForPosition = true;
                    }
                }
                Position position = new Position();
                position.setId(idPosition);
                position.setName(positionName);
                position.setNumberOfHours(numOfHours);
                position.setIsRegisteredPlayerForBrigade(isRegisteredPlayerForBrigade);
                position.setIsRegisteredPlayerForPosition(isRegisteredPlayerForPosition);
                position.setRegisteredPlayer(registeredPlayer);
                positionsForBrigade.add(position);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return positionsForBrigade;
    }
    
}
