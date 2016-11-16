package team.software.d_packageapp;

import android.util.Log;
import android.util.Patterns;

import java.util.regex.Pattern;

public final class validatorInput {
    public boolean isValideName(String name){
        Pattern patron = Pattern.compile("[a-zA-Z ]+$]");
        return !patron.matcher(name).matches() && name.length() > 0 && name.length() < 30;
    }

    public boolean isvalideMail(String correo){
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    public boolean isValidePassword(String password) {
        return password.length() > 8 && password.length() < 15;
    }

}
