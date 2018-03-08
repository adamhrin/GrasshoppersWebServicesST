/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adam
 */
@XmlRootElement(name = "player")
public class Player implements Serializable {
    private int idPlayer; 
    private String firstname;
    private String nick;
    private String surname;
    private String password;
    private int number;
    private String fullName;
    //private boolean playerAcceptedTraining;
    private int playerAcceptedTraining;

    public int getIdPlayer() {
        return this.idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
    
    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFullName() {
        return this.firstname + " " + this.nick + " " + this.surname;
    }

//    public boolean getPlayerAcceptedTraining() {
//        return this.playerAcceptedTraining;
//    }
//
//    public void setPlayerAcceptedTraining(boolean playerAcceptedTraining) {
//        this.playerAcceptedTraining = playerAcceptedTraining;
//    }

    public int getPlayerAcceptedTraining() {
        return this.playerAcceptedTraining;
    }

    public void setPlayerAcceptedTraining(int playerAcceptedTraining) {
        this.playerAcceptedTraining = playerAcceptedTraining;
    }
    

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
}
