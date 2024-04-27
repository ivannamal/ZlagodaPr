package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.TWO;
import ZLAGODA_project.Front.ManagerMenu;
import ZLAGODA_project.Front.NotEditableTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ZvitWindow extends JDialog{
    private JPanel MainPanel;
    private JPanel TablePanel1;
    private JPanel TablePanel2;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private JTable table4;
    private JTable table5;
    private JTable table6;
    private JButton printButton;
    private JPanel NotSoMainPanel;
    private JPanel TablePanel3;
    private JPanel TablePanel4;
    private JPanel TablePanel5;
    private JPanel TablePanel6;

    ManagerMenu Owner = null;
    Integer ID = null;
    public ZvitWindow(Frame owner, boolean modal){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("Employee");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //NotSoMainPanel.setMaximumSize(new Dimension(540,520));

        DataHolder employeeDataset = Owner.DBC.getAllEmployee_SortByName();
        DataHolder customerDataset = Owner.DBC.getAllCustomer_SortByName();
        DataHolder categoryDataset = Owner.DBC.getAllCategory_SortByName();
        DataHolder productDataset = Owner.DBC.getAllProducts_SortByName();
        DataHolder productsInStoreDataset = Owner.DBC.getAllStoreProducts_SortByCountORName(false);
        DataHolder cheqDataset = Owner.DBC.getAllCheqs_SortByPrintDate();

        TWO<Object[],Object[][]> data = employeeDataset.ConvertToSwingTableData();
        NotEditableTableModel tableModel1 = new NotEditableTableModel(data._second, data._first);
        table1.setModel(tableModel1);

        data = customerDataset.ConvertToSwingTableData();
        NotEditableTableModel tableModel2 = new NotEditableTableModel(data._second, data._first);
        table2.setModel(tableModel2);

        data = categoryDataset.ConvertToSwingTableData();
        NotEditableTableModel tableModel3 = new NotEditableTableModel(data._second, data._first);
        table3.setModel(tableModel3);

        data = productDataset.ConvertToSwingTableData();
        NotEditableTableModel tableModel4 = new NotEditableTableModel(data._second, data._first);
        table4.setModel(tableModel4);

        data = productsInStoreDataset.ConvertToSwingTableData();
        NotEditableTableModel tableModel5 = new NotEditableTableModel(data._second, data._first);
        table5.setModel(tableModel5);

        data = cheqDataset.ConvertToSwingTableData();
        NotEditableTableModel tableModel6 = new NotEditableTableModel(data._second, data._first);
        table6.setModel(tableModel6);


        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Owner.DBC.createPDF();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
    }


}
