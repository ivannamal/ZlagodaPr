package ZLAGODA_project.DataBase.DataClasses;

import ZLAGODA_project.DataBase.Exeptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;

public class EmployeeData {

    public EmployeeData(){}
    public EmployeeData(
    Integer id_employee,
    String empl_surname,
    String empl_name,
    String empl_patronymic,
    String empl_role,
    Double salary,
    String date_of_birth,
    String data_of_start,
    String phone_number,
    String city,
    String street,
    String zip_code
    ) throws NegativeValueException, TooBigNumberException, InvalidDateFormatException, TooYoungException {
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            _id_employee = id_employee;
            _empl_surname = empl_surname;
            _empl_name = empl_name;
            _empl_patronymic = empl_patronymic;
            _empl_role = empl_role;
            _salary = salary;
            if(_salary < 0){
                throw new NegativeValueException();
            }
            _date_of_birth = java.sql.Date.valueOf(date_of_birth);
            _data_of_start = java.sql.Date.valueOf(data_of_start);

            {
                long d1 = _date_of_birth.getTime();
                long d2 = _data_of_start.getTime();
                long age = (d2 - d1) / (1000L * 60 * 60 * 24 * 365);
                if(age < 18){
                    throw new TooYoungException();
                }
            }

            {
                long d1 = _date_of_birth.getTime();
                long d2 = System.currentTimeMillis();
                long age = (d2 - d1) / (1000L * 60 * 60 * 24 * 365);
                if(age < 18){
                    throw new TooYoungException();
                }
            }
                _phone_number = phone_number;
            if(_phone_number.length() > 13){
                throw new TooBigNumberException();
            }
            _city = city;
            _street = street;
            _zip_code = zip_code;
        }
        catch (IllegalArgumentException e) {
            throw new InvalidDateFormatException();
            //e.printStackTrace();
        }

    }
    public EmployeeData(
            String id_employee,
            String empl_surname,
            String empl_name,
            String empl_patronymic,
            String empl_role,
            String salary,
            String date_of_birth,
            String data_of_start,
            String phone_number,
            String city,
            String street,
            String zip_code
    ) throws NegativeValueException, TooBigNumberException, InvalidDateFormatException, TooYoungException {
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            try {
                if(id_employee.length() == 0){
                    _id_employee = 0;
                }
                else{
                    _id_employee = Integer.valueOf(id_employee);
                }
                _salary = Double.valueOf(salary);
            }
            catch (Exception e){
                throw new InvalidValueException();
            }

            _empl_surname = empl_surname;
            _empl_name = empl_name;

            if(empl_patronymic.equalsIgnoreCase("null") || empl_patronymic.length() == 0){
                _empl_patronymic = null;
            }
            else{
                _empl_patronymic = empl_patronymic;
            }

            _empl_role = empl_role;

            if(_salary < 0){
                throw new NegativeValueException();
            }
            _date_of_birth = java.sql.Date.valueOf(date_of_birth);
            _data_of_start = java.sql.Date.valueOf(data_of_start);

            {
                long d1 = _date_of_birth.getTime();
                long d2 = _data_of_start.getTime();
                long age = (d2 - d1) / (1000L * 60 * 60 * 24 * 365);
                if(age < 18){
                    throw new TooYoungException();
                }
            }

            {
                long d1 = _date_of_birth.getTime();
                long d2 = System.currentTimeMillis();
                long age = (d2 - d1) / (1000L * 60 * 60 * 24 * 365);
                if(age < 18){
                    throw new TooYoungException();
                }
            }
            _phone_number = phone_number;
            if(_phone_number.length() > 13){
                throw new TooBigNumberException();
            }
            _city = city;
            _street = street;
            _zip_code = zip_code;
        }
        catch (IllegalArgumentException | InvalidValueException e) {
            throw new InvalidDateFormatException();
            //e.printStackTrace();
        }

    }

    public Integer _id_employee = null;
    public String _empl_surname = null;
    public String _empl_name = null;
    public String _empl_patronymic = null;
    public String _empl_role = null;
    public Double _salary = null;
    public java.sql.Date _date_of_birth = null;
    public java.sql.Date _data_of_start = null;
    public String _phone_number = null;
    public String _city = null;
    public String _street = null;
    public String _zip_code = null;

}
