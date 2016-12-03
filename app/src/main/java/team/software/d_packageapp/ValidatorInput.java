package team.software.d_packageapp;

import android.util.Patterns;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public final class ValidatorInput {

    boolean isValideName(String name){
        Pattern patron = Pattern.compile("[a-zA-Z ]{2,20}");
        return patron.matcher(name).matches() ;
    }

    boolean isvalideMail(String correo){
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    boolean isValidePassword(String password) {
        return password.length() > 7;
    }

    boolean isValideDate(String dateToValidate, String dateFromat){
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

    boolean isValideNumber(String number){
        return number.length() == 11;
    }

    boolean isValideAddress(String address) {
        return  address.length() > 0;
    }

    boolean isValideLicense(String license) {
        return license.length() > 0;
    }

    boolean isValideColor(String color) {
        return color.length() == 7;
    }

    boolean isValidePlateNumber(String plateNumber) {
        return plateNumber.length() == 7;
    }

    boolean isValidaEqualPassword(String newPassword, String verifyPassword) {
        return newPassword.equals(verifyPassword);
    }

    boolean isValideAccount(String numAccount) {
        return numAccount.length() == 20;
    }

    boolean isValideBank(String selection, String stringToCompare) {
        return !selection.equals(stringToCompare);
    }

    boolean isValideIdNumber(String idNum) {
        return idNum.length() > 6 && idNum.length() < 9;
    }
}
