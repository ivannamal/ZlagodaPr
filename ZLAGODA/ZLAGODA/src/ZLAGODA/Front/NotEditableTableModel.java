package ZLAGODA_project.Front;

import javax.swing.table.DefaultTableModel;

public class NotEditableTableModel extends DefaultTableModel {


    public NotEditableTableModel(Object Data[][], Object Names[]){
        super(Data, Names);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
