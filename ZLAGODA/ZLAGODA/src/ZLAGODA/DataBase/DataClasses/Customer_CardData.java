package ZLAGODA_project.DataBase.DataClasses;

import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;
import ZLAGODA_project.DataBase.Exeptions.NegativeValueException;
import ZLAGODA_project.DataBase.Exeptions.TooBigNumberException;

public class Customer_CardData {

    public Customer_CardData(){}
    public Customer_CardData(
    Integer card_number,
    String cust_surname,
    String cust_name,
    String cust_patronymic,
    String phone_number,
    String city,
    String street,
    String zip_code,
    Double percent
    ) throws TooBigNumberException, NegativeValueException {
        _card_number = card_number;
        _cust_surname = cust_surname;
        _cust_name = cust_name;
        _cust_patronymic = cust_patronymic;
        _phone_number = phone_number;
        if(_phone_number.length() > 13){
            throw new TooBigNumberException();
        }
        _city = city;
        _street = street;
        _zip_code = zip_code;
        _percent = percent;
        if(_percent > 1){
            _percent = 1.0;
        }
        else if(_percent < 0){
            throw new NegativeValueException();
        }
    }
    public Customer_CardData(
            String card_number,
            String cust_surname,
            String cust_name,
            String cust_patronymic,
            String phone_number,
            String city,
            String street,
            String zip_code,
            String percent
    ) throws TooBigNumberException, NegativeValueException, InvalidValueException {

        _cust_surname = cust_surname;
        _cust_name = cust_name;
        _phone_number = phone_number;
        if(_phone_number.length() > 13){
            throw new TooBigNumberException();
        }
        if(cust_patronymic.equalsIgnoreCase("null")){
            _cust_patronymic = null;
        }
        else{
            _cust_patronymic = cust_patronymic;
        }
        if(city.equalsIgnoreCase("null")){
            _city = null;
        }
        else{
            _city = city;
        }
        if(street.equalsIgnoreCase("null")){
            _street = null;
        }
        else{
            _street = street;
        }
        if(zip_code.equalsIgnoreCase("null")){
            _zip_code = null;
        }
        else{
            _zip_code = zip_code;
        }



        try {
            _card_number = Integer.valueOf(card_number);
            _percent = Double.valueOf(percent);
        }
        catch (Exception e){
            throw new InvalidValueException();
        }
        if(_percent > 1){
            _percent = 1.0;
        }
        else if(_percent < 0){
            throw new NegativeValueException();
        }
    }

    public Integer _card_number = null;
    public String _cust_surname = null;
    public String _cust_name = null;
    public String _cust_patronymic = null;
    public String _phone_number = null;
    public String _city = null;
    public String _street = null;
    public String _zip_code = null;
    public Double _percent = null;

}
