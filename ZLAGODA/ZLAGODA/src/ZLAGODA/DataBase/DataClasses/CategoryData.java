package ZLAGODA_project.DataBase.DataClasses;

import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;

public class CategoryData {

    public CategoryData(){}
    public CategoryData(
    Integer category_number,
    String category_name
    )
    {
        _category_name = category_name;
        _category_number = category_number;
    }
    public CategoryData(
            String category_number,
            String category_name
    ) throws InvalidValueException {
        _category_name = category_name;
        try {
            _category_number = Integer.valueOf(category_number);
        }
        catch (Exception e){
            throw new InvalidValueException();
        }
    }

    public Integer _category_number = null;
    public String _category_name = null;

}
