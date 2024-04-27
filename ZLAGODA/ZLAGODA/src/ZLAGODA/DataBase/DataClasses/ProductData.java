package ZLAGODA_project.DataBase.DataClasses;

import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;

public class ProductData {
    public Integer _id_product;
    public Integer _category_number;
    public String _product_name;
    public String _characteristics;

    public ProductData() {}

    public ProductData(Integer id_product, Integer category_number, String product_name, String characteristics) throws InvalidValueException {
        if (id_product == null || category_number == null) {
            throw new InvalidValueException();
        }
        _id_product = id_product;
        _category_number = category_number;
        _product_name = product_name;
        _characteristics = characteristics;
    }
}
