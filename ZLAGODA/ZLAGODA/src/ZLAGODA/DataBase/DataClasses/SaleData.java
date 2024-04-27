package ZLAGODA_project.DataBase.DataClasses;

public class SaleData {

    public SaleData(){}
    public SaleData(
    Integer UPC,
    Integer check_number,
    Integer product_number,
    Double selling_price
    ){
        _UPC = UPC;
        _check_number = check_number;
        _product_number = product_number;
        _selling_price	= selling_price;
    }

    public Integer _UPC = null;
    public Integer _check_number = null;
    public Integer _product_number = null;
    public Double _selling_price	= null;

}
