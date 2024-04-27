package ZLAGODA_project.DataBase.DataClasses;

import ZLAGODA_project.DataBase.Exeptions.*;

import java.sql.Date;

public class Store_ProductData {

    public Store_ProductData() {}

    public Store_ProductData(
            Integer UPC,
            String expire_Date,
            Integer id_product,
            Double selling_price,
            Integer products_number
    ) throws NegativeValueException, InvalidDateFormatException {
        _UPC = UPC;
        _expire_Date = java.sql.Date.valueOf(expire_Date);
        _id_product = id_product;
        if (selling_price < 0) {
            throw new NegativeValueException();
        }
        _selling_price = selling_price;
        if (products_number < 0) {
            throw new NegativeValueException();
        }
        _products_number = products_number;
    }

    public Store_ProductData(
            String UPC,
            String expire_Date,
            String id_product,
            String selling_price,
            String products_number
    ) throws NegativeValueException, InvalidDateFormatException, InvalidValueException {
        try {
            _UPC = Integer.valueOf(UPC);
            _id_product = Integer.valueOf(id_product);
            _selling_price = Double.valueOf(selling_price);
            _products_number = Integer.valueOf(products_number);
        } catch (NumberFormatException e) {
            throw new InvalidValueException();
        }

        try {
            _expire_Date = java.sql.Date.valueOf(expire_Date);
        } catch (IllegalArgumentException e) {
            throw new InvalidDateFormatException();
        }

        if (_selling_price < 0) {
            throw new NegativeValueException();
        }

        if (_products_number < 0) {
            throw new NegativeValueException();
        }
    }

    public Integer _UPC = null;
    public java.sql.Date _expire_Date = null;
    public Integer _id_product = null;
    public Double _selling_price = null;
    public Integer _products_number = null;
}
