package team.software.d_packageapp;

import android.util.Patterns;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        return password.length() > 8;
    }

    public boolean isValideDate(String dateToValidate, String dateFromat){
        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean isValideNumber(String number){
        return Patterns.PHONE.matcher(number).matches();
    }

    public boolean isValideAddress(String address) {
        if (address.length() > 0) {
            return true;
        }
        return  false;
    }

    public boolean isValideLicense(String license) {
        if (license.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean isValideYear(String year) {
        if (year.length() == 4) {
            return true;
        }
        return false;
    }

    public boolean isValidePlateNumber(String plateNumber) {
        if (plateNumber.length() == 6) {
            return true;
        }
        return false;
    }
}
