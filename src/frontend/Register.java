package frontend;

import javax.swing.*;
import java.io.IOException;

public class Register {
    public Register() {
        registrationInfoToServer = new RegistrationInfoToServer();
    }



    public boolean processRegistration(String firstName, String lastName, String userName, String password, String confirmPassword) throws IOException {
        if (firstName.equals("") || lastName.equals("") || userName.equals("") || password.equals("") || confirmPassword.equals("")) {
            JOptionPane.showMessageDialog(null, "One or More Fields are Blank", "Register", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(password.equals(confirmPassword))) {
            JOptionPane.showMessageDialog(null, "Password Doesnot Match", "Register", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String regXml = firstName.trim() + " " + lastName.trim() + " " + userName.trim() + " " + password.trim() + " " + confirmPassword.trim();
        System.out.println("data sending to sendRegistrationInfo");
        return registrationInfoToServer.sendRegistrationInfo(regXml);

    }

//    variable Declaration
    RegistrationInfoToServer registrationInfoToServer;
}

