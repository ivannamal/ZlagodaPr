package ZLAGODA_project.DataBase.DataClasses;

import ZLAGODA_project.DataBase.Exeptions.InvalidDateFormatException;
import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class CheqData {

    public CheqData(){}
    public CheqData(
    Integer check_number,
    Integer id_employee,
    Integer card_number,
    String print_date
    ) throws InvalidDateFormatException {
        try {
            _check_number = check_number;
            _id_employee = id_employee;
            _card_number = card_number;
            _print_date = Timestamp.valueOf(print_date);
        } catch (IllegalArgumentException ee) {
            throw new InvalidDateFormatException();
        }

    }
    public CheqData(
            String check_number,
            String id_employee,
            String card_number,
            String print_date
    ) throws InvalidDateFormatException, InvalidValueException {

        try {
            _check_number = Integer.valueOf(check_number);
            _id_employee = Integer.valueOf(id_employee);
            if(card_number.equalsIgnoreCase("null")){
                _card_number = null;
            }
            else{
                _card_number = Integer.valueOf(card_number);
            }
        }
        catch (Exception e){
            throw new InvalidValueException();
        }
        try {
            _print_date = Timestamp.valueOf(print_date);
        } catch (IllegalArgumentException ee) {
            throw new InvalidDateFormatException();
            //e.printStackTrace();
        }

    }

    public Integer _check_number = null;
    public Integer _id_employee = null;
    public Integer _card_number = null;
    public Timestamp _print_date = null;


}
