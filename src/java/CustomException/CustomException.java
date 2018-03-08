/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomException;

/**
 *
 * @author Adam
 */
public class CustomException extends Exception {
    
    public static final int ERR_DATA_NOT_FOUND    = 10000;
    public static final int ERR_CONNECTION_CLOSE  = 10001;
    
    private int errorCode;
    
    public CustomException(int errorCode){
        this.errorCode = errorCode;
    }
    
}
