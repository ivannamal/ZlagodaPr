package ZLAGODA_project.DataBase;

import ZLAGODA_project.DataBase.Exeptions.InvalidDateFormatException;

public class Utils {

    static public java.sql.Date CheckDate(String date) throws InvalidDateFormatException {
        try {
            return java.sql.Date.valueOf(date);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidDateFormatException();
            //e.printStackTrace();
        }
    }

}
