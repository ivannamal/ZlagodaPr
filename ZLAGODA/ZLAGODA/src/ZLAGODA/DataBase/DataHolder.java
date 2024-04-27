package ZLAGODA_project.DataBase;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.util.ArrayList;
import java.util.LinkedList;


public class DataHolder {


    public DataHolder(){
        _columnNames = new LinkedList<>();
        _data = new LinkedList<>();
    }

    public LinkedList<String> _columnNames;
    public LinkedList<LinkedList<Object>> _data;
    LinkedList<String> _logins;
    LinkedList<String> _passwords;
    public LinkedList<String> getLogins() {
        return _logins;
    }

    public LinkedList<String> getPasswords() {
        return _passwords;
    }
    public TWO<Object[], Object[][]> ConvertToSwingTableData(){
        String[] names = _columnNames.toArray(String[]::new);
        String[][] table = new String[_data.size()][_columnNames.size()];

        int y = 0;
        for (LinkedList<Object> datum : _data) {
            int x = 0;
            for (Object o : datum) {
                if(o != null){
                    table[y][x] = o.toString();
                }
                else{
                    table[y][x] = "NULL";
                }
                x++;
            }
            y++;
        }
        return new TWO<Object[], Object[][]>(names, table);
    }

}
