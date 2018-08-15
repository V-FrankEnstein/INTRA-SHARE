package frontend;

import javax.swing.*;
import java.io.IOException;

public class Login {

    public Login() {
        loginInfoToServer = new LoginInfoToServer();
    }


    public boolean processLogin(String userName, String password) throws IOException, ClassNotFoundException {
        if ( userName.trim().equals("") || password.trim().equals("") ) {
            JOptionPane.showMessageDialog(null, "Fields are Blank", "Login", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String logXml = userName.trim() + " " + password.trim();
        return loginInfoToServer.sendLoginInfo(logXml);
    }


//    variable Declaration
    LoginInfoToServer loginInfoToServer;
}
